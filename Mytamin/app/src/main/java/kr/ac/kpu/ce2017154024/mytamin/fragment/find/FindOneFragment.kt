package kr.ac.kpu.ce2017154024.mytamin.fragment.find

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.FindPasswordActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentFindOneBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitClient
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import kr.ac.kpu.ce2017154024.mytamin.utils.checkEmail
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToTimeLine
import kr.ac.kpu.ce2017154024.mytamin.viewModel.findPasswordViewmodel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class FindOneFragment : Fragment(),View.OnClickListener {
    private lateinit var sendEmail:String
    private lateinit var mbinding:FragmentFindOneBinding
    private val myViewModel by activityViewModels<findPasswordViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding=FragmentFindOneBinding.inflate(inflater,container,false)
        mbinding?.findOneEmailBtn.setOnClickListener(this)
        mbinding?.findOneCodeBtn.setOnClickListener(this)

        myViewModel.timerCount.observe(requireActivity(), Observer {
            mbinding?.findOneTimer.text = it.parseIntToTimeLine()
            if (it==0){
                setEnable(false)
            }
        })

        return mbinding.root
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.findOneEmailBtn ->{
                if (checkEmail(mbinding?.findOneEmailText.text.toString().trim()) ){
                    wantCode(mbinding?.findOneEmailText.text.toString().trim())
                    setEnableEmail(false)
                }else Toast.makeText(requireActivity(),"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show()
            }
            mbinding?.findOneCodeBtn ->{
                (activity as FindPasswordActivity).showLoading(true)
                JoinRetrofitManager.instance.postCertificate(sendEmail,mbinding?.findOneEmailCode.text.toString()){responseStatus, b ->
                    (activity as FindPasswordActivity).showLoading(false)
                    if (b) (activity as FindPasswordActivity).enableComplete(true)
                    else Toast.makeText(requireActivity(),"인증번호가 틀렸습니다",Toast.LENGTH_SHORT).show()

                }

            }
        }
    }
    private fun wantCode(email:String){
        JoinRetrofitManager.instance.postEmailCode(email){
            if (it == RESPONSE_STATUS.OKAY){
                setEnable(true)
                myViewModel.timerDestory()
                myViewModel.timerset()
                myViewModel.timerStart()
                sendEmail=email
                myViewModel.setemail(email)
                mbinding?.findOneEmailBtn.text = "재전송"
                setEnableEmail(true)

            }
        }
    }
    private fun setEnable(data:Boolean){
        if (data){
            mbinding?.findOneCodeBtn.setTextColor(resources.getColor(R.color.primary,null))
            mbinding?.findOneCodeBtn.isEnabled = true
        }else{
            mbinding?.findOneCodeBtn.setTextColor(resources.getColor(R.color.notEnabled,null))
            mbinding?.findOneCodeBtn.isEnabled = false
        }
    }
    private fun setEnableEmail(data:Boolean){
        if (data){
            mbinding?.findOneEmailBtn.setTextColor(resources.getColor(R.color.primary,null))
            mbinding?.findOneEmailBtn.isEnabled = true
        }else{
            mbinding?.findOneEmailBtn.setTextColor(resources.getColor(R.color.notEnabled,null))
            mbinding?.findOneEmailBtn.isEnabled = false
        }
    }

}