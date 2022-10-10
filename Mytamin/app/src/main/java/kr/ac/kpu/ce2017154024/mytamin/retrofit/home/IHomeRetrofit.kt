package kr.ac.kpu.ce2017154024.mytamin.retrofit.home

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.CareData
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.NewUser
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IHomeRetrofit {

    @GET("/home/welcome")
    fun getWelcomeMessage() : Call<JsonElement>

    @PATCH("/home/breath")
    fun completeBreath() : Call<JsonElement>

    @PATCH("/home/sense")
    fun completeSense() : Call<JsonElement>

    @POST("/report/new")
    fun completeReport(@Body data:ReportData): Call<JsonElement>
    @POST("/care/new")
    fun completeCare(@Body data:CareData): Call<JsonElement>

    @GET("/home/progress/status")
    fun getStatus():Call<JsonElement>

    @GET("/mytamin/latest")
    fun getlatestMytamin() : Call<JsonElement>

    @GET("/mytamin/latest")
    suspend fun getlatestMytaminSuspend() : Response<LatestMytamin>

    @PUT("/report/{reportId}")
    fun correctionReport(@Body data:ReportData, @Path("reportId") reportId:Int): Call<JsonElement>
    @PUT("/care/{careId}")
    fun correctionCare(@Body data:CareData,@Path("careId") careId:Int): Call<JsonElement>

//    @POST("/auth/signup")
//    fun postUser(@Body data: NewUser): Call<JsonElement>

}