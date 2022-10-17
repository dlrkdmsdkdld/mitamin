package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.model.ProfileData
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
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
    }
    fun getProfileDataAPI(){
        InformationRetrofitManager.instance.getProfileData(completion = {responseStatus, profileData ->
            if (responseStatus == RESPONSE_STATUS.OKAY){
                setprofile(profileData)
            }
        })
    }



}