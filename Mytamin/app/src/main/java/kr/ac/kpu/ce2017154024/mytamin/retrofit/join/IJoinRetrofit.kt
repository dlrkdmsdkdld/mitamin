package kr.ac.kpu.ce2017154024.mytamin.retrofit.join


import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.*
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
    fun postUser(@Body data:NewUser): Call<JsonElement>

    @POST("/auth/default/login")
    fun postLogin(@Body data:LoginData): Call<JsonElement>

    @POST("/auth/reset/code")
    fun postEmailCode(@Body email: email): Call<JsonElement>


    @POST("/auth/code")
    fun postCertificate(@Body certificate: emailCertificate): Call<JsonElement>

    @PUT("/auth/password")
    fun ChangePassword(@Body ChangePassword: ChangePassword): Call<JsonElement>

}