package kr.ac.kpu.ce2017154024.mytamin.fragment.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomAlarmCalendarBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class AlarmCalendarFragment : BottomSheetDialogFragment() {
    private lateinit var mbinding : BottomAlarmCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding= BottomAlarmCalendarBinding.inflate(layoutInflater)
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val hourdata = arrayOf("00시","01시","02시" , "03시","04시","05시","06시","07시","08시","09시","10시","11시")
        mbinding?.bottomRecordHour.apply {
            maxValue =11
            minValue = 0
            displayedValues = hourdata
        }
        val minutedata = arrayOf("00분","05분","10분" , "15분","20분","25분","30분","35분","40분","45분","50분","55분")
        mbinding?.bottomRecordTime.apply {
            maxValue =11
            minValue = 0
            displayedValues = minutedata
        }
        val timedata = arrayOf("오후","오전")
        mbinding?.bottomRecordNight.apply {
            minValue=10
            maxValue=11
            displayedValues = timedata
        }
        mbinding?.bottomRecordHour.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d(TAG,"bottomRecordHour  시간 :$newVal  ")
        }
        mbinding?.bottomRecordNight.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal ==10){
                Log.d(TAG,"bottomRecordNight  오후선택  ")
            }else{
                Log.d(TAG,"bottomRecordNight  오전선택  ")

            }
        }
        mbinding?.bottomRecordTime.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d(TAG,"bottomRecordTime  분 :${newVal*5}  ")
        }

    }

}