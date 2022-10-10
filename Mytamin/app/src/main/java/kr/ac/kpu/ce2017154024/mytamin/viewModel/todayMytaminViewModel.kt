package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kr.ac.kpu.ce2017154024.mytamin.Repository.MytaminRepository
import kr.ac.kpu.ce2017154024.mytamin.model.CareData
import kr.ac.kpu.ce2017154024.mytamin.model.ReportData
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton

class todayMytaminViewModel :ViewModel() {
    private val mytaminRepository= MytaminRepository()  //레포지토리 ##


    private val status = MutableLiveData<Status>()
    val getstatus : LiveData<Status>
        get() = status

    fun setstatus(time: Status){
        status.value = time
    }

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

    fun completeBreath(){
        mytaminRepository.completeBreath()
    }
    fun completeSense(){
        mytaminRepository.completeSense()
    }
    fun CorrectionReport(reportId:Int){
        var inputdata:ReportData
        when(selectediagnosis.value!!.count()){
            1 -> inputdata=ReportData(mentalConditionCode = selectemojiState.value!!, tag1 =selectediagnosis!!.value!!.get(0) ,todayReport=report.value!!)
            2 -> inputdata=ReportData(mentalConditionCode = selectemojiState.value!!, tag1 =selectediagnosis!!.value!!.get(0) , tag2 = selectediagnosis!!.value!!.get(1) ,todayReport=report.value!!)
            else -> inputdata=ReportData(mentalConditionCode = selectemojiState.value!!, tag1 =selectediagnosis!!.value!!.get(0) , tag2 = selectediagnosis!!.value!!.get(1), tag3 =selectediagnosis!!.value!!.get(2),todayReport=report.value!!)
        }
        mytaminRepository.CorrectionReport(inputdata,reportId)
        //mytaminRepository.CorrectionReport(inputdata,"가탄")
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

    ///마이타민 5단계
    private val careCategoryCode = MutableLiveData<Int>()
    val getcareCategoryCode : LiveData<Int>
        get() = careCategoryCode
    fun setcareCategoryCode(inputstep:Int){
        careCategoryCode.value = inputstep
    }
    private val careCategoryCodeMsg = MutableLiveData<String>()
    val getcareCategoryCodeMsg : LiveData<String>
        get() = careCategoryCodeMsg
    fun setcareCategoryCodeMsg(inputstep:String){
        careCategoryCodeMsg.value = inputstep
    }
    private val careMsg1 = MutableLiveData<String>()
    val getcareMsg1 : LiveData<String>
        get() = careMsg1
    fun setcareMsg1(inputstep:String){
        careMsg1.value = inputstep
    }
    private val careMsg2 = MutableLiveData<String>()
    val getcareMsg2: LiveData<String>
        get() = careMsg2
    fun setcareMsg2(inputstep:String){
        careMsg2.value = inputstep
    }
    ///마이타민 6단계
    fun completeCare(){
        val inputdata = CareData(careCategoryCode = getcareCategoryCode.value!!, careMsg1 =  getcareMsg1.value!!, careMsg2 = getcareMsg2.value!!  )
        mytaminRepository.completeCare(inputdata)
    }
    fun correctionCare(careId: Int){
        val inputdata = CareData(careCategoryCode = getcareCategoryCode.value!!, careMsg1 =  getcareMsg1.value!!, careMsg2 = getcareMsg2.value!!  )
        mytaminRepository.correctionCare(inputdata,careId)
    }

//    with(CoroutineScope(Dispatchers.IO)){
//        val job: Job = launch { completeBreath() }
//    }

//    if(returnday!=0){
//        //(activity as todayMytaminActivity).submitDay(returnmonth+1,returnday)}
//    }
}