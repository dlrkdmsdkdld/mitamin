package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.MyApplication
import kr.ac.kpu.ce2017154024.mytamin.retrofit.home.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class MytaminRepository {

    fun completeBreath(){
        with(CoroutineScope(Dispatchers.IO)){
            val job: Job = launch {
                HomeRetrofitManager.instance.doCompleteBreath { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY ->{
                            Handler(Looper.getMainLooper()).post{
                                Toast.makeText(MyApplication.instance, "마이타민 숨 쉬기 완료", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }
    }
    fun completeSense(){
        with(CoroutineScope(Dispatchers.IO)){
            val job: Job = launch {
                HomeRetrofitManager.instance.doCompleteSense { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY ->{
                            Handler(Looper.getMainLooper()).post{
                                Toast.makeText(MyApplication.instance, "마이타민 감각 깨우기 완료", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }
    }

}