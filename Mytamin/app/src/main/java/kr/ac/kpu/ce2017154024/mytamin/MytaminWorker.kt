package kr.ac.kpu.ce2017154024.mytamin

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.work.*
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class MytaminWorker(ctx: Context, params: WorkerParameters) :Worker(ctx,params) {
    override fun doWork(): Result {
        val appContext = applicationContext


        return try {
            wellcomAPICall()


            Result.success()
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error applying work")
            Result.failure()
        }
    }
    //액티비티에선 이런식으로 사용
//    internal fun applyBlur() {
//        workManager.enqueue(OneTimeWorkRequest.from(MytaminWorker::class.java))
//    }
    private fun wellcomAPICall(){
        HomeRetrofitManager.instance.doCompleteBreath {responseStatus ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(MyApplication.instance, "마이타민 숨 쉬기 완료", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }
    }
    private fun doworkMytaminWorkOneTime(){
        val workRequest = OneTimeWorkRequestBuilder<MytaminWorker>().build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(workRequest)
    }
    private fun doWorkWithConstraints() {
        // 네트워크 연결  상태를 제약조건으로 추가 한다
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 제약 조건과 함께 작업 생성
        val requestConstraint  = OneTimeWorkRequestBuilder<MytaminWorker>()
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(requestConstraint)
    }
}