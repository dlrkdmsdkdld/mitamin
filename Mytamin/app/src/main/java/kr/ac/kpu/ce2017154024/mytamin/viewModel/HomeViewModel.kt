package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.LoginData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.home.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class HomeViewModel: ViewModel() {

    private val nickname = MutableLiveData<String>()
    val getnickname : LiveData<String>
        get() = nickname

    fun setnickname(time:String){
        nickname.value = time
    }

    private val comment = MutableLiveData<String>()
    val getcomment : LiveData<String>
        get() = comment

    fun setcomment(icomment:String){
        comment.value = icomment
    }
    init {
        wellcomAPICall()
    }

    fun wellcomAPICall(){
        HomeRetrofitManager.instance.getWelcomeMessage(completion = {responseStatus, wellcomedata ->
            Log.d(TAG,"HomeViewModel wellcomAPICall ")
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    setnickname(wellcomedata!!.email)
                    setcomment(wellcomedata!!.password)
                }
            }
        })
    }

}