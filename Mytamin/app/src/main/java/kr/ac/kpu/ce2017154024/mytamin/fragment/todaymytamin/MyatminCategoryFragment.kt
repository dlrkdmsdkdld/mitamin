package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMyatminCategoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MyatminCategoryFragment : BottomSheetDialogFragment(),View.OnClickListener {
    private var mBinding : FragmentMyatminCategoryBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyatminCategoryBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MyatminCategoryFragment onCreateView")
        connectBtn()
        return mBinding?.root
    }


    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MyatminCategoryFragment onDestroyView")
    }
    fun connectBtn(){
        mBinding?.mytaminBottomTextOne?.setOnClickListener(this)
        mBinding?.mytaminBottomTextTwo?.setOnClickListener(this)
        mBinding?.mytaminBottomTextThree?.setOnClickListener(this)
        mBinding?.mytaminBottomTextFour?.setOnClickListener(this)
        mBinding?.mytaminBottomTextFive?.setOnClickListener(this)
        mBinding?.mytaminBottomTextSix?.setOnClickListener(this)
        mBinding?.mytaminBottomTextSeven?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.mytaminBottomTextOne ->{
                todayMytaminViewModel.setcareCategoryCode(1)
            }
            mBinding?.mytaminBottomTextTwo->{
                todayMytaminViewModel.setcareCategoryCode(2)

            }
            mBinding?.mytaminBottomTextThree->{
                todayMytaminViewModel.setcareCategoryCode(3)

            }
            mBinding?.mytaminBottomTextFour->{
                todayMytaminViewModel.setcareCategoryCode(4)

            }
            mBinding?.mytaminBottomTextFive->{
                todayMytaminViewModel.setcareCategoryCode(5)

            }
            mBinding?.mytaminBottomTextSix->{
                todayMytaminViewModel.setcareCategoryCode(6)

            }
            mBinding?.mytaminBottomTextSeven->{
                todayMytaminViewModel.setcareCategoryCode(7)

            }

        }
    }
}