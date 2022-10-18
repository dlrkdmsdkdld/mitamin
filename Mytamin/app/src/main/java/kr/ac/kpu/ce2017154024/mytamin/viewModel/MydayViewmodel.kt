package kr.ac.kpu.ce2017154024.mytamin.viewModel

import androidx.lifecycle.ViewModel
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager

class MydayViewmodel:ViewModel() {
    init {
        InformationRetrofitManager.instance.getMyday(){responseStatus, mydayData ->  }   
    }
    


}