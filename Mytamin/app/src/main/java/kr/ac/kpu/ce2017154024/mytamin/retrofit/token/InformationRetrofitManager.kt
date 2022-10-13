package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import android.util.Log
import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart

class InformationRetrofitManager {

    companion object{
        val instance=InformationRetrofitManager()
    }
    private val iInformationRetrofit:IInformationRetrofit? =TokenRetrofitClient.getClient()?.create(IInformationRetrofit::class.java)

    fun oneImageAPICall(file: MultipartBody.Part){
        iInformationRetrofit?.editProfileImage(file)?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"이미지전송성공response -> $response")
                response.body()?.let {
                    val body =it.asJsonObject
                    val message = body.get("message").asString
                    val uri = body.get("data").asString
                    Log.d(TAG, "user doCompleteBreath response message:${message} " )
                    Log.d(TAG, "user doCompleteBreath response updatedTime:$uri" )
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"이미지전송실패 이유 -> $t")
            }

        })

    }



}