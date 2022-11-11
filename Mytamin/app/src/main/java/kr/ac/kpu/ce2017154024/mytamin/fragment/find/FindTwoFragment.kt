package kr.ac.kpu.ce2017154024.mytamin.fragment.find

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_join_step_one.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.FindPasswordActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentFindTwoBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.viewModel.findPasswordViewmodel
import java.util.regex.Pattern


class FindTwoFragment : Fragment() {
    private lateinit var mbinding:FragmentFindTwoBinding
    private  var passwordValue :String? =null
    private val myViewModel by activityViewModels<findPasswordViewmodel>()
    private var okRepassword:Boolean =false
    private var passwordeye = true
    private var repasswordeye = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentFindTwoBinding.inflate(inflater,container,false)


        (activity as FindPasswordActivity).enableComplete(false)
        mbinding?.findPasswordBtn.setOnClickListener {
            passwordEye()
        }
        mbinding?.findRepasswordBtn.setOnClickListener {
            repasswordEye()
        }
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mbinding.findTwoOnePasswordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d(Constant.TAG,"join_step_one_password_text afterTextChanged $p0  ${p0?.trim()}")
                if (Pattern.matches("^[a-zA-Z0-9]*\$", p0) && (8 <=p0!!.count() && p0!!.count()<31 )  && p0.toString()==p0.toString().trim()) {
                    mbinding.findTwoPasswordLayout.text="사용 가능한 비밀번호에요!"
                    mbinding?.findTwoPasswordLayout.setTextColor(resources.getColor(R.color.primary,null))
                    passwordValue=p0.toString().trim()
                    if (passwordValue == mbinding.findTwoPasswordConfirmText.text.toString()){
                        okRepassword=true
                        mbinding.findTwoPasswordConfirmLayout.text="비밀번호가 일치해요!"
                        mbinding?.findTwoPasswordConfirmLayout.setTextColor(resources.getColor(R.color.primary,null))
                    }
                    OkAllItem()
                }
                else {
                    mbinding.findTwoPasswordLayout.text="영문, 숫자를 포함한 8~30자리 조합으로 설정해주세요."
                    mbinding?.findTwoPasswordLayout.setTextColor(resources.getColor(R.color.textRed,null))
                    passwordValue=null
                    OkAllItem()
                }

            }

        })

        mbinding.findTwoPasswordConfirmText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (passwordValue == p0.toString()){
                    mbinding.findTwoPasswordConfirmLayout.text="비밀번호가 일치해요!"
                    mbinding?.findTwoPasswordConfirmLayout.setTextColor(resources.getColor(R.color.primary,null))
                    okRepassword=true
                    OkAllItem()


                }else{mbinding.findTwoPasswordConfirmLayout.text="비밀번호가 일치하지 않습니다."
                    mbinding?.findTwoPasswordConfirmLayout.setTextColor(resources.getColor(R.color.textRed,null))
                    okRepassword=false
                    OkAllItem()
                }
            }

        })

    }
    private fun OkAllItem(){
        if (passwordValue!=null && okRepassword) {
            (activity as FindPasswordActivity).enableComplete(true)
            myViewModel.setPassword(passwordValue!!)
        }else{
            (activity as FindPasswordActivity).enableComplete(false)

        }
    }
    private fun passwordEye(){
        if (passwordeye) {
            passwordeye=false
            mbinding?.findPasswordBtn.setBackgroundResource(R.drawable.ic_eye_off)

            mbinding?.findTwoOnePasswordText.inputType=(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            mbinding?.findTwoOnePasswordText.setSelection(mbinding?.findTwoOnePasswordText.text.length)
            //mbinding?.findTwoOnePasswordText.transformationMethod = PasswordTransformationMethod.getInstance()
        }else{
            passwordeye=true
            mbinding?.findPasswordBtn.setBackgroundResource(R.drawable.ic_baseline_remove_red_eye_24)
            mbinding?.findTwoOnePasswordText.inputType=InputType.TYPE_CLASS_TEXT
            mbinding?.findTwoOnePasswordText.setSelection(mbinding?.findTwoOnePasswordText.text.length)

        }
    }
    private fun repasswordEye(){
        if (repasswordeye) {
            repasswordeye=false
            mbinding?.findRepasswordBtn.setBackgroundResource(R.drawable.ic_eye_off)
            mbinding?.findTwoPasswordConfirmText.inputType=InputType.TYPE_CLASS_TEXT or  InputType.TYPE_TEXT_VARIATION_PASSWORD
            mbinding?.findTwoPasswordConfirmText.setSelection(mbinding?.findTwoPasswordConfirmText.text.length)
        }else{
            repasswordeye=true
            mbinding?.findRepasswordBtn.setBackgroundResource(R.drawable.ic_baseline_remove_red_eye_24)
            mbinding?.findTwoPasswordConfirmText.inputType=InputType.TYPE_CLASS_TEXT
            mbinding?.findTwoPasswordConfirmText.setSelection(mbinding?.findTwoPasswordConfirmText.text.length)

        }
    }

}