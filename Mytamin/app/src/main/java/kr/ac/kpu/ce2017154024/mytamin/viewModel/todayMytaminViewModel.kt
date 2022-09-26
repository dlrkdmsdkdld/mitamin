package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import java.util.concurrent.TimeUnit

class todayMytaminViewModel :ViewModel() {


    private val emojiState = MutableLiveData<Int>()
    val selectemojiState : LiveData<Int>
        get() = step
    fun setselectemojiState(emojiState:Int){
        step.value = emojiState
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
    //프로그래스 관련함수들
    var _auto = MutableLiveData<Boolean>()
    val auto : LiveData<Boolean>
        get() = _auto
    fun autoset(data:Boolean){
        _auto.value=data
    }

}