package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET

interface IhistoryRetrofit {
    @GET("/care/random")
    fun getRandomCare() : Call<JsonElement>

    @GET("/report/feeling/rank")
    fun getMostFeeling() : Call<JsonElement>


}