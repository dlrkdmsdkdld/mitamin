package kr.ac.kpu.ce2017154024.mytamin.retrofit


import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IJoinRetrofit {
    @GET("/auth/check/email/{emailAddress}")
    fun checkEmail(@Path("emailAddress")   emailAddress:String): Call<JsonElement>
    @GET("/auth/check/nickname/{nickName}")
    fun checkName(@Path("nickName") nickname:String): Call<JsonElement>

    @POST("/auth/signup")
    fun newUserJoin(
        @Body("email") String email,
        @Body("password") String password,
        @Body("nickname") String nickname,
        @Body("mytaminHour") String mytaminHour,
        @Body("mytaminMin") String mytaminMin,

    )
//    {
//        "email" : "mytamn@naver.com",
//        "password" : "a1s2d3f4",
//        "nickname" : "강철탈",
//        "mytaminHour" : "22",
//        "mytaminMin" : "00"
//    }
}