package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import android.util.Log
import com.google.gson.*
import kr.ac.kpu.ce2017154024.mytamin.model.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HistoryRetrofitManager {

    companion object {
        val instance = HistoryRetrofitManager()
    }

    private val iHistoryRetrofit: IhistoryRetrofit? =
        TokenRetrofitClient.getClient()?.create(IhistoryRetrofit::class.java)

    fun getRandomCare(completion: (RESPONSE_STATUS, randomCare?) -> Unit){
        iHistoryRetrofit?.getRandomCare()?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    if (!(it.asJsonObject.get("data").isJsonNull)){
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

    fun getMostFeeling(completion: (RESPONSE_STATUS, ArrayList<feeling>?) -> Unit){
        iHistoryRetrofit?.getMostFeeling()?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    val data = it.asJsonObject.get("data").asJsonArray
                    var result = ArrayList<feeling>()
                    Log.d(TAG, "이번 달 느낀 감정 개수 = data.size = ${data.size()}")
                    if (data.size() ==0) completion(RESPONSE_STATUS.NO_CONTENT , null)
                    else{
                        data.forEach {
                            val feel = it.asJsonObject.get("feeling").asString
                            val count = it.asJsonObject.get("count").asInt
                            result.add(feeling(feel,count))
                        }
                        completion(RESPONSE_STATUS.OKAY , result)
                    }


                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.FAIL , null)
            }

        })
    }
    fun getWeeklyMental(completion:(RESPONSE_STATUS, ArrayList<weeklyMental>?) ->Unit){
        iHistoryRetrofit?.weeklyMental()?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response?.body()?.let {
                    val data = it.asJsonObject.get("data").asJsonArray
                    var result = ArrayList<weeklyMental>()
                    data.forEach {
                        val dayOfWeek = it.asJsonObject.get("dayOfWeek").asString
                        val mentalConditionCode =it.asJsonObject.get("mentalConditionCode").asInt
                        result.add(weeklyMental(dayOfWeek,mentalConditionCode))
                    }
                    completion(RESPONSE_STATUS.OKAY,result)
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.FAIL,null)
            }

        })

    }
    fun getMonthMytamin(month:String, completion: (RESPONSE_STATUS, ArrayList<monthMytamin>?) -> Unit){
            iHistoryRetrofit?.getMonthMytamin(month)?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val result = ArrayList<monthMytamin>()
                        val data = it.asJsonObject.get("data").asJsonArray
                        data.forEach {
                            val day = it.asJsonObject.get("day").asInt
                            val mentalConditionCode = it.asJsonObject.get("mentalConditionCode").asInt
                            result.add(monthMytamin(day,mentalConditionCode))
                        }
                        completion(RESPONSE_STATUS.OKAY,result)
                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    completion(RESPONSE_STATUS.FAIL,null)
                }

            })
    }

    fun getWeekMytamin(day:String,completion: (RESPONSE_STATUS, randomCare?) -> Unit){
        iHistoryRetrofit?.getWeekMytamin(day)?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }

        })

    }

    fun CareHistoryFilter(codeList:ArrayList<Int>,completion:(RESPONSE_STATUS, ArrayList<monthCareMytamin>?) -> Unit){
        val k =JSONObject( )
        k.put("careCategoryCodeList",codeList)
        iHistoryRetrofit?.getCarehistory(k)?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    val data = it.asJsonObject.get("data").asJsonObject
                    if(data.size() == 0){
                        completion(RESPONSE_STATUS.NO_CONTENT,null)
                    }else{
                        val k = data.keySet()
                        val result = ArrayList<monthCareMytamin>()
                        Log.d(TAG,"k set : $k")
                        k.forEach {
                            val caremytaminArray = ArrayList<careMytamin>()
                            val monthData = data.get("$it").asJsonArray
                            monthData.forEach {
                                val careMsg1 = it.asJsonObject.get("careMsg1").asString
                                val careMsg2 = it.asJsonObject.get("careMsg2").asString
                                val careCategory = it.asJsonObject.get("careCategory").asString
                                val takeAt = it.asJsonObject.get("takeAt").asString
                                val care = careMytamin(careMsg1, careMsg2 = careMsg2,careCategory, takeAt)
                                caremytaminArray.add(care)
                            }
                            val tmp = monthCareMytamin(time = it, arrayCareMytamin = caremytaminArray)
                            result.add(tmp)

                        }
                        completion(RESPONSE_STATUS.OKAY,result)
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.FAIL,null)

            }

        })
    }


}