package kr.ac.kpu.ce2017154024.mytamin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.MydayData
import kr.ac.kpu.ce2017154024.mytamin.model.ProfileData
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class InformationViewModel : ViewModel() {

    private val profile = MutableLiveData<ProfileData>()
    val getprofile : LiveData<ProfileData>
        get() = profile

    fun setprofile(time: ProfileData?){
        if (time !=null){
            profile.value = time!!
        }
    }
    init {
        getProfileDataAPI()
        getMydayAPI()
    }
    fun getProfileDataAPI(){
        InformationRetrofitManager.instance.getProfileData(completion = {responseStatus, profileData ->
            if (responseStatus == RESPONSE_STATUS.OKAY){
                setprofile(profileData)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"InformationViewModel onCleared ")
    }
    private val MydayData = MutableLiveData<MydayData>()
    val getMydayData : LiveData<MydayData>
        get() = MydayData

    fun setMydayData(time: MydayData?){
        if (time !=null){
            MydayData.value = time!!
        }
    }
    fun getMydayAPI(){
        InformationRetrofitManager.instance.getMyday{ responseStatus, mydayData ->
            if (responseStatus == RESPONSE_STATUS.OKAY)setMydayData(mydayData)
        }
    }


}