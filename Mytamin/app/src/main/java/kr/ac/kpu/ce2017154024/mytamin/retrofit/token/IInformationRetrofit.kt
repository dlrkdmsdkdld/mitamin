package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface IInformationRetrofit {

    @Multipart
    @POST("/user/img")
    fun editProfileImage(@Part file:MultipartBody.Part): Call<JsonElement>
    @GET("/user/profile")
    fun getProfile() : Call<JsonElement>


    @PATCH("/user/bemy-msg")
    fun CorrectionBeMyMessage(@Body beMyMessage:String) : Call<JsonElement>

    @PATCH("/user/nickname/{nickname}")
    fun CorrectionNickname(@Path("nickname") nickname:String) : Call<JsonElement>

    @GET("/wish/list")
    fun getWishlist() : Call<JsonElement>

    @GET("/myday/info")
    fun getMyday() : Call<JsonElement>


}