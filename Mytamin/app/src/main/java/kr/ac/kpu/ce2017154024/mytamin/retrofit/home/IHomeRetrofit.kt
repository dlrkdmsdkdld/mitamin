package kr.ac.kpu.ce2017154024.mytamin.retrofit.home

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface IHomeRetrofit {

    @GET("/home/welcome")
    fun getWelcomeMessage() : Call<JsonElement>

    @PATCH("/home/breath")
    fun completeBreath() : Call<JsonElement>

    @PATCH("/home/sense")
    fun completeSense() : Call<JsonElement>
}