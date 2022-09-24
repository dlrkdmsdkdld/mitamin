package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Job
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
        password.value = time
    }

    private val email = MutableLiveData<String>()
    val getemail : LiveData<String>
        get() = email
    fun setemail(time:String){
        email.value = time
        Log.d(TAG,"joinViewModel emailValue -> ${email.value}")
    }

    private val name = MutableLiveData<String>()
    val getname : LiveData<String>
        get() = name
    fun setname(time:String){
        name.value = time
    }
    //프로그래스 관련함수들
    var _progressPer = MutableLiveData<Int>()
    val progressPer : LiveData<Int>
        get() = _progressPer
    fun getInterval(): Observable<Long> =
        Observable.interval(1L, TimeUnit.MILLISECONDS).map { interval ->
            interval + 1
        }.take(200)
}