package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import java.util.concurrent.TimeUnit

class todayMytaminViewModel :ViewModel() {
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
    fun timerPause(){
        if (::a.isInitialized) a.cancel()
    }
    //프로그래스 관련함수들
    var _progressPer = MutableLiveData<Int>()
    val progressPer : LiveData<Int>
        get() = _progressPer
    fun getInterval(): Observable<Long> =
        Observable.interval(1L, TimeUnit.MILLISECONDS).map { interval ->
            interval + 1
        }.take(100)
    //초기화
    init {
        _timerCount.value =10
        _progressPer.value=100
    }
}