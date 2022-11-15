package kr.ac.kpu.ce2017154024.mytamin.fragment.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomAlarmCalendarBinding
import kr.ac.kpu.ce2017154024.mytamin.model.mytaminAlarmTime
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToHH
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.settingViewmodel


class AlarmCalendarFragment : BottomSheetDialogFragment() ,View.OnClickListener{
    private lateinit var mbinding : BottomAlarmCalendarBinding
    var hour=9
    var min=0
    var AmorPm=12
    private val myviewmodel:  settingViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding= BottomAlarmCalendarBinding.inflate(layoutInflater)
        mbinding?.bottomRecordBtn.setOnClickListener(this)
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
            hour=newVal
        }
        mbinding?.bottomRecordNight.setOnValueChangedListener { picker, oldVal, newVal ->
            if (newVal ==10){
                Log.d(TAG,"bottomRecordNight  오후선택  ")
                AmorPm=12
            }else{
                Log.d(TAG,"bottomRecordNight  오전선택  ")
                AmorPm=0
            }
        }
        mbinding?.bottomRecordTime.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d(TAG,"bottomRecordTime  분 :${newVal*5}  ")
            min=newVal*5
        }
        mbinding?.bottomRecordHour.value=9
        mbinding?.bottomRecordTime.value=0
        myviewmodel.getmytaminAlarmData.value?.let { it.whentime?.let {
            val amorpm = "${it.get(0)}${it.get(1)}"
            val hour="${it.get(3)}${it.get(4)}".toInt()
            if (amorpm =="오후"){mbinding?.bottomRecordNight.value=10
                AmorPm=12
            }
            else {mbinding?.bottomRecordNight.value=11
                AmorPm=0
            }
            mbinding?.bottomRecordHour.value=hour
            this.hour=hour
            val min=("${it.get(6)}${it.get(7)}".toInt()) / 5
            this.min=min*5
            mbinding?.bottomRecordTime.value=min
        } }
    }

    override fun onClick(p0: View?) {
        if (p0==mbinding?.bottomRecordBtn){
            val result = mytaminAlarmTime("${(hour+AmorPm).parseIntToHH()}","${min.parseIntToHH()}")
            Log.d(TAG,"mytaminAlarmTime - >${result}")
            myviewmodel.setmytaminTime(result)

            onDestroyView()
        }
    }

}