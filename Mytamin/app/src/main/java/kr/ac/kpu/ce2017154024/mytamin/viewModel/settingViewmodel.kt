package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.model.mydayAlarm
import kr.ac.kpu.ce2017154024.mytamin.model.mytaminAlarm
import kr.ac.kpu.ce2017154024.mytamin.model.mytaminAlarmTime
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class settingViewmodel:ViewModel() {


    private val mytaminAlarmData = MutableLiveData<mytaminAlarm>()
    val getmytaminAlarmData: LiveData<mytaminAlarm>
        get() = mytaminAlarmData

    fun setmytaminAlarmData(time: mytaminAlarm?){
        time?.let {  mytaminAlarmData.value=it}
    }
    private val myDayData = MutableLiveData<mydayAlarm>()
    val getmyDayData: LiveData<mydayAlarm>
        get() = myDayData

    fun setmyDayData(time: mydayAlarm?){
        time?.let {  myDayData.value=it}
    }

    private val mytaminTime = MutableLiveData<mytaminAlarmTime>()
    val getmytaminTime: LiveData<mytaminAlarmTime>
        get() = mytaminTime

    fun setmytaminTime(time: mytaminAlarmTime?){
        time?.let {  mytaminTime.value=it}
    }


    init {
        getAlarmAPI()
    }
    fun getAlarmAPI(){
        HistoryRetrofitManager.instance.getAlarmState{responseStatus, mytaminAlarm, mydayAlarm ->
            setmytaminAlarmData(mytaminAlarm)
            setmyDayData(mydayAlarm)
        }
    }
    fun mytaminAlarmOn(data:mytaminAlarmTime){
        HistoryRetrofitManager.instance.mytaminAlarmOn(data){responseStatus: RESPONSE_STATUS ->
            getAlarmAPI()
        }
    }


}