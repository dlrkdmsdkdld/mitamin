package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_join_step_one.*
import kr.ac.kpu.ce2017154024.mytamin.BuildConfig
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.joinActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentJoinStepOneBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import java.util.regex.Pattern

class joinStepOneFragment : Fragment() {
    private var mBinding : FragmentJoinStepOneBinding?=null
    private var emailValue:String?=null
    private var passwordValue:String?=null
    private var RepasswordValue:Boolean?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJoinStepOneBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"joinStepOneFragment onCreateView")
        //CheckEmailAPICall("mytamin@naver.com")



        return mBinding?.root

    }
    private fun CheckEmailAPICall(query:String){
        JoinRetrofitManager.instance.checkEmail(inputemail = query, completion = { responseStatus, checkEmailData ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY -> {
                    Log.d(TAG,"api 호출 성공 check  = $checkEmailData")
                }
            }

        })
    }

    //join_step_one_email_text --> 이메일 입력창
    //join_step_one_password_text --> 패스워드 입력창
    //join_step_one_password_confirm_text -- >패스워드 확인창
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        join_step_one_password_text.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d(TAG,"join_step_one_password_text afterTextChanged $p0")
                if (Pattern.matches("^[a-zA-Z0-9]*\$", p0) and ((8 <=p0!!.count()) and (p0!!.count()<31) )) {
                    join_step_one_password_layout.helperText="사용 가능한 비밀번호입니다"
                    passwordValue=p0.toString()
                }
                else {
                    join_step_one_password_layout.helperText="영문, 숫자를 포함한 8~30자리 조합으로 설정해주세요."
                    passwordValue=null
                }

            }

        })
        join_step_one_password_confirm_text.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (passwordValue == p0.toString()){
                    join_step_one_password_confirm_layout.helperText="비밀번호가 일치합니다."
                    RepasswordValue=true

                }else{join_step_one_password_confirm_layout.helperText="비밀번호가 일치하지 않습니다."
                    RepasswordValue=false
                }
            }

        })
        join_step_one_email_text.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"joinStepOneFragment onDestroyView")
    }


}