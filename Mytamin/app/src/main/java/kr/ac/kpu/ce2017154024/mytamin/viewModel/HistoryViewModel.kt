package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.feeling
import kr.ac.kpu.ce2017154024.mytamin.model.randomCare
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class HistoryViewModel:ViewModel() {

    private val randomcare = MutableLiveData<randomCare>()
    val getrandomcare : LiveData<randomCare>
        get() = randomcare
    fun setrandomcare(time:randomCare){
        randomcare.value = time
    }
    private val mostFeel = MutableLiveData<ArrayList<feeling>>()
    val getmostFeel : LiveData<ArrayList<feeling>>
        get() = mostFeel
    fun setmostFeel(time:ArrayList<feeling>){
        mostFeel.value = time
    }
    init {
        randomCareAPI()
        getMostFeelAPI()
    }
    fun randomCareAPI(){
        HistoryRetrofitManager.instance.getRandomCare(){responseStatus, randomCareD ->
            if (responseStatus==RESPONSE_STATUS.OKAY){
                randomCareD?.let { setrandomcare(randomCareD) }
            }
        }
    }
    fun getMostFeelAPI(){
        HistoryRetrofitManager.instance.getMostFeeling{responseStatus, arrayList ->
            if (responseStatus == RESPONSE_STATUS.OKAY){
                Log.d(TAG  , "arrayList -> $arrayList ")
                arrayList?.let { setmostFeel(arrayList) }
            }
        }
    }
}