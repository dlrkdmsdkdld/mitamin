package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.UI.WishlistSnackbar
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteDataParent
import kr.ac.kpu.ce2017154024.mytamin.model.modifyWish
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class MydayViewmodel:ViewModel() {

    private val daynoteDataArray = MutableLiveData<ArrayList<daynoteDataParent>>()
    val getdaynoteDataArray : LiveData<ArrayList<daynoteDataParent>>
        get() = daynoteDataArray
    fun setdaynoteDataArray(i:ArrayList<daynoteDataParent>){
        daynoteDataArray.value = i
    }
    private val WishlistDelete = MutableLiveData<Int>()
    val getWishlistDelete : LiveData<Int>
        get() = WishlistDelete
    fun setWishlistDelete(time:Int){

            WishlistDelete.value = time

    }
    fun deleteWishlistAPI(id:Int){
        InformationRetrofitManager.instance.deleteWishlist(id)
    }

    private val WishlistModify = MutableLiveData<modifyWish>()
    val getWishlistModify : LiveData<modifyWish>
        get() = WishlistModify
    fun setWishlistModify(time:modifyWish){
        WishlistModify.value = time
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