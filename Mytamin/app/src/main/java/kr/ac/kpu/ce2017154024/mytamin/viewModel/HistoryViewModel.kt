package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.feeling
import kr.ac.kpu.ce2017154024.mytamin.model.monthMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.randomCare
import kr.ac.kpu.ce2017154024.mytamin.model.weeklyMental
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
    private val weekMental = MutableLiveData<ArrayList<weeklyMental>>()
    val getweekMental : LiveData<ArrayList<weeklyMental>>
        get() = weekMental
    fun setweekMental(time:ArrayList<weeklyMental>){
        weekMental.value = time
    }
    private val SelectMonthAndYear = MutableLiveData<String>()
    val getSelectMonthAndYear : LiveData<String>
        get() = SelectMonthAndYear
    fun setSelectMonthAndYear(time:String){
        SelectMonthAndYear.value = time
    }
    private val monthmytamin = MutableLiveData<ArrayList<monthMytamin>>()
    val getmonthmytamin: LiveData<ArrayList<monthMytamin>>
        get() = monthmytamin
    fun setmonthmytamin(time:ArrayList<monthMytamin>){
        monthmytamin.value = time
    }

    init {
        randomCareAPI()
        getMostFeelAPI()
        getWeeklyMentalAPI()
    }
    fun getMonthMytaminAPI(){
        getSelectMonthAndYear.value?.let {
            HistoryRetrofitManager.instance.getMonthMytamin(it){responseStatus, montharrayList ->
                montharrayList?.let { setmonthmytamin(it) }
                montharrayList?.forEach {
                    Log.d(TAG,"it day: ${it.day} code : ${it.mentalConditionCode}")
                }
            }
        }
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
    fun getWeeklyMentalAPI(){
        HistoryRetrofitManager.instance.getWeeklyMental{responseStatus, arrayListWeek ->
            arrayListWeek?.let {  setweekMental(it)}
        }
    }
}