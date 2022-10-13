package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IInformationRetrofit {

    @Multipart
    @POST("/img/one")
    fun editProfileImage(@Part file:MultipartBody.Part): Call<JsonElement>

}