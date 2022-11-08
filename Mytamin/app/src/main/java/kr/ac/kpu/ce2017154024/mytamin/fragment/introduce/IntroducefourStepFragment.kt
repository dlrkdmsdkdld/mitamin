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
            val userData = NewUser(email,password,nickname)
            (activity as IntroduceActivity).customProgressDialog.show()
            (activity as IntroduceActivity).newUserJoinAPICall(userData,false)
          //  (activity as IntroduceActivity).customProgressDialog.dismiss()

           // (activity as IntroduceActivity).goLogin()
        }

    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"IntroducefourStepFragment onDestroyView")
    }
    private fun inputTime(){
        val hourdata = arrayOf("00시","01시","02시" , "03시","04시","05시","06시","07시","08시","09시","10시","11시")
        mBinding!!.introduceRecordHour.apply {
            maxValue =11
            minValue = 0
            displayedValues = hourdata
        }
        val minutedata = arrayOf("00분","05분","10분" , "15분","20분","25분","30분","35분","40분","45분","50분","55분")
        mBinding!!.introduceRecordTime.apply {
            maxValue =11
            minValue = 0
            displayedValues = minutedata
        }
        val timedata = arrayOf("오후","오전")
        mBinding!!.introduceRecordNight.apply {
            minValue=10
            maxValue=11
            displayedValues = timedata
        }



    }
    private fun numberPickerListener(){
        mBinding?.introduceRecordNight?.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal ==10){
                Log.d(TAG,"bottomRecordNight  오후선택  ")
                AMorPM=12
            }else{
                Log.d(TAG,"bottomRecordNight  오전선택  ")
                AMorPM=0

            }
            Log.d(TAG, " 현재 선택 시간 : ${hour+AMorPM}시 ${minute}분")
            (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)

        }
        mBinding?.introduceRecordHour?.setOnValueChangedListener { picker, oldVal, newVal ->
            hour = newVal
            Log.d(TAG,"bottomRecordHour  시간 :$newVal  ")
            Log.d(TAG, " 현재 선택 시간 : ${hour+AMorPM}시 ${minute}분")
            (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)
        }
        mBinding?.introduceRecordTime?.setOnValueChangedListener{ picker, oldVal, newVal ->
            Log.d(TAG,"bottomRecordTime  분 :${newVal*5}  ")
            minute = newVal*5
            Log.d(TAG, " 현재 선택 시간 : ${hour+AMorPM}시 ${minute}분")
            (activity as IntroduceActivity).submitTime(hour+AMorPM,minute)
        }



    }

}