package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class MytaminBottomSheetFragment : BottomSheetDialogFragment() {
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
    }

}