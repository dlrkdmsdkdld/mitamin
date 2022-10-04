package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.Repository.HomeRepository
import kr.ac.kpu.ce2017154024.mytamin.Repository.MytaminRepository
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.LoginData
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.retrofit.home.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class HomeViewModel: ViewModel() {
    private val HomeRepository= HomeRepository()  //레포지토리 ##
    private val latestMytamin = MutableLiveData<LatestMytamin>()
    val getlatestMytamin : LiveData<LatestMytamin>
        get() = latestMytamin
    fun setlatestMytamin(data:LatestMytamin?){
        Log.d(TAG,"뷰모델에서 코루틴 값  -> ${data}")
        if (data!=null){
            latestMytamin.value = data!!
        }
    }




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
        StatusAPI()
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
    fun StatusAPI(){
        HomeRetrofitManager.instance.getStatus(completion = {responseStatus,Status ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    setstatus(Status!!)
                }
            }
        })
    }
    fun LatestMytaminAPI(){
        HomeRetrofitManager.instance.getlatestMytamin(completion = {responseStatus,LatestMytamin ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    Log.d(TAG,"LatestMytaminAPI call -->${LatestMytamin}")
                    setLatestMytamin(LatestMytamin!!)
                }
            }
        })
    }
    private val status = MutableLiveData<Status>()
    val getstatus : LiveData<Status>
        get() = status

    fun setstatus(time:Status){
        status.value = time
    }

    private val LatestMytamin = MutableLiveData<LatestMytamin>()
    val getLatestMytamin : LiveData<LatestMytamin>
        get() = LatestMytamin

    fun setLatestMytamin(time:LatestMytamin){
        LatestMytamin.value = time
    }

}