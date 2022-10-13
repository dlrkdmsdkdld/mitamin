package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IInformationRetrofit {

    @POST("/img/one")
    fun editProfileImage(@Body data: ReportData): Call<JsonElement>

}