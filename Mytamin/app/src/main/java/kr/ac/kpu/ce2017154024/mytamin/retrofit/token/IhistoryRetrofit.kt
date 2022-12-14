package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kr.ac.kpu.ce2017154024.mytamin.model.careCategoryCodeList
import kr.ac.kpu.ce2017154024.mytamin.model.initdata
import kr.ac.kpu.ce2017154024.mytamin.model.mytaminAlarmTime
import org.json.JSONArray
import org.json.JSONObject
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

    @DELETE ("/mytamin/{mytaminId}")
    fun DeleteMytamin(@Path("mytaminId") mytaminId:Int ) : Call<JsonElement>

    //var k = "application/json;charset=UTF-8"
    @POST ("/care/list")
    fun getCarehistory( @Body careCategoryCodeList: careCategoryCodeList) : Call<JsonElement>

    //    @Headers("Content-Type: application/json")
//    @DELETE ("/user/init")
//    fun initmitamin( @Body data: initdata) : Call<JsonElement>

    @HTTP(method = "DELETE", path = "/user/init", hasBody = true)
    fun initmitamin( @Body data: initdata) : Call<JsonElement>
    @DELETE ("/user/withdrawal")
    fun quitMytamin( ) : Call<JsonElement>


    @GET("/alarm/status")
    fun getAlarmState() : Call<JsonElement>

    @PATCH("/alarm/mytamin/on")
    fun setMytmainAlarmOn( @Body data: mytaminAlarmTime) : Call<JsonElement>

    @PATCH("/alarm/mytamin/off")
    fun setMytmainAlarmOff( ) : Call<JsonElement>
    @PATCH("/alarm/myday/off")
    fun setMydayAlarmOff( ) : Call<JsonElement>
    @PATCH("/alarm/myday/on/{time}")
    fun setMydayAlarmOn( @Path("time") time: Int) : Call<JsonElement>

}