package kr.ac.kpu.ce2017154024.mytamin.retrofit.home

import android.util.Log
import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.LoginData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitClient
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class HomeRetrofitManager {

    companion object{
        val instance=HomeRetrofitManager()
    }
    private val iHomeRetrofit:IHomeRetrofit? =TokenRetrofitClient.getClient()?.create(IHomeRetrofit::class.java)

    fun getWelcomeMessage(completion:(RESPONSE_STATUS,LoginData?) -> Unit){
        iHomeRetrofit?.getWelcomeMessage()
            ?.enqueue(object : retrofit2.Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        Log.d(TAG, "user getWelcomeMessage onResponse ${response}" )
                        val body =it.asJsonObject
                        //TODO 잊지말고 토큰 추가해야함
                        val nickname = body.get("data").asJsonObject.get("nickname").asString
                        val comment = body.get("data").asJsonObject.get("comment").asString
                        val result = LoginData(nickname,comment)
                        completion(RESPONSE_STATUS.OKAY,result)

                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user Login onFailure ${t}" )
                }

            })

    }


}