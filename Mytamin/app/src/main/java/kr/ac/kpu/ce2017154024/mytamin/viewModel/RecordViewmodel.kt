package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkManager
import kr.ac.kpu.ce2017154024.mytamin.MyApplication
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToMM
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToHome
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToMonth
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToYear

class RecordViewmodel():ViewModel(){

  //  private val workManager = WorkManager.getInstance(application)
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
    private val categoryText = MutableLiveData<String>()
    val getcategoryText: LiveData<String>
        get() = categoryText
    fun setcategoryText(d: String){
        categoryText.value = d
    }

    private val wishListArray = MutableLiveData<Array<WishList>>()
    val getwishListArray : LiveData<Array<WishList>>
        get() = wishListArray
    fun setWishList(i:Array<WishList>){
        wishListArray.value = i
        Log.d(TAG,"wishListArray List size -> ${wishListArray.value?.size}")
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

    private val UrlList = MutableLiveData<ArrayList<String>>()
    val getUrlList : LiveData<ArrayList<String>>
        get() = UrlList
    fun addUrlList(data:Uri){
        UrlList.value?.add(data.toString())
        Log.d(TAG,"addUrlList List size -> ${UrlList.value}")
    }
    fun removeUrlList(pos:Int){
        UrlList.value?.removeAt(pos)
    }

    init {
        UrlList.value = ArrayList<String>()
        bitmapList.value = ArrayList<Bitmap>()
        setYear(parseTimeToYear())
        setmonth(parseTimeToMonth())
    }
    private val ok = MutableLiveData<Boolean>()
    val getok: LiveData<Boolean>
        get() = ok
    fun setok(d: Boolean){
        ok.value = d
    }
    fun checkAPI(){
        val year = getyear.value
        val month = getmonth.value.parseIntToMM()
        InformationRetrofitManager.instance.checkrecord("${year}.$month"){responseStatus, b ->
            if (b==false){
                Handler(Looper.getMainLooper()).post{
                    Toast.makeText(MyApplication.instance, "이 날짜는 마이타민 작성불가능합니다.", Toast.LENGTH_SHORT).show()
                    setok(false)
                }
            }else{
                Handler(Looper.getMainLooper()).post{
                    Toast.makeText(MyApplication.instance, "이 날짜는 마이타민 작성가능합니다.", Toast.LENGTH_SHORT).show()
                    setok(true)
                }
            }

        }
    }



}

