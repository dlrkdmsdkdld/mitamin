package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView.CareParentAdapter
import kr.ac.kpu.ce2017154024.mytamin.model.monthCareMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.weeklyMental
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class CareHistoryViewModel:ViewModel() {

    private val category= MutableLiveData<ArrayList<Int>>()
    val categoryItem=ArrayList<Int>()
    val getcategory : LiveData<ArrayList<Int>>
        get() = category
    fun setcategory(time:ArrayList<Int>){
        category.value = time
    }
    fun addcategory(p: IntArray){
        p.forEach {
            categoryItem.add(it)
        }

        setcategory(categoryItem)
        Log.d(TAG,"추가됨 p : $p")


    }
    fun removeCategory(index:Int){
        var count = 0
        categoryItem.forEach {
            if (index == it){
                categoryItem.removeAt(count)
                setcategory(categoryItem)
                return
            }
            count+=1
        }
    }
    private val myMonthCareMytamin= MutableLiveData<ArrayList<monthCareMytamin>>()
    val getmyMonthCareMytamin : LiveData<ArrayList<monthCareMytamin>>
        get() = myMonthCareMytamin
    fun setmyMonthCareMytamin(time: ArrayList<monthCareMytamin>){
        myMonthCareMytamin.value = time
    }

    init {
        setcategory(ArrayList<Int>())
        CategoryHistoryAPI()
    }


    fun CategoryHistoryAPI(){
        getcategory.value?.let {
            HistoryRetrofitManager.instance.CareHistoryFilter(it){ responseStatus, mmonthCareMytamin ->
                Log.d(Constant.TAG,"monthCareMytamin : $mmonthCareMytamin")
                if (responseStatus ==RESPONSE_STATUS.NO_CONTENT ){
                    Log.d(Constant.TAG,"검색결과 없음 ")
                    setmyMonthCareMytamin( ArrayList<monthCareMytamin>())
                }
                mmonthCareMytamin?.let { setmyMonthCareMytamin(mmonthCareMytamin) }
            }
        }

    }
}