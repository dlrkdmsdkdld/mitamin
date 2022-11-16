package kr.ac.kpu.ce2017154024.mytamin.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kr.ac.kpu.ce2017154024.mytamin.MyApplication
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

object PreferenceUtil {

    private const val INPUT_EMAIL_HISTORY = "input_email_history"
    private const val INPUT_PASSWORD_HISTORY = "input_password_history"

    private const val KEY_EMAIL_HISTORY = "key_email_history"
    private const val KEY_PASSWORD_HISTORY = "key_password_history"

    private const val INPUT_TOKEN_HISTORY = "input_token_history"
    private const val KEY_TOKEN_HISTORY = "key_token_history"

    fun storeUserData(email:String,password:String){
        Log.d(TAG,"SharedPrefManager - storeUserData called")
        val sharedEmail = MyApplication.instance.getSharedPreferences(INPUT_EMAIL_HISTORY,Context.MODE_PRIVATE)
        val sharedPassword = MyApplication.instance.getSharedPreferences(INPUT_PASSWORD_HISTORY,Context.MODE_PRIVATE)
        val editorEmail = sharedEmail.edit()
        editorEmail.putString(KEY_EMAIL_HISTORY,email)
        editorEmail.apply()
        val editorPassword = sharedPassword.edit()
        editorPassword.putString(KEY_PASSWORD_HISTORY,password)
        editorPassword.apply()

    }
    fun storeUserToken(token:String){
        val sharedtoken = MyApplication.instance.getSharedPreferences(INPUT_TOKEN_HISTORY,Context.MODE_PRIVATE)
        val editortoken = sharedtoken.edit()
        editortoken.putString(KEY_TOKEN_HISTORY,token)
        editortoken.apply()
    }
    fun obtainToken():String{
        val sharedtoken = MyApplication.instance.getSharedPreferences(INPUT_TOKEN_HISTORY,Context.MODE_PRIVATE)
        val token = sharedtoken.getString(KEY_TOKEN_HISTORY,"null").toString()
        return token
    }
    fun obtainUserData():Pair<String,String>{
        val sharedEmail = MyApplication.instance.getSharedPreferences(INPUT_EMAIL_HISTORY,Context.MODE_PRIVATE)
        val email = sharedEmail.getString(KEY_EMAIL_HISTORY,"null").toString()
        val sharedPassword = MyApplication.instance.getSharedPreferences(INPUT_PASSWORD_HISTORY,Context.MODE_PRIVATE)
        val password = sharedPassword.getString(KEY_PASSWORD_HISTORY,"null").toString()
        Log.d(TAG,"SharedPrefManager - obtainUserData called email : $email password : $password")
        return Pair(email,password)
    }
    fun clearUserData(){
        val sharedE = MyApplication.instance.getSharedPreferences(INPUT_EMAIL_HISTORY,Context.MODE_PRIVATE)
        val editorE = sharedE.edit()
        editorE.clear()
        editorE.apply()
        val sharedP = MyApplication.instance.getSharedPreferences(INPUT_PASSWORD_HISTORY,Context.MODE_PRIVATE)
        val editorP = sharedP.edit()
        editorP.clear()
        editorP.apply()
    }


}