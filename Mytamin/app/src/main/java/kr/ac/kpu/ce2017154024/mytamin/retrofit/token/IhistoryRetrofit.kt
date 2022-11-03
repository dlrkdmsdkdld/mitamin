package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IhistoryRetrofit {
    @GET("/care/random")
    fun getRandomCare() : Call<JsonElement>

    @GET("/report/feeling/rank")
    fun getMostFeeling() : Call<JsonElement>
    @GET("/report/weekly/mental")
    fun weeklyMental() : Call<JsonElement>

    @GET("/mytamin/monthly/{date}")
    fun getMonthMytamin(@Path("date") date:String ) : Call<JsonElement>
}