package kr.ac.kpu.ce2017154024.mytamin.Repository

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.MyApplication
import kr.ac.kpu.ce2017154024.mytamin.model.CareData
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
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
    fun completeReport(data:ReportData){
        with(CoroutineScope(Dispatchers.IO)){
            val job:Job = launch {
                HomeRetrofitManager.instance.doCompleteReport(data) { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY ->{
                            Handler(Looper.getMainLooper()).post{
                                Toast.makeText(MyApplication.instance, "마이타민 하루진단하기 완료", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }
    }
    fun CorrectionReport(data:ReportData,reportId:Int){
        with(CoroutineScope(Dispatchers.IO)){
            val job:Job = launch {
                HomeRetrofitManager.instance.doCorrectionReportReport(data,reportId) { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY ->{
                            Handler(Looper.getMainLooper()).post{
                                Toast.makeText(MyApplication.instance, "마이타민 하루진단하기 완료", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }
    }
    fun completeCare(data:CareData){
        with(CoroutineScope(Dispatchers.IO)){
            val job:Job = launch {
                HomeRetrofitManager.instance.doCompleteCare(data) { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY ->{
//                            Handler(Looper.getMainLooper()).post{
//                                Toast.makeText(MyApplication.instance, "마이타민 칭찬 처방하기 완료", Toast.LENGTH_SHORT).show()
//                            }

                        }
                    }

                }
            }
        }
    }
    fun correctionCare(data:CareData,careId: Int){
        with(CoroutineScope(Dispatchers.IO)){
            val job:Job = launch {
                HomeRetrofitManager.instance.docorrectionCare(data,careId) { responseStatus ->
                    when(responseStatus){
                        RESPONSE_STATUS.OKAY ->{
                            Handler(Looper.getMainLooper()).post{
                                Toast.makeText(MyApplication.instance, "마이타민 칭찬 처방하기 완료", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }
            }
        }
    }

}