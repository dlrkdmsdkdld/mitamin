package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMyatminCategoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant


class MyatminCategoryFragment : BottomSheetDialogFragment() {
    private var mBinding : FragmentMyatminCategoryBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyatminCategoryBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MyatminCategoryFragment onCreateView")
        return mBinding?.root
    }


    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MyatminCategoryFragment onDestroyView")
    }
}