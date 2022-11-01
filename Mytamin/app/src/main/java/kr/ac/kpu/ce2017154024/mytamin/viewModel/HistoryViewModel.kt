package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.randomCare
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class HistoryViewModel:ViewModel() {

    private val randomcare = MutableLiveData<randomCare>()
    val getrandomcare : LiveData<randomCare>
        get() = randomcare

    fun setrandomcare(time:randomCare){
        randomcare.value = time
    }
    init {
        randomCareAPI()
    }
    fun randomCareAPI(){
        HistoryRetrofitManager.instance.getRandomCare(){responseStatus, randomCareD ->
            if (responseStatus==RESPONSE_STATUS.OKAY){
                randomCareD?.let { setrandomcare(randomCareD) }
            }
        }
    }
}