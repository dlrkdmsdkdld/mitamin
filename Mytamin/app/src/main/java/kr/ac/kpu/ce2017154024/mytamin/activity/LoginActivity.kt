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
import kr.ac.kpu.ce2017154024.mytamin.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.model.LoginData
import kr.ac.kpu.ce2017154024.mytamin.model.NewUser
import kr.ac.kpu.ce2017154024.mytamin.retrofit.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding:ActivityLoginBinding
    private lateinit var customProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG,"LoginActivity onCreate")
        customProgressDialog= LoadingDialog(this)
        customProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        login_login_Btn.setOnClickListener(this)
        login_back_btn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            login_login_Btn ->{
                val email = login_email_text.text
                val password = login_password_text.text
                val inputdata = LoginData(email.toString(),password.toString())
                loginAPICall(query = inputdata)
                customProgressDialog.show()

            }
            login_back_btn->{
                val intent= Intent(this,firstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    fun loginAPICall(query:LoginData){
        JoinRetrofitManager.instance.UserLogin(inputData = query, completion = { responseStatus, returnLoginData ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    if(returnLoginData?.statusCode==200 && returnLoginData?.message=="기본 로그인"){
                        Log.d(TAG,"로그인 성공 -> ${returnLoginData?.accessToken}")
                        PrivateUserDataSingleton.accessToken = returnLoginData?.accessToken
                        PrivateUserDataSingleton.refreshToken=returnLoginData?.refreshToken
                        PrivateUserDataSingleton.userEmail = query.email
                        PrivateUserDataSingleton.userPassword = query.password
                        Log.d(TAG,"PrivateUserDataSingleton 성공" +
                                "PrivateUserDataSingleton.accessToken -> ${returnLoginData?.accessToken}"+"" +
                                "userEmail -> $PrivateUserDataSingleton.userEmail")
                        customProgressDialog.dismiss()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"${returnLoginData?.message}", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        })
    }


}