package kr.ac.kpu.ce2017154024.mytamin.Repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import kr.ac.kpu.ce2017154024.mytamin.MyApplication
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.retrofit.home.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class HomeRepository {
//   suspend fun getLatestMytamin(): LatestMytamin?{
//        var result: LatestMytamin? = null
//        with(CoroutineScope(Dispatchers.IO)){
//            val job: Job = launch {
//                HomeRetrofitManager.instance.getlatestMytamin(){ responseStatus, LastMytamin->
//                    when(responseStatus){
//                        RESPONSE_STATUS.OKAY ->{
//                            result= LastMytamin!!
//                            Log.d(TAG,"데이터 받아옴 getLatestMytamin ->$result ")
//
//
//                        }
//
//                    }
//
//                }
//            }
//            job.
//        }
//
//        return result
//
//    }


//    val LatestMytamin: Deferred<LatestMytamin> = CoroutineScope(Dispatchers.IO).async{
//        var result: LatestMytamin? = null
//        HomeRetrofitManager.instance.getlatestMytamin(){
//                responseStatus, LastMytamin->
//            when(responseStatus){
//                RESPONSE_STATUS.OKAY ->{
//                    result= LastMytamin!!
//                    Log.d(TAG,"데이터 받아옴 getLatestMytamin ->${result} ")
//
//                }
//            }
//
//        }
//        result!!
//    }
//    suspend fun getLatestMytamin() {
//        lateinit var result:LatestMytamin
//        val b = withContext(Dispatchers.IO){
//            HomeRetrofitManager.instance.getlatestMytamin(){
//                    responseStatus, LastMytamin->
//                when(responseStatus){
//                    RESPONSE_STATUS.OKAY ->{
//                        result= LastMytamin!!
//                        //Log.d(TAG,"데이터 받아옴 getLatestMytamin ->${result} ")
//                    }
//                }
//            }
//            return@withContext result
//        }
//
//
//    }

    suspend fun getLatestMytamin(): LatestMytamin? {
        val job :Deferred<LatestMytamin?> = CoroutineScope(Dispatchers.IO).async {
            lateinit var result:LatestMytamin
             HomeRetrofitManager.instance.getlatestMytamin(){
                    responseStatus, LastMytamin->
                when(responseStatus){
                    RESPONSE_STATUS.OKAY ->{
                        result= LastMytamin!!
                        //Log.d(TAG,"데이터 받아옴 getLatestMytamin ->${result} ")
                    }
                }
            }

            result
        }

        val tmp = job.await()

        Log.d(TAG,"데이터 받아옴 getLatestMytamin ->${tmp} ")
        return tmp
       // Log.d(TAG,"${k} ob.await()job.await()job.await()job.await()job.await()job.await()값은 ")
      //return result?



    }
}