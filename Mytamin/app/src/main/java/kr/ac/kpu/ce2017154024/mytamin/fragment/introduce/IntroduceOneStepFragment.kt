package kr.ac.kpu.ce2017154024.mytamin.fragment.introduce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentIntroduceOneStepBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class IntroduceOneStepFragment : Fragment() {

    private var mBinding : FragmentIntroduceOneStepBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIntroduceOneStepBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(TAG,"IntroduceOneStepFragment onCreateView")
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(TAG,"IntroduceOneStepFragment onDestroyView")
    }

}