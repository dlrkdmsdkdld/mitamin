package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_mytamin_bottom_sheet.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminBottomSheetFragment : BottomSheetDialogFragment() {
    private var returnmonth=0
    private var returnday=0
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"MytaminBottomSheetFragment onCreateView()")
        Log.d(TAG,"바텀시트 프래그먼트 생성됨")
        return inflater.inflate(R.layout.fragment_mytamin_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"MytaminBottomSheetFragment onViewCreated()")
        mytamin_bottom_sheet_calendar.setOnDateChangeListener { calendarView, year, month,day ->
            returnmonth=month
            returnday=day
            Log.d(TAG,"MytaminBottomSheetFragment setOnDateChangeListener() ${month+1} 월 $day")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"MytaminBottomSheetFragment onDestroy()")
        if(returnday!=0){
            todayMytaminViewModel.dayset(returnday)
            todayMytaminViewModel.monthset(returnmonth+1)
            //(activity as todayMytaminActivity).submitDay(returnmonth+1,returnday)}
         }
    }



}