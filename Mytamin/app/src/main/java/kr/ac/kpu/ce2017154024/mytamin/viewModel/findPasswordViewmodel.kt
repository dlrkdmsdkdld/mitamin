package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class findPasswordViewmodel :ViewModel(){

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
    private val password = MutableLiveData<String>()
    val getpassword : LiveData<String>
        get() = password
    fun setPassword(data:String){
        password.value = data
    }
    private val email = MutableLiveData<String>()
    val getemail: LiveData<String>
        get() = email
    fun setemail(data:String){
        email.value = data
    }
}