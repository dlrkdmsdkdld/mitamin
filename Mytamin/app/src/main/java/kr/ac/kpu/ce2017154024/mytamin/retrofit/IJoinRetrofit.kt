package kr.ac.kpu.ce2017154024.mytamin.retrofit


import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IJoinRetrofit {
//https://my-tamin.herokuapp.com/auth/check/email/mytamin@naver.com
    @GET("/auth/check/email/{emailAddress}")
    fun checkEmail(@Path("emailAddress")   emailAddress:String): Call<JsonElement>
    @GET("/auth/check/nickname/{nickName}")
    fun checkName(@Path("nickName") nickname:String): Call<JsonElement>
}