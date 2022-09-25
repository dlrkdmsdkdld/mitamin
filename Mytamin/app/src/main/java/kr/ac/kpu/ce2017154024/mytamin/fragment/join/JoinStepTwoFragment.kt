package kr.ac.kpu.ce2017154024.mytamin.fragment.join

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_join_step_one.*
import kotlinx.android.synthetic.main.fragment_join_step_two.*
import kr.ac.kpu.ce2017154024.mytamin.activity.joinActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentJoinStepTwoBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class joinStepTwoFragment : Fragment() {
    private var mBinding: FragmentJoinStepTwoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJoinStepTwoBinding.inflate(inflater, container, false)
        mBinding = binding
        Log.d(Constant.TAG, "joinStepTwoFragment onCreateView")
        mBinding?.joinStepTwoAllCheck?.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                Log.d(TAG,"joinStepTwoFragment joinStepTwoAllRadio $b")
                mBinding?.joinStepTwoAllCheck?.isChecked=true
                mBinding?.joinStepTwoFirstCheck?.isChecked = true
                mBinding?.joinStepTwoSecondCheck?.isChecked = true
                (activity as joinActivity).canEnableNextbtn(true)
            }else{
                Log.d(TAG,"joinStepTwoFragment joinStepTwoAllRadio $b")
                mBinding?.joinStepTwoAllCheck?.isChecked=false
                mBinding?.joinStepTwoFirstCheck?.isChecked = false
                mBinding?.joinStepTwoSecondCheck?.isChecked = false
                (activity as joinActivity).canEnableNextbtn(false)
            }
        }

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        join_step_two_first_check.setOnCheckedChangeListener { compoundButton, b ->

            if (join_step_two_second_check.isChecked){
                (activity as joinActivity).canEnableNextbtn(true)
            }else{
                (activity as joinActivity).canEnableNextbtn(false)
            }

        }
        join_step_two_second_check.setOnCheckedChangeListener { compoundButton, b ->

            if (join_step_two_first_check.isChecked){
                (activity as joinActivity).canEnableNextbtn(true)
            }else{
                (activity as joinActivity).canEnableNextbtn(false)
            }

        }


    }

    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding = null
        super.onDestroyView()
        Log.d(Constant.TAG, "joinStepTwoFragment onDestroyView")
    }


}