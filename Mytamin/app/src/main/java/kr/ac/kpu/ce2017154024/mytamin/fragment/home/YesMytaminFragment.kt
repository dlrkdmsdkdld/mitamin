package kr.ac.kpu.ce2017154024.mytamin.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentYesMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.emojiArray
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HomeViewModel


class YesMytaminFragment : Fragment() {

    private var mBinding : FragmentYesMytaminBinding?=null
    private val homeviewmodel : HomeViewModel by viewModels({requireParentFragment()})
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentYesMytaminBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"HomeFragment의 자식 프래그먼트 YesMytaminFragment onCreateView")
        val status = homeviewmodel.getstatus.value
        val latestMytamin = homeviewmodel.getLatestMytamin.value
        Log.d(Constant.TAG,"HomeFragment의 자식 프래그먼트 YesMytaminFragment status - > $status")
        if (status!!.reportIsDone==true && status!!.careIsDone==true ){
            mBinding?.yesMytaminImage?.setImageResource(emojiArray[latestMytamin!!.mentalConditionCode])
            mBinding?.yesMytaminFeelingTag?.text = latestMytamin!!.feelingTag
            mBinding?.yesMytaminMentalConditionMsg?.text = latestMytamin!!.mentalConditionMsg
            mBinding?.yesmytaminTodayReport?.text = latestMytamin!!.todayReport

            mBinding?.yesmytaminCareMsg1?.text = latestMytamin!!.careMsg1
            mBinding?.yesmytaminCareMsg2?.text = latestMytamin!!.careMsg2
        }else if(status!!.reportIsDone==true && status!!.careIsDone==false){
            mBinding?.yesMytaminImage?.setImageResource(emojiArray[latestMytamin!!.mentalConditionCode])
            mBinding?.yesMytaminFeelingTag?.text = latestMytamin!!.feelingTag
            mBinding?.yesMytaminMentalConditionMsg?.text = latestMytamin!!.mentalConditionMsg
            mBinding?.yesmytaminTodayReport?.text = latestMytamin!!.todayReport
        }
        else if(status!!.reportIsDone==false && status!!.careIsDone==true){
            mBinding?.yesmytaminCareMsg1?.text = latestMytamin!!.careMsg1
            mBinding?.yesmytaminCareMsg2?.text = latestMytamin!!.careMsg2
        }


        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"HomeFragment의 자식 프래그먼트 YesMytaminFragment onDestroyView")
    }


}