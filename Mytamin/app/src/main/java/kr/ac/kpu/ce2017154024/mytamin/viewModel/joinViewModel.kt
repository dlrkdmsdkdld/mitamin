package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import java.util.concurrent.TimeUnit

class joinViewModel: ViewModel() {
    private val id = MutableLiveData<String>()
    val getid : LiveData<String>
        get() = id
    fun setid(time:String){
        id.value = time
    }
    private val password = MutableLiveData<String>()
    val getpassword : LiveData<String>
        get() = password
    fun setpassword(time:String){
        password.value = time.trim()
    }

    private val email = MutableLiveData<String>()
    val getemail : LiveData<String>
        get() = email
    fun setemail(time:String){
        email.value = time.trim()
        Log.d(TAG,"joinViewModel emailValue -> ${email.value}")
    }

    private val name = MutableLiveData<String>()
    val getname : LiveData<String>
        get() = name
    fun setname(time:String){
        name.value = time.trim()
    }
    //프로그래스 관련함수들
    var _progressPer = MutableLiveData<Int>()
    val progressPer : LiveData<Int>
        get() = _progressPer
    fun getInterval(): Observable<Long> =
        Observable.interval(1L, TimeUnit.MILLISECONDS).map { interval ->
            interval + 1
        }.take(200)
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
    fun timerset(){
        _timerCount.value = 300
    }
    fun timerDestory(){
        viewModelScope.launch {
            //job이 초기화 됐는지 확인하고 완료되지않았을때 돌림
            if ( ::a.isInitialized &&a.isCompleted==false  ){
                a.cancelAndJoin()
            }
        }
    }


}