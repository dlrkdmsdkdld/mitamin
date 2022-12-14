package kr.ac.kpu.ce2017154024.mytamin.fragment.introduce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_introducefour_step.*
import kr.ac.kpu.ce2017154024.mytamin.activity.IntroduceActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentIntroducefourStepBinding
import kr.ac.kpu.ce2017154024.mytamin.model.NewUser
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class IntroducefourStepFragment : Fragment() {
    private var mBinding : FragmentIntroducefourStepBinding?=null
    private var hour =1
    private var minute =5
    private var AMorPM =12

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIntroducefourStepBinding.inflate(inflater,container,false)
        mBinding =binding
        (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)

        Log.d(Constant.TAG,"IntroducefourStepFragment onCreateView")


        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputTime()
        numberPickerListener()

        introduce_start_btn.setOnClickListener {
            val email = (activity as IntroduceActivity).getemailData()
            val password = (activity as IntroduceActivity).getpasswordData()
            val nickname = (activity as IntroduceActivity).getnicknameData()
            val userData = NewUser(email,password,nickname,null,null)
            (activity as IntroduceActivity).customProgressDialog.show()
            (activity as IntroduceActivity).newUserJoinAPICall(userData,false)
          //  (activity as IntroduceActivity).customProgressDialog.dismiss()

           // (activity as IntroduceActivity).goLogin()
        }

    }
    override fun onDestroyView() { // ??????????????? ???????????? ??????????????????
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"IntroducefourStepFragment onDestroyView")
    }
    private fun inputTime(){
        val hourdata = arrayOf("00???","01???","02???" , "03???","04???","05???","06???","07???","08???","09???","10???","11???")
        mBinding!!.introduceRecordHour.apply {
            maxValue =11
            minValue = 0
            displayedValues = hourdata
        }
        val minutedata = arrayOf("00???","05???","10???" , "15???","20???","25???","30???","35???","40???","45???","50???","55???")
        mBinding!!.introduceRecordTime.apply {
            maxValue =11
            minValue = 0
            displayedValues = minutedata
        }
        val timedata = arrayOf("??????","??????")
        mBinding!!.introduceRecordNight.apply {
            minValue=10
            maxValue=11
            displayedValues = timedata
        }



    }
    private fun numberPickerListener(){
        mBinding?.introduceRecordNight?.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal ==10){
                Log.d(TAG,"bottomRecordNight  ????????????  ")
                AMorPM=12
            }else{
                Log.d(TAG,"bottomRecordNight  ????????????  ")
                AMorPM=0

            }
            Log.d(TAG, " ?????? ?????? ?????? : ${hour+AMorPM}??? ${minute}???")
            (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)

        }
        mBinding?.introduceRecordHour?.setOnValueChangedListener { picker, oldVal, newVal ->
            hour = newVal
            Log.d(TAG,"bottomRecordHour  ?????? :$newVal  ")
            Log.d(TAG, " ?????? ?????? ?????? : ${hour+AMorPM}??? ${minute}???")
            (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)
        }
        mBinding?.introduceRecordTime?.setOnValueChangedListener{ picker, oldVal, newVal ->
            Log.d(TAG,"bottomRecordTime  ??? :${newVal*5}  ")
            minute = newVal*5
            Log.d(TAG, " ?????? ?????? ?????? : ${hour+AMorPM}??? ${minute}???")
            (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)
        }



    }

}