package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.model.LoginData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mbinding: ActivityLoginBinding
    private lateinit var customProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG, "LoginActivity onCreate")
        customProgressDialog = LoadingDialog(this)
        customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        login_login_Btn.setOnClickListener(this)
        login_back_btn.setOnClickListener(this)
        mbinding?.loginFindBtn.setOnClickListener(this)
        // PreferenceUtil.clearUserData()


    }

    override fun onClick(p0: View?) {
        when (p0) {
            login_login_Btn -> {

                customProgressDialog.show()
                val email = login_email_text.text
                val password = login_password_text.text
                val inputdata = LoginData(email.toString(), password.toString())
                loginAPICall(query = inputdata)

            }
            login_back_btn -> {
                val intent = Intent(this, firstActivity::class.java)
                startActivity(intent)
                finish()
            }
            mbinding?.loginFindBtn -> {
                val intent = Intent(this, FindPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun loginAPICall(query: LoginData) {
        JoinRetrofitManager.instance.UserLogin(
            inputData = query,
            completion = { responseStatus, returnLoginData ->
                Log.d(TAG, "로그인  -> ${returnLoginData}")
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        if (returnLoginData?.statusCode == 200 && returnLoginData?.message == "기본 로그인") {
                            Log.d(TAG, "로그인 성공 -> ${returnLoginData?.accessToken}")
                            PrivateUserDataSingleton.accessToken = returnLoginData?.accessToken
                            PrivateUserDataSingleton.refreshToken = returnLoginData?.refreshToken
                            PrivateUserDataSingleton.userEmail = query.email
                            PrivateUserDataSingleton.userPassword = query.password
                            if (login_check.isChecked) {
                                PreferenceUtil.storeUserData(query.email, query.password)
                            }
                            //유저 데이터 preference에 저장
                            Log.d(
                                TAG, "PrivateUserDataSingleton 성공" +
                                        "PrivateUserDataSingleton.accessToken -> ${returnLoginData?.accessToken}" + "" +
                                        "userEmail -> $PrivateUserDataSingleton.userEmail"
                            )
                            customProgressDialog.dismiss()
                            val intent = Intent(this, MainActivity::class.java)
                            finishAffinity() // 로그인 액티비티를 스택에서 삭제
                            startActivity(intent)
                            finish()
                        }

                    }
                    RESPONSE_STATUS.USER_NOT_FOUND_ERROR -> {
                        customProgressDialog.dismiss()
                        Toast.makeText(this, "없는 유저에요!", Toast.LENGTH_SHORT).show()

                    }
                    RESPONSE_STATUS.PASSWORD_PATTERN_ERROR -> {
                        customProgressDialog.dismiss()
                        Toast.makeText(this, "비번이나 아이디 틀려요!", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d(TAG, "로그인 실패 -> ")
                        customProgressDialog.dismiss()
                    }


                }
            })
    }


}