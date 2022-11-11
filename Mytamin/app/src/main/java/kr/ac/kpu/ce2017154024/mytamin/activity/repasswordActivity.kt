package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivitySettingBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentFindTwoBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.setting.AlarmCalendarFragment
import kr.ac.kpu.ce2017154024.mytamin.model.ChangePassword
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.userEmail
import java.util.regex.Pattern

class repasswordActivity : AppCompatActivity(), View.OnClickListener {
    private var okRepassword:Boolean =false
    private  var passwordValue :String? =null
    private lateinit var customProgressDialog: Dialog
    private lateinit var mbinding :FragmentFindTwoBinding
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
                mbinding.findTwoPasswordLayout.hint =""
                Log.d(Constant.TAG,"join_step_one_password_text afterTextChanged $p0  ${p0?.trim()}")
                if (Pattern.matches("^[a-zA-Z0-9]*\$", p0) && (8 <=p0!!.count() && p0.count()<31 )  && p0.toString()==p0.toString().trim()) {
                    mbinding.findTwoPasswordLayout.helperText="사용 가능한 비밀번호에요!"
                    passwordValue=p0.toString().trim()
                    OkAllItem()
                }
                else {
                    mbinding.findTwoPasswordLayout.helperText="영문, 숫자를 포함한 8~30자리 조합으로 설정해주세요."
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
                    mbinding.findTwoPasswordConfirmLayout.helperText="비밀번호가 일치해요!"
                    okRepassword=true
                    OkAllItem()

                }else{mbinding.findTwoPasswordConfirmLayout.helperText="비밀번호가 일치하지 않습니다."
                    okRepassword=false
                    OkAllItem()
                }
            }

        })
        mbinding.findCompleteBtnOther.setOnClickListener(this)
        mbinding.findBackBtnOther.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
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