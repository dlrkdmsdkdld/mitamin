package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import com.google.gson.JsonElement
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.randomCare
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Response

class HistoryRetrofitManager {

    companion object {
        val instance = HistoryRetrofitManager()
    }

    private val iInformationRetrofit: IhistoryRetrofit? =
        TokenRetrofitClient.getClient()?.create(IhistoryRetrofit::class.java)

    fun getRandomCare(completion: (RESPONSE_STATUS, randomCare?) -> Unit){
        iInformationRetrofit?.getRandomCare()?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    if (it.asJsonObject.has("data")){
                        val careMsg1 = it.asJsonObject.get("data").asJsonObject.get("careMsg1").asString
                        val careMsg2 = it.asJsonObject.get("data").asJsonObject.get("careMsg2").asString
                        val takeAt = it.asJsonObject.get("data").asJsonObject.get("takeAt").asString
                        val result = randomCare(careMsg1 =careMsg1,careMsg2=careMsg2,takeAt=takeAt )
                        completion(RESPONSE_STATUS.OKAY,result)
                    }else{
                        completion(RESPONSE_STATUS.NO_CONTENT,null)

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.FAIL,null)

            }

        })
    }
}