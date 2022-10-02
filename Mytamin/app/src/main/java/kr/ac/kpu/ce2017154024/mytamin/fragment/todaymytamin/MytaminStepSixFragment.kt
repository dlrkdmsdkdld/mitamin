package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepSixBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant


class MytaminStepSixFragment : Fragment(),View.OnClickListener {
    private var mBinding : FragmentMytaminStepSixBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepSixBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MytaminStepSixFragment onCreateView")
        mBinding?.mytaminStepSixLayout?.setOnClickListener(this)
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepSixFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.mytaminStepSixLayout ->{
                val bottomSheetDialogFragment= MyatminCategoryFragment()
                bottomSheetDialogFragment.show(childFragmentManager,bottomSheetDialogFragment.tag)

            }
        }
    }
}