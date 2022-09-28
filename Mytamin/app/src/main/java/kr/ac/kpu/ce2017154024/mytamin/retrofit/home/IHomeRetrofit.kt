package kr.ac.kpu.ce2017154024.mytamin.retrofit.home

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.NewUser
import retrofit2.Call
import retrofit2.http.*

interface IHomeRetrofit {

    @GET("/home/welcome")
    fun getWelcomeMessage() : Call<JsonElement>

    @PATCH("/home/breath")
    fun completeBreath() : Call<JsonElement>

    @PATCH("/home/sense")
    fun completeSense() : Call<JsonElement>
//    @POST("/auth/signup")
//    fun postUser(@Body data: NewUser): Call<JsonElement>

}