package kr.ac.kpu.ce2017154024.mytamin.retrofit.token

import android.util.Log
import kr.ac.kpu.ce2017154024.mytamin.BuildConfig
import kr.ac.kpu.ce2017154024.mytamin.utils.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TokenRetrofitClient {
    private var TokenRetrofitClient: Retrofit? = null

    //레트로핏 클라이언트 가져오기
    fun getClient(): Retrofit?{
        Log.d(Constant.TAG,"RetorfitClient - getClient() called" )
        //okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()
        //로그를 찍기 위해 로깅 인터셉터 설정
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(Constant.TAG,"RetrofitClient - log() called / message: $message")
                when{
                    message.isJsonObject() -> Log.d(Constant.TAG, JSONObject(message).toString(4))

                    message.isJsonArray() -> Log.d(Constant.TAG, JSONArray(message).toString(4))
                    else ->{
                        try {
                            Log.d(Constant.TAG, JSONObject(message).toString(4))
                        }catch (e:Exception){
                            Log.d(Constant.TAG,message)
                        }
                    }

                }
            }

        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(loggingInterceptor)
        //기본 파라미터 인터셉터 설정
        val baseParameterInterceptor : Interceptor = (object  : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(Constant.TAG,"RetrofitClient - Interceptor called ")
                var originalRequest = chain.request().newBuilder()
                    .addHeader("X-AUTH-TOKEN", "${PreferenceUtil.obtainToken()}" )
                    .build()
                 //   .addHeader("X-AUTH-TOKEN", "${PrivateUserDataSingleton.accessToken}" )
                  //  Log.d(TAG,"변수에서 토큰 가져옴1 ${PrivateUserDataSingleton.accessToken} ")
                  //  Log.d(TAG,"변수에서 토큰 가져옴2 ${PreferenceUtil.obtainToken()} ")

//                if(PrivateUserDataSingleton.isTokenINitialized()){//토큰초기화됐는지확인하고 토큰넣음
//                     originalRequest = chain.request().newBuilder()
//                        .addHeader("X-AUTH-TOKEN", "${PrivateUserDataSingleton.accessToken}" )
//                        .build()
//                    Log.d(TAG,"변수에서 토큰 가져옴 ${PrivateUserDataSingleton.accessToken} ")
//                }else{
//                    Log.d(TAG,"기기에서 토큰가져옴 ${PreferenceUtil.obtainToken()} ")
//                    originalRequest = chain.request().newBuilder()
//                        .addHeader("X-AUTH-TOKEN", "${PreferenceUtil.obtainToken()}" )
//                        .build()
//                }
               // Log.d(TAG,"추가한 토큰 ${PrivateUserDataSingleton.accessToken} ")
                val finalRequest = originalRequest.newBuilder()
                    .method(originalRequest.method,originalRequest.body)
                    .build()

                val response = chain.proceed(finalRequest)
//                if(response.code !=200){ //응답코드가 200이아닐때
//                    Handler(Looper.getMainLooper()).post{
//                        Log.d(TAG,"RetrofitClient - Interceptor 오류오류발생 !!response ")
//                        Toast.makeText(MyApplication.instance, "${response.code} 에러입니다", Toast.LENGTH_SHORT).show()
//                    }
//                }

                return response
            }

        })

        //위에서 설정한 기본인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(baseParameterInterceptor)
        //커넷션 타임아웃
        // client.connectTimeout(10, TimeUnit.SECONDS)
        //  client.readTimeout(10, TimeUnit.SECONDS)
        // client.writeTimeout(10, TimeUnit.SECONDS)
        //client.retryOnConnectionFailure(true) // 실패했을때 다시시도



        if(TokenRetrofitClient == null){
            //레트로핏 빌더
            TokenRetrofitClient = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

                //위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()
        }
        return TokenRetrofitClient
    }
}