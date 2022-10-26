package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import android.util.Log
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.model.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart

class InformationRetrofitManager {

    companion object{
        val instance=InformationRetrofitManager()
    }
    private val iInformationRetrofit:IInformationRetrofit? =TokenRetrofitClient.getClient()?.create(IInformationRetrofit::class.java)

    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())
    fun editProfile(file: MultipartBody.Part?, body: EditProfile, completion:(RESPONSE_STATUS, Int?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val bool : RequestBody = body.isImgEdited.toRequestBody()
            val nicknameRequestBody : RequestBody = body.nickname.toRequestBody()
            val beMyMessageRequestBody : RequestBody = body.beMyMessage.toRequestBody()
//            val beMyMessageRequestBody : RequestBody = body.beMyMessage.toPlainRequestBody()
            iInformationRetrofit?.editProfile(file = file, isImgEdited = bool, nickname = nicknameRequestBody,beMyMessage = beMyMessageRequestBody)
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        Log.d(TAG, "이미지전송성공response -> $response")
                        response.body()?.let {
                            val body = it.asJsonObject
                            val statusCode = body.get("statusCode").asInt
                            completion(RESPONSE_STATUS.OKAY, statusCode)
                            Log.d(TAG, "user doCompleteBreath response message:${statusCode} ")
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "이미지전송실패 이유 -> $t")
                        completion(RESPONSE_STATUS.FAIL, null)
                    }

                })
        }
    }
    fun getProfileData(completion:(RESPONSE_STATUS, ProfileData?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.getProfile()
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            Log.d(TAG, "user getWelcomeMessage onResponse ${response}" )
                            val data =it.asJsonObject.get("data").asJsonObject
                            val nickname = data.get("nickname").asString
                            var profileImgUrl:String? =null
                            if (!data.get("profileImgUrl").isJsonNull){
                                profileImgUrl = data.get("profileImgUrl").asString

                            }
                            val beMyMessage = data.get("beMyMessage").asString

                            val result = ProfileData(nickname=nickname,profileImgUrl=profileImgUrl ,beMyMessage=beMyMessage)

                            completion(RESPONSE_STATUS.OKAY,result)
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "user Login onFailure ${t}" )
                        completion(RESPONSE_STATUS.FAIL,null)
                    }

                })


        }


    }
    fun CorrectionBeMyMessage(beMyMessage:String,completion:(RESPONSE_STATUS,Int?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.CorrectionBeMyMessage(beMyMessage)
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            Log.d(TAG, "user CorrectionBeMyMessage onResponse ${response}" )
                            val body =it.asJsonObject
                            //TODO 잊지말고 토큰 추가해야함
                            val nickname = body.get("statusCode").asInt

                            completion(RESPONSE_STATUS.OKAY,nickname)

                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "CorrectionBeMyMessage onFailure ${t}" )
                        completion(RESPONSE_STATUS.OKAY,null)
                    }

                })
        }


    }

    fun CorrectionNickname(nickname:String,completion:(RESPONSE_STATUS,Int?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.CorrectionNickname(nickname)
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        response.body()?.let {
                            Log.d(TAG, "user CorrectionNickname onResponse ${response}")
                            val body = it.asJsonObject
                            //TODO 잊지말고 토큰 추가해야함
                            val nickname = body.get("statusCode").asInt
                            completion(RESPONSE_STATUS.OKAY, nickname)
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "CorrectionNickname onFailure ${t}")
                        completion(RESPONSE_STATUS.OKAY, null)
                    }

                })
        }
    }
    var job: Job? = null
    fun getMyday(completion:(RESPONSE_STATUS,MydayData?) -> Unit){
         CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.getMyday()
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            Log.d(TAG, "user getMyday onResponse ${response}" )
                            val data =it.asJsonObject.get("data").asJsonObject
                            //TODO 잊지말고 토큰 추가해야함
                            val myDayMMDD = data.get("myDayMMDD").asString
                            val dday = data.get("dday").asString
                            val comment = data.get("comment").asString
                            val result = MydayData(myDayMMDD,dday,comment)
                            completion(RESPONSE_STATUS.OKAY,result)
                        }
                    }
                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "getMyday onFailure ${t}" )
                        completion(RESPONSE_STATUS.OKAY,null)
                    }

                })
        }


    }
//    fun getMyday(completion:(RESPONSE_STATUS,MydayData?) -> Unit){
//        iInformationRetrofit?.getMyday()
//            ?.enqueue(object : retrofit2.Callback<JsonElement> {
//                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                    response.body()?.let {
//                        Log.d(TAG, "user getMyday onResponse ${response}" )
//                        val data =it.asJsonObject.get("data").asJsonObject
//                        //TODO 잊지말고 토큰 추가해야함
//                        val myDayMMDD = data.get("myDayMMDD").asString
//                        val dday = data.get("dday").asString
//                        val comment = data.get("comment").asString
//                        val result = MydayData(myDayMMDD,dday,comment)
//                        completion(RESPONSE_STATUS.OKAY,result)
//                    }
//                }
//                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                    Log.d(TAG, "getMyday onFailure ${t}" )
//                    completion(RESPONSE_STATUS.OKAY,null)
//                }
//
//            })
//
//    }
    fun sendNewWishlist(wishtext: String,completion:(RESPONSE_STATUS, WishList?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.sendNewWishlist(wishtext)?.enqueue(object :retrofit2.Callback<JsonElement>{
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    response.body()?.let {
                        val statusCode = it.asJsonObject.get("statusCode").asInt
                        if (statusCode == 201){
                            val wishId = it.asJsonObject.get("data").asJsonObject.get("wishId").asInt
                            val wishText = it.asJsonObject.get("data").asJsonObject.get("wishText").asString
                            val count = it.asJsonObject.get("data").asJsonObject.get("count").asInt
                            val result = WishList(wishId,wishText,count)
                            completion(RESPONSE_STATUS.OKAY,result)
                        }
                        completion(RESPONSE_STATUS.FAIL,null)

                    }

                }

                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    completion(RESPONSE_STATUS.FAIL,null)
                }

            })
        }

    }
    fun getWishlist(completion:(RESPONSE_STATUS, ArrayList<WishList>?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.getWishlist()
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            Log.d(TAG, "user getWishlist onResponse ${response}")
                            val data = it.asJsonObject.get("data").asJsonArray
                            val dataCount = data.count()
//                            val pulished = data.get("published").asJsonArray
//                            val pulishedCount = pulished.count()
//                            val hidden = data.get("hidden").asJsonArray
//                            val hiddenCount = hidden.count()
                            if (dataCount == 0) {
                                completion(RESPONSE_STATUS.NO_CONTENT, null)
                            } else {
                                Log.d(TAG, "pulished.count() ${data.count()} ")
                                val totalWishList =ArrayList<WishList>()
                                data.forEach {
                                    val resultItem = it.asJsonObject
                                    val wishId =resultItem.get("wishId").asInt
                                    val wishText =resultItem.get("wishText").asString
                                    val count =resultItem.get("count").asInt
                                    val tmp = WishList(wishId,wishText,count)
                                    totalWishList.add(tmp)
                                }
                                completion(RESPONSE_STATUS.OKAY, totalWishList)
                            }


                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "getWishlist onFailure ${t}")
                         completion(RESPONSE_STATUS.FAIL,null)
                    }

                })
            }
        }
    fun getDaynote(completion:(RESPONSE_STATUS, WishList?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.getDaynote()
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            Log.d(TAG, "user getWishlist onResponse $response")
                            val data = it.asJsonObject.get("data").asJsonObject
                            if (data.size()==0){
                                completion(RESPONSE_STATUS.NO_CONTENT, null)
                                Log.d(TAG, "daynoteList onResponse nudaynoteList.size() 0")

                            }else{
                                Log.d(TAG, "daynoteList onResponse daynoteList.size() ->${data.size()}")

                            }


                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "getWishlist onFailure ${t}")
                        // completion(RESPONSE_STATUS.OKAY,null)
                    }

                })
        }
    }
    fun imageListAPI(file: List<MultipartBody.Part?>, completion:(RESPONSE_STATUS, Int?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.sendImageList(file = file)
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        Log.d(TAG, "이미지전송성공response -> $response")
                        response.body()?.let {
                            val body = it.asJsonObject
                            val statusCode = body.get("statusCode").asInt
                            completion(RESPONSE_STATUS.OKAY, statusCode)
                            Log.d(TAG, "imageListAPI response message:${statusCode} ")
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "이미지전송실패 이유 -> $t")
                        completion(RESPONSE_STATUS.FAIL, null)
                    }

                })
        }
    }
    fun checkrecord(day:String,completion:(RESPONSE_STATUS, Boolean?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            iInformationRetrofit?.checkDaynote(day)
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                        response.body()?.let {
                            Log.d(TAG, "user checkrecord onResponse $response")
                            val data = it.asJsonObject.get("data").asBoolean
                            completion(RESPONSE_STATUS.OKAY,data)

                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "checkrecord onFailure ${t}")
                        completion(RESPONSE_STATUS.OKAY,null)
                    }

                })
        }
    }
    //@Part file:List<MultipartBody.Part?>, @Part("wishText") wishText: RequestBody,@Part("note") note: RequestBody
    //        ,@Part("date") date: RequestBody

    fun newdaynote(file: List<MultipartBody.Part?>, wishtext :String,note:String,date:String, completion:(RESPONSE_STATUS, Int?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val wishtextRequestBody : RequestBody = wishtext.toRequestBody()
            val noteRequestBody : RequestBody = note.toRequestBody()
            val dateRequestBody : RequestBody = date.toRequestBody()
            iInformationRetrofit?.newDaynote(file = file, wishText =wishtextRequestBody, note = noteRequestBody, date = dateRequestBody )
                ?.enqueue(object : retrofit2.Callback<JsonElement> {
                    override fun onResponse(
                        call: Call<JsonElement>,
                        response: Response<JsonElement>
                    ) {
                        Log.d(TAG, "데이노트 작성 성공 response -> $response")
                        response.body()?.let {
                            val body = it.asJsonObject
                            val statusCode = body.get("statusCode").asInt
                            completion(RESPONSE_STATUS.OKAY, statusCode)
                            Log.d(TAG, "데이노트 작성 성공h response message:${statusCode} ")
                        }
                    }

                    override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                        Log.d(TAG, "데이노트 작성 실패 이유 -> $t")
                        completion(RESPONSE_STATUS.FAIL, null)
                    }

                })
        }
    }
}