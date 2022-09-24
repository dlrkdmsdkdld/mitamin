package kr.ac.kpu.ce2017154024.mytamin.retrofit


import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.NewUser
import retrofit2.Call
import retrofit2.http.*

interface IJoinRetrofit {
    @GET("/auth/check/email/{emailAddress}")
    fun checkEmail(@Path("emailAddress")   emailAddress:String): Call<JsonElement>
    @GET("/auth/check/nickname/{nickName}")
    fun checkName(@Path("nickName") nickname:String): Call<JsonElement>

//    @POST("/auth/signup")
//    fun postUser(
//        @Body email:String,
//        @Body password:String,
//        @Body nickname:String,
//        @Body mytaminHour:String,
//        @Body mytaminMin:String,
//
//    ): Call<JsonElement>
    @POST("/auth/signup")
    fun postUser(
        @Body data:NewUser,


        ): Call<JsonElement>

}