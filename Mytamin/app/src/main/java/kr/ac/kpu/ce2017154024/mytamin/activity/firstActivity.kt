package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.android.synthetic.main.activity_first.*
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityFirstBinding
import kr.ac.kpu.ce2017154024.mytamin.model.LoginData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS


class firstActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityFirstBinding
    private lateinit var customProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        mBinding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Log.d(TAG, "firstActivity onCreate")
        first_join_btn.setOnClickListener(this)
        first_login_btn.setOnClickListener(this)
        customProgressDialog = LoadingDialog(this)


        val email = PreferenceUtil.obtainUserData().first
        val password = PreferenceUtil.obtainUserData().second
        Log.d(TAG, "LoginActivity PreferenceUtil email : $email passowrd:$password ")
        if (email != "null" && password != "null") {
            val inputdata = LoginData(email, password)
            loginAPICall(query = inputdata)
            customProgressDialog.show()
        } else {

        }
        //PreferenceUtil.clearUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "firstActivity onDestroy")
    }


    override fun onClick(p0: View?) {
        when (p0) {
            first_join_btn -> {
                val intent = Intent(applicationContext, joinActivity::class.java)
                startActivity(intent)
                finish()
            }
            first_login_btn -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun loginAPICall(query: LoginData) {
        JoinRetrofitManager.instance.UserLogin(
            inputData = query,
            completion = { responseStatus, returnLoginData ->
                Log.d(TAG, "?????????  -> ${returnLoginData}")
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        if (returnLoginData?.statusCode == 200 && returnLoginData?.message == "?????? ?????????") {
                            Log.d(TAG, "????????? ?????? -> ${returnLoginData?.accessToken}")
                            PrivateUserDataSingleton.accessToken = returnLoginData?.accessToken
                            PrivateUserDataSingleton.refreshToken = returnLoginData?.refreshToken
                            PrivateUserDataSingleton.userEmail = query.email.trim()
                            PrivateUserDataSingleton.userPassword = query.password
                            PreferenceUtil.storeUserToken(returnLoginData?.accessToken)
                            //?????? ????????? preference??? ??????
                            Log.d(
                                TAG, "PrivateUserDataSingleton ??????" +
                                        "PrivateUserDataSingleton.accessToken -> ${returnLoginData?.accessToken}" + "" +
                                        "userEmail -> ${PrivateUserDataSingleton.userEmail}end"
                            )
                            customProgressDialog.dismiss()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else if (returnLoginData?.statusCode == 404) {
                        } else {
                            Log.d(TAG, "????????? ?????? -> ")
                            customProgressDialog.dismiss()
                            Toast.makeText(this, "${returnLoginData?.message}", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                    RESPONSE_STATUS.USER_NOT_FOUND_ERROR -> {
                        Log.d(TAG, "????????? ?????? ?????? ??????????????? ")
                        customProgressDialog.dismiss()
                        Toast.makeText(this, "????????? ?????? ??? ????????????.", Toast.LENGTH_SHORT).show()

                    }
                    RESPONSE_STATUS.PASSWORD_PATTERN_ERROR -> {
                        Log.d(TAG, "????????? ?????? ???????????? ????????? ??????")
                        customProgressDialog.dismiss()
                        Toast.makeText(this, "????????? ?????? ???????????? ????????? ??????", Toast.LENGTH_SHORT).show()
                    }else -> customProgressDialog.dismiss()



                }
            })
    }
}