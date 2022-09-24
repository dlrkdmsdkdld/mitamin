package kr.ac.kpu.ce2017154024.mytamin.fragment.introduce

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_introducefour_step.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.IntroduceActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentIntroduceOneStepBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentIntroducefourStepBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class IntroducefourStepFragment : Fragment() {
    private var mBinding : FragmentIntroducefourStepBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIntroducefourStepBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"IntroducefourStepFragment onCreateView")
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val datePicker = introduce_timePicker
        datePicker.setOnTimeChangedListener { timePicker, i, i2 ->
            (activity as IntroduceActivity).submitTime(i,i2)
            Log.d(TAG,"${i}시 ${i2}분")

        }
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"IntroducefourStepFragment onDestroyView")
    }

}