package kr.ac.kpu.ce2017154024.mytamin.retrofit

import android.util.Log
import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.CheckOverlapData
import kr.ac.kpu.ce2017154024.mytamin.model.NewUser
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class JoinRetrofitManager {
    companion object{
        val instance = JoinRetrofitManager()
    }
    private val iJoinRetrofit:IJoinRetrofit? = JoinRetrofitClient.getClient()?.create(IJoinRetrofit::class.java)

    fun checkEmail(inputemail:String? , completion:(RESPONSE_STATUS, CheckOverlapData?) -> Unit){
        val term = inputemail ?:""
        val call = iJoinRetrofit?.checkEmail(emailAddress = term).let {
            it
        }?: return


        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when(response.code()){
                    200 ->{
                        response.body()?.let {
                            val body = it.asJsonObject
                            val status = body.get("statusCode").asInt
                            val check = body.get("data").asBoolean
                            val resultData = CheckOverlapData(status = status ,result = check )
                            completion(RESPONSE_STATUS.OKAY,resultData)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
            }

        })
    }
    fun checkName(inputemail:String? , completion:(RESPONSE_STATUS, CheckOverlapData?) -> Unit){
        val term = inputemail ?:""
        val call = iJoinRetrofit?.checkName(nickname = term).let {
            it
        }?: return


        call.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            val body = it.asJsonObject
                            val status = body.get("statusCode").asInt
                            val check = body.get("data").asBoolean
                            val resultData = CheckOverlapData(status = status ,result = check )
                            completion(RESPONSE_STATUS.OKAY,resultData)
                        }

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
            }

        })
    }

    fun newUserJoin(inputData:NewUser, completion:(RESPONSE_STATUS, Int?) -> Unit){
//        iJoinRetrofit?.postUser(inputData.email,inputData.password,inputData.nickname,inputData.mytaminHour,inputData.mytaminMin)
        iJoinRetrofit?.postUser(inputData)
            ?.enqueue(object :Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                            response.body()?.let {
                                Log.d(TAG,"newUserJoin onResponse ${response} ")
                                val body = it.asJsonObject
                                val status = body.get("statusCode").asInt
                                completion(RESPONSE_STATUS.OKAY,status)
                            }

                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG,"newUserJoin onFailure ${t.message} ")
                }

            })


    }


}