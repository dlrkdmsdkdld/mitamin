package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentFindTwoBinding
import kr.ac.kpu.ce2017154024.mytamin.model.ChangePassword
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.userEmail
import java.util.regex.Pattern

class repasswordActivity : AppCompatActivity(), View.OnClickListener {
    private var okRepassword:Boolean =false
    private  var passwordValue :String? =null
    private lateinit var customProgressDialog: Dialog
    private lateinit var mbinding :FragmentFindTwoBinding
    private var passwordeye = true
    private var repasswordeye = true
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
           // InputType.TYPE_CLASS_TEXT
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= FragmentFindTwoBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        mbinding.findTopLayoutOther.visibility=View.VISIBLE
        customProgressDialog= LoadingDialog(this)
        mbinding.findCompleteBtnOther.visibility = View.VISIBLE
        mbinding.findTwoOnePasswordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d(Constant.TAG,"join_step_one_password_text afterTextChanged $p0  ${p0?.trim()}")
                if (Pattern.matches("^[a-zA-Z0-9]*\$", p0) && (8 <=p0!!.count() && p0.count()<31 )  && p0.toString()==p0.toString().trim()) {
                    mbinding.findTwoPasswordLayout.text="사용 가능한 비밀번호에요!"
                    mbinding.findTwoPasswordLayout.setTextColor(resources.getColor(R.color.primary,null))
                    passwordValue=p0.toString().trim()
                    if (passwordValue == mbinding.findTwoPasswordConfirmText.text.toString()){
                        okRepassword=true
                        mbinding.findTwoPasswordConfirmLayout.text="비밀번호가 일치해요!"
                        mbinding.findTwoPasswordConfirmLayout.setTextColor(resources.getColor(R.color.primary,null))
                    }
                    OkAllItem()
                }
                else {
                    mbinding.findTwoPasswordLayout.text="영문, 숫자를 포함한 8~30자리 조합으로 설정해주세요."
                    mbinding.findTwoPasswordLayout.setTextColor(resources.getColor(R.color.textRed,null))
                    passwordValue=null
                    OkAllItem()
                }

            }

        })

        mbinding.findTwoPasswordConfirmText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                mbinding.findTwoPasswordConfirmLayout.hint =""
                if (passwordValue == p0.toString()){
                    mbinding.findTwoPasswordConfirmLayout.text="비밀번호가 일치해요!"
                    okRepassword=true
                    mbinding.findTwoPasswordConfirmLayout.setTextColor(resources.getColor(R.color.primary,null))
                    OkAllItem()

                }else{mbinding.findTwoPasswordConfirmLayout.text="비밀번호가 일치하지 않습니다."
                    okRepassword=false
                    mbinding.findTwoPasswordConfirmLayout.setTextColor(resources.getColor(R.color.textRed,null))
                    OkAllItem()
                }
            }

        })
        mbinding.findCompleteBtnOther.setOnClickListener(this)
        mbinding.findBackBtnOther.setOnClickListener(this)
        mbinding.findPasswordBtn .setOnClickListener(this)
        mbinding.findRepasswordBtn.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding.findPasswordBtn ->{
                passwordEye()
            }
            mbinding.findRepasswordBtn ->{
                repasswordEye()
            }
            mbinding.findCompleteBtnOther ->{
                passwordValue?.let {
                    customProgressDialog.show()
                    JoinRetrofitManager.instance.changePassword(ChangePassword(password = it, email = userEmail)){ responseStatus ->
                        customProgressDialog.dismiss()
                        val i = Intent(this,LoginActivity::class.java)
                        startActivity(i)
                        finishAffinity()
                    }
                }

            }
            mbinding.findBackBtnOther ->{
                finish()
            }

        }

    }
    private fun OkAllItem(){
        mbinding.findCompleteBtnOther.isEnabled = passwordValue!=null && okRepassword
    }
}