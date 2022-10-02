package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kr.ac.kpu.ce2017154024.mytamin.Repository.MytaminRepository
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData

class todayMytaminViewModel :ViewModel() {
    private val mytaminRepository= MytaminRepository()  //레포지토리 ##
    private val emojiState = MutableLiveData<Int>()
    val selectemojiState : LiveData<Int>
        get() = emojiState
    fun setselectemojiState(i:Int){
        emojiState.value = i
    }
    private val diagnosis = MutableLiveData<List<String>>()
    val selectediagnosis : LiveData<List<String>>
        get() = diagnosis
    fun setdiagnosis(i:List<String>){
        diagnosis.value = i
    }
    private val step = MutableLiveData<Int>()
    val getstep : LiveData<Int>
        get() = step
    fun setstep(inputstep:Int){
        step.value = inputstep
    }
    ///타이머 카운트 변수관련 함수들
    private val _timerCount = MutableLiveData<Int>()
    private lateinit var a: Job
    val timerCount : LiveData<Int>
        get() = _timerCount
    fun timerStart(){
        a=viewModelScope.launch {
            while (_timerCount.value!! > 0 ){
                _timerCount.value = _timerCount.value!!.minus(1)
                delay(1000L)
            }
        }
    }
    fun timerset(time:Int){
        _timerCount.value = time
    }
    fun timerDestory(){
        viewModelScope.launch {
            //job이 초기화 됐는지 확인하고 완료되지않았을때 돌림
            if ( ::a.isInitialized &&a.isCompleted==false  ){
            a.cancelAndJoin()
            }
        }
    }
    fun timerPause(){
        if (::a.isInitialized) a.cancel()
    }
    var _auto = MutableLiveData<Boolean>()
    val auto : LiveData<Boolean>
        get() = _auto
    fun autoset(data:Boolean){
        _auto.value=data
    }
    var _month = MutableLiveData<Int>()
    val month : LiveData<Int>
        get() = _month
    fun monthset(data:Int){
        _month.value=data
    }
    var _day= MutableLiveData<Int>()
    val day : LiveData<Int>
        get() = _day
    fun dayset(data:Int){
        _day.value=data
    }
    fun completeBreath(){
        mytaminRepository.completeBreath()
    }
    fun completeSense(){
        mytaminRepository.completeSense()
    }
    fun completeReport(){
        var inputdata:ReportData
        when(selectediagnosis.value!!.count()){
            1 -> inputdata=ReportData(mentalConditionCode = selectemojiState.value!!, tag1 =selectediagnosis!!.value!!.get(0) ,todayReport=report.value!!)
            2 -> inputdata=ReportData(mentalConditionCode = selectemojiState.value!!, tag1 =selectediagnosis!!.value!!.get(0) , tag2 = selectediagnosis!!.value!!.get(1) ,todayReport=report.value!!)
            else -> inputdata=ReportData(mentalConditionCode = selectemojiState.value!!, tag1 =selectediagnosis!!.value!!.get(0) , tag2 = selectediagnosis!!.value!!.get(1), tag3 =selectediagnosis!!.value!!.get(2),todayReport=report.value!!)
        }
        mytaminRepository.completeReport(inputdata)
    }
    var _report = MutableLiveData<String>()
    val report : LiveData<String>
        get() = _report
    fun reportset(data:String){
        _report.value=data
    }
//    with(CoroutineScope(Dispatchers.IO)){
//        val job: Job = launch { completeBreath() }
//    }

//    if(returnday!=0){
//        //(activity as todayMytaminActivity).submitDay(returnmonth+1,returnday)}
//    }
}