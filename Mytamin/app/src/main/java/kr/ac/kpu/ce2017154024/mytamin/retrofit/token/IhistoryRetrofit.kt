package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*

interface IhistoryRetrofit {
    @GET("/care/random")
    fun getRandomCare() : Call<JsonElement>

    @GET("/report/feeling/rank")
    fun getMostFeeling() : Call<JsonElement>
    @GET("/report/weekly/mental")
    fun weeklyMental() : Call<JsonElement>

    @GET("/mytamin/monthly/{date}")
    fun getMonthMytamin(@Path("date") date:String ) : Call<JsonElement>
    @GET("/mytamin/weekly/{date}")
    fun getWeekMytamin(@Path("date") date:String ) : Call<JsonElement>


    //var k = "application/json;charset=UTF-8"
    @GET("/care/list")
    fun getCarehistory( @Query("careCategoryCodeList")  careCategoryCodeList: JSONArray) : Call<JsonElement>

    //    @Headers("Content-Type: application/json")



}