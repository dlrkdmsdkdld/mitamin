package kr.ac.kpu.ce2017154024.mytamin.fragment.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.view.descendants
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomRecordCalendarBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentBottomProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.parseBottomCalendarYear
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel

class BottomYearMonthFragment: BottomSheetDialogFragment(),View.OnClickListener {
    private var mBinding : BottomRecordCalendarBinding?=null
    val k = parseBottomCalendarYear()
    private var year:Int = k.get(0)
    private var month:Int =1
    private val myRecordViewmodel: RecordViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = BottomRecordCalendarBinding.inflate(inflater,container,false)
        mBinding =binding

        Log.d(Constant.TAG,"BottomYearMonthFragment onCreateView")
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val yearCalendar = mBinding?.bottomRecordYear

        val min =k.get(0)
        val max = k.get(k.lastIndex)
        val parseArrayList = ArrayList<String>()
        k.forEach {
            parseArrayList.add("${it}년")
        }
        Log.d(TAG," min = $min")
        Log.d(TAG," max = $max")

        Log.d(TAG," parseArrayList = $parseArrayList")
        val parseArray = parseArrayList.toArray(arrayOfNulls<String>(parseArrayList.size))
        Log.d(TAG," k = $parseArray")
        yearCalendar.apply {
            this!!.maxValue  = max
            minValue = min
            displayedValues = parseArray
        }

        yearCalendar?.setOnValueChangedListener(object : com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                year=newVal
                Log.d(TAG, "yearCalendar setOnValueChangedListener  - >  newval : $newVal 년")
               // Log.d(TAG, "yearCalendar setOnValueChangedListener  - > oldval : $oldVal 년  newval : $newVal 년")
            }

        })

        val monthdata = arrayOf("1월","2월","3월" , "4월","5월","6월","7월","8월","9월","10월","11월","12월")
        mBinding?.bottomRecordMonth.apply {
            this!!.maxValue  = 12
            minValue = 1
            displayedValues = monthdata

        }
        mBinding?.bottomRecordMonth?.setOnValueChangedListener(object : com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                month=newVal
                Log.d(TAG, "yearCalendar setOnValueChangedListener  - >  newval : $newVal 년")
                // Log.d(TAG, "yearCalendar setOnValueChangedListener  - > oldval : $oldVal 년  newval : $newVal 년")
            }

        })
        mBinding?.bottomRecordBtn?.setOnClickListener(this)
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"BottomYearMonthFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.bottomRecordBtn ->{
                Log.d(TAG,"year: $year  month :$month")
                myRecordViewmodel.setYear(year)
                myRecordViewmodel.setmonth(month)
                onDestroyView()
            }
        }
    }
}