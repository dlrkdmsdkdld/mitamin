package kr.ac.kpu.ce2017154024.mytamin.retrofit.home

import android.util.Log
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kr.ac.kpu.ce2017154024.mytamin.model.*
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
    fun doCompleteBreath(completion:(RESPONSE_STATUS) -> Unit){
        iHomeRetrofit?.completeBreath()
            ?.enqueue(object : retrofit2.Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        //TODO 잊지말고 토큰 추가해야함
                        val message = body.get("message").asString
                        val updatedTime = body.get("data").asJsonObject.get("updatedTime").asString
                        Log.d(TAG, "user doCompleteBreath response message:${message}  updatedTime:$updatedTime" )
                        completion(RESPONSE_STATUS.OKAY)

                    }
                }
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL)
                }

            })

    }
    fun doCompleteSense(completion:(RESPONSE_STATUS) -> Unit){
        iHomeRetrofit?.completeSense()
            ?.enqueue(object : retrofit2.Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        //TODO 잊지말고 토큰 추가해야함
                        val message = body.get("message").asString
                        val updatedTime = body.get("data").asJsonObject.get("updatedTime").asString
                        Log.d(TAG, "user doCompleteBreath response message:${message}  updatedTime:$updatedTime" )
                        completion(RESPONSE_STATUS.OKAY)

                    }
                }
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL)
                }

            })

    }
    fun doCompleteReport(inputdata:ReportData,completion: (RESPONSE_STATUS) -> Unit){
        iHomeRetrofit?.completeReport(inputdata)
            ?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        val message = body.get("message").asString
                        Log.d(TAG, "user doCompleteReport response message:${message} "  )
                        completion(RESPONSE_STATUS.OKAY)

                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL)
                }

            })
    }

    fun doCorrectionReportReport(inputdata:ReportData,reportId:Int,completion: (RESPONSE_STATUS) -> Unit){
        iHomeRetrofit?.correctionReport(inputdata,reportId)
            ?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        val message = body.get("message").asString
                        Log.d(TAG, "user doCompleteReport response message:${message} "  )
                        completion(RESPONSE_STATUS.OKAY)

                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL)
                }

            })
    }

    fun doCompleteCare(inputdata:CareData,completion: (RESPONSE_STATUS) -> Unit){
        iHomeRetrofit?.completeCare(inputdata)
            ?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        val message = body.get("message").asString
                        //val takeAtTime = body.get("data").asJsonObject.get("takeAt").asString
                        Log.d(TAG, "user doCompleteReport response message:${message}  " )
                        completion(RESPONSE_STATUS.OKAY)

                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL)
                }

            })
    }
    fun docorrectionCare(inputdata:CareData,careId:Int,completion: (RESPONSE_STATUS) -> Unit){
        iHomeRetrofit?.correctionCare(inputdata, careId )
            ?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        val message = body.get("message").asString
                        //val takeAtTime = body.get("data").asJsonObject.get("takeAt").asString
                        Log.d(TAG, "user docorrectionCare response message:${message}  " )
                        completion(RESPONSE_STATUS.OKAY)

                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL)
                }

            })
    }

    fun getStatus(completion: (RESPONSE_STATUS,Status?) -> Unit){
        iHomeRetrofit?.getStatus()
            ?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        val message = body.get("message").asString
                        val breathIsDone = body.get("data").asJsonObject.get("breathIsDone").asBoolean
                        val senseIsDone = body.get("data").asJsonObject.get("senseIsDone").asBoolean
                        val reportIsDone = body.get("data").asJsonObject.get("reportIsDone").asBoolean
                        val careIsDone = body.get("data").asJsonObject.get("careIsDone").asBoolean
                        val result = Status(breathIsDone,senseIsDone,reportIsDone,careIsDone)
                        Log.d(TAG, "user getStatus response message:${message}  result:$result" )
                        completion(RESPONSE_STATUS.OKAY,result)

                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL,null)
                }

            })
    }
//    data class LatestMytamin(val takeAt:String="",val canEdit:Boolean,val mentalConditionCode:Int=0
//                             ,val feelingTag:String ="", val mentalConditionMsg:String="" , val todayReport:String="" ,val  careMsg1:String="",val careMsg2:String=""
//    ) {
//    }



    fun getlatestMytamin (status: Status , completion: (RESPONSE_STATUS,LatestMytamin?) -> Unit){
        iHomeRetrofit?.getlatestMytamin()
            ?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val body =it.asJsonObject
                        val data = body.get("data").asJsonObject
                        if (status.reportIsDone ==true && status.careIsDone == false ){ //진단하기만하고 칭찬처방은 안한경우
                            val takeAtTime = data.get("takeAt").asString
                            //val canEditCare = data.get("canEditCare").asBoolean
                            val report = data.get("report").asJsonObject
                            val canEditReport = report.get("canEdit").asBoolean
                            val reportId = report.get("reportId").asInt
                            val mentalConditionCode = report.get("mentalConditionCode").asInt
                            val feelingTag = report.get("feelingTag").asString
                            val mentalConditionMsg =report.get("mentalCondition").asString
                            val todayReport = report.get("todayReport").asString
                            val result = LatestMytamin(takeAtTime,canEditReport,false,reportId,mentalConditionCode,feelingTag,mentalConditionMsg,todayReport)
                            completion(RESPONSE_STATUS.OKAY,result)

                        }
                        else if(status.reportIsDone ==true && status.careIsDone ==true){//진단하기,칭찬처방 둘다한경우우
                           val message = body.get("message").asString
                            val takeAtTime = data.get("takeAt").asString

                            val report = data.get("report").asJsonObject
                            val reportId = report.get("reportId").asInt
                            val canEditReport = report.get("canEdit").asBoolean
                            val mentalConditionCode = report.get("mentalConditionCode").asInt
                            val feelingTag = report.get("feelingTag").asString
                            val mentalConditionMsg =report.get("mentalCondition").asString
                            val todayReport = report.get("todayReport").asString

                            val care = data.get("care").asJsonObject
                            val careId = care.get("careId").asInt
                            val careMsg1 = care.get("careMsg1").asString
                            val careMsg2 = care.get("careMsg2").asString
                            val canEditCare = care.get("canEdit").asBoolean
                            val careCategory = care.get("careCategory").asString

                            val result = LatestMytamin(takeAtTime,canEditReport,canEditCare,reportId,mentalConditionCode,feelingTag,mentalConditionMsg,todayReport,careId,careCategory,careMsg1,careMsg2)
                            Log.d(TAG, "user doCompleteReport response message:${message}  updatedTime:$takeAtTime" )
                            completion(RESPONSE_STATUS.OKAY,result)

                        }else if (status.careIsDone ==true && status.reportIsDone ==false){
                            val takeAtTime = body.get("data").asJsonObject.get("takeAt").asString

                            val care = data.get("care").asJsonObject
                            val careId = care.get("careId").asInt
                            val careMsg1 = care.get("careMsg1").asString
                            val careMsg2 = care.get("careMsg2").asString
                            val canEditCare = care.get("canEdit").asBoolean
                            val result = LatestMytamin(takeAt = takeAtTime,canEditReport=false,canEditCare=canEditCare,careId = careId, careMsg1 = careMsg1, careMsg2 = careMsg2)
                            completion(RESPONSE_STATUS.OKAY,result)

                        }


                    }
                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d(TAG, "user doCompleteBreath fail${t}" )
                    completion(RESPONSE_STATUS.FAIL,null)
                }

            })
    }


}