package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToHome
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToMonth
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToYear

class RecordViewmodel:ViewModel() {
    private val year = MutableLiveData<Int>()
    val getyear : LiveData<Int>
        get() = year
    fun setYear(d: Int){
        year.value = d
    }
    private val month = MutableLiveData<Int>()
    val getmonth : LiveData<Int>
        get() = month
    fun setmonth(d: Int){
        month.value = d
    }

    private val bitmapList = MutableLiveData<ArrayList<Bitmap>>()
    val getbitmapList : LiveData<ArrayList<Bitmap>>
        get() = bitmapList
    fun addbitmapList(data:Bitmap){
        bitmapList.value?.add(data)
        Log.d(TAG,"addbitmap List size -> ${bitmapList.value?.size}")
    }
    fun removeBitmapList(pos:Int){
        bitmapList.value?.removeAt(pos)
    }
    init {
        bitmapList.value = ArrayList<Bitmap>()
        setYear(parseTimeToYear())
        setmonth(parseTimeToMonth())
    }



}