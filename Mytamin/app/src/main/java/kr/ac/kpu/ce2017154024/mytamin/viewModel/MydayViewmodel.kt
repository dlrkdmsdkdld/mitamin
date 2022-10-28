package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class MydayViewmodel:ViewModel() {

    private val daynoteDataArray = MutableLiveData<ArrayList<daynoteData>>()
    val getdaynoteDataArray : LiveData<ArrayList<daynoteData>>
        get() = daynoteDataArray
    fun setdaynoteDataArray(i:ArrayList<daynoteData>){
        daynoteDataArray.value = i
    }

    private val WishlistContent = MutableLiveData<Boolean>()
    val getWishlistContent : LiveData<Boolean>
        get() = WishlistContent
    fun setWishlistContent(time:Boolean){
        WishlistContent.value = time
    }
    private val DaynoteContent = MutableLiveData<Boolean>()
    val getDaynoteContent : LiveData<Boolean>
        get() = DaynoteContent
    fun setDaynoteContent(time:Boolean){
        DaynoteContent.value = time
    }
    private val wishListArray = MutableLiveData<ArrayList<WishList>>()
    val getwishListArray : LiveData<ArrayList<WishList>>
        get() = wishListArray
    fun setwishListArray(i:ArrayList<WishList>){
        wishListArray.value = i
    }
    init {
        getWishlistAPI()
        getdaynoteAPI()
    }
    fun getWishlistAPI(){
        InformationRetrofitManager.instance.getWishlist(){responseStatus, mydayData ->
            when(responseStatus){
                RESPONSE_STATUS.NO_CONTENT ->{
                    setWishlistContent(false)
                }
                RESPONSE_STATUS.OKAY ->{
                    setWishlistContent(true)
                    mydayData?.let { setwishListArray(it) }
                }
            }

        }
    }
    fun getdaynoteAPI(){
        InformationRetrofitManager.instance.getDaynote{responseStatus, daynoteList ->
            when(responseStatus){
                RESPONSE_STATUS.NO_CONTENT ->setDaynoteContent(false)
                RESPONSE_STATUS.OKAY -> {
                    setDaynoteContent(true)
                    daynoteList?.let { setdaynoteDataArray(it) }
                }
            }

        }
    }


    


}