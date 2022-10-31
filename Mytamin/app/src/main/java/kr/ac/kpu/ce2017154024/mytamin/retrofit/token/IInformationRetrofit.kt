package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.EditProfile
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IInformationRetrofit {

    @Multipart
    @POST("/user/img")
    fun editProfileImage(@Part file:MultipartBody.Part): Call<JsonElement>
    @GET("/user/profile")
    fun getProfile() : Call<JsonElement>

    @Multipart
    @PUT("/user/profile")
    fun editProfile(@Part file:MultipartBody.Part? ,@Part("isImgEdited") isImgEdited: RequestBody,@Part("nickname") nickname: RequestBody,
                    @Part("beMyMessage") beMyMessage: RequestBody
                    ) : Call<JsonElement>



    @PATCH("/user/bemy-msg")
    fun CorrectionBeMyMessage(@Body beMyMessage:String) : Call<JsonElement>

    @PATCH("/user/nickname/{nickname}")
    fun CorrectionNickname(@Path("nickname") nickname:String) : Call<JsonElement>

    @GET("/wish/list")
    fun getWishlist() : Call<JsonElement>

    @GET("/myday/info")
    fun getMyday() : Call<JsonElement>

    @GET("/daynote/list")
    fun getDaynote() : Call<JsonElement>


    @Multipart
    @POST("/img/list")
    fun sendImageList(@Part file:List<MultipartBody.Part?>) : Call<JsonElement>

    @GET("/daynote/check/{day}")
    fun checkDaynote(@Path("day") day:String) : Call<JsonElement>

    @POST("/wish/new")
    fun sendNewWishlist(@Body wishText:String) : Call<JsonElement>

    @Multipart
    @POST("/daynote/new")
    fun newDaynote(@Part fileList:List<MultipartBody.Part?>, @Part("wishText") wishText: RequestBody,@Part("note") note: RequestBody
        ,@Part("date") date: RequestBody
    ) : Call<JsonElement>

    @DELETE("/daynote/{daynoteId}")
    fun deleteDaynote(@Path("daynoteId") day:Int): Call<JsonElement>

    @Multipart
    @PUT("/daynote/{daynoteId}")
    fun modifyDaynote(@Part fileList:List<MultipartBody.Part?>, @Part("wishText") wishText: RequestBody,@Part("note") note: RequestBody
                   ,@Path("daynoteId") daynoteId:Int
    ) : Call<JsonElement>


}