package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.gson.*
import kr.ac.kpu.ce2017154024.mytamin.MyApplication
import kr.ac.kpu.ce2017154024.mytamin.model.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.logging.Handler

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

    fun getWeekMytamin(day:String,completion: (RESPONSE_STATUS, ArrayList<dayMytamin>?) -> Unit){
        iHistoryRetrofit?.getWeekMytamin(day)?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var result = ArrayList<dayMytamin>()
                response.body()?.let {
                    val data = it.asJsonObject.get("data").asJsonObject
                    val iterator = data.keySet()

                    iterator.forEach {
                        if(data.get(it).isJsonNull){
                            result.add(dayMytamin(day=it , null,null,null,null))
                        }else{ // 안에 report 혹은 care 내용이 있는경우
                            val tmp = data.get(it).asJsonObject
                            val mytaminId =tmp.get("mytaminId").asInt
                            val takeAt = tmp.get("takeAt").asString
                            var report :dayMytaminReport?
                            if (tmp.get("report").isJsonNull){
                                report =null
                            }else{
                                val reportId = tmp.get("report").asJsonObject.get("reportId").asInt
                                val canEdit = tmp.get("report").asJsonObject.get("canEdit").asBoolean
                                val mentalConditionCode = tmp.get("report").asJsonObject.get("mentalConditionCode").asInt
                                val mentalCondition = tmp.get("report").asJsonObject.get("mentalCondition").asString
                                val feelingTag = tmp.get("report").asJsonObject.get("feelingTag").asString
                                val todayReport = tmp.get("report").asJsonObject.get("todayReport").asString
                                report = dayMytaminReport(reportId, canEdit, mentalConditionCode, mentalCondition, feelingTag, todayReport)
                            }
                            var care :dayMytaminCare?
                            if (tmp.get("care").isJsonNull){
                                care =null
                            }else{
                                val careId = tmp.get("care").asJsonObject.get("careId").asInt
                                val canEdit = tmp.get("care").asJsonObject.get("canEdit").asBoolean
                                val careCategory = tmp.get("care").asJsonObject.get("careCategory").asString
                                val careMsg1 = tmp.get("care").asJsonObject.get("careMsg1").asString
                                val careMsg2 = tmp.get("care").asJsonObject.get("careMsg2").asString
                                care = dayMytaminCare(careId, canEdit, careCategory, careMsg1, careMsg2)
                            }
                            val total = dayMytamin(day = it,mytaminId=mytaminId,takeAt=takeAt,report=report,care=care)
                            result.add(total)

                        }
                    }
                    completion(RESPONSE_STATUS.OKAY,result)
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.FAIL,null)

            }

        })

    }

    fun CareHistoryFilter(codeList:ArrayList<Int>,completion:(RESPONSE_STATUS, ArrayList<monthCareMytamin>?) -> Unit){
        val tmp = careCategoryCodeList(codeList)
        iHistoryRetrofit?.getCarehistory(tmp)?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    val data = it.asJsonObject.get("data").asJsonObject
                    if(data.size() == 0){
                        completion(RESPONSE_STATUS.NO_CONTENT,null)
                    }else{
                        val ketset = data.keySet()
                        val result = ArrayList<monthCareMytamin>()
                        Log.d(TAG,"k set : $ketset")
                        ketset.forEach {
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

    fun deleteMytamin(DeleteMytamin:Int,completion: (RESPONSE_STATUS, Int?) -> Unit){
        iHistoryRetrofit?.DeleteMytamin(DeleteMytamin)?.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                response.body()?.let {
                    val statusCode = it.asJsonObject.get("statusCode").asInt
                    if (statusCode ==200){
                        completion(RESPONSE_STATUS.OKAY,200)
                    }else{
                        completion(RESPONSE_STATUS.FAIL,null)

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                completion(RESPONSE_STATUS.FAIL,null)

            }

        })
    }
}