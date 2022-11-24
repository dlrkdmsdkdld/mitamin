package kr.ac.kpu.ce2017154024.mytamin.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentYesMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.emojiArray
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HomeViewModel


class YesMytaminFragment : Fragment() ,View.OnClickListener{

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
        status?.let {
            var status=it
            latestMytamin?.let {
                initData(status,it)
            }
        }

        if (latestMytamin?.canEditReport == true){
            mBinding?.yesMytaminStep3Btn?.setOnClickListener(this)
        }else{
            mBinding?.yesMytaminStep3Btn?.visibility=View.GONE


        }
        if (latestMytamin?.canEditCare== true){
            mBinding?.yesMytaminStep4Btn?.setOnClickListener(this)
        }else{
            mBinding?.yesMytaminStep4Btn?.visibility=View.GONE
        }


        return mBinding?.root
    }
    private fun initData(status:Status,latestMytamin:LatestMytamin){
        latestMytamin?.reportId?.let {
            mBinding?.yesMytaminImage?.setImageResource(emojiArray[latestMytamin!!.mentalConditionCode])
            mBinding?.yesMytaminFeelingTag?.text = latestMytamin?.feelingTag?: ""
            mBinding?.yesMytaminMentalConditionMsg?.text = latestMytamin?.mentalConditionMsg?: ""
            mBinding?.yesmytaminTodayReport?.text = latestMytamin?.todayReport?: ""

            mBinding?.yesmytaminCareMsg1?.text = latestMytamin?.careMsg1?: ""
            mBinding?.yesmytaminCareMsg2?.text = latestMytamin?.careMsg2?: ""
        }
        latestMytamin?.careId?.let {
            mBinding?.yesmytaminCareMsg1?.text = latestMytamin?.careMsg1?: ""
            mBinding?.yesmytaminCareMsg2?.text = latestMytamin?.careMsg2?: ""
        }
//        if (status!!.reportIsDone==true && status!!.careIsDone==true ){
//
//        }else if(status!!.reportIsDone==true && status!!.careIsDone==false){
//            mBinding?.yesMytaminImage?.setImageResource(emojiArray[latestMytamin!!.mentalConditionCode])
//            mBinding?.yesMytaminFeelingTag?.text = latestMytamin?.feelingTag?: ""
//            mBinding?.yesMytaminMentalConditionMsg?.text = latestMytamin?.mentalConditionMsg?: ""
//            mBinding?.yesmytaminTodayReport?.text = latestMytamin?.todayReport?: ""
//        }
//        else if(status!!.reportIsDone==false && status!!.careIsDone==true){
//
//        }
    }

    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"HomeFragment의 자식 프래그먼트 YesMytaminFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.yesMytaminStep3Btn ->{
                val intent= Intent(context, todayMytaminActivity::class.java)
                intent.putExtra("step",3)
                intent.putExtra("modify",true)


                val bundle = Bundle()
                bundle.putSerializable("statusData",homeviewmodel.getstatus.value)
                intent.putExtra("bundle",bundle)
                val bundleL = Bundle()
                bundleL.putSerializable("LatestMytamin",homeviewmodel.getLatestMytamin.value)
                intent.putExtra("bundleL",bundleL)
                startActivity(intent)

            }
            mBinding?.yesMytaminStep4Btn->{
                val intent= Intent(context, todayMytaminActivity::class.java)
                intent.putExtra("step",6)
                intent.putExtra("modify",true)

                val bundle = Bundle()
                bundle.putSerializable("statusData",homeviewmodel.getstatus.value)
                intent.putExtra("bundle",bundle)
                val bundleL = Bundle()
                bundleL.putSerializable("LatestMytamin",homeviewmodel.getLatestMytamin.value)
                intent.putExtra("bundleL",bundleL)
                startActivity(intent)
            }
        }
    }
//    Log.d(TAG,"클릭한 리싸이클러뷰 $position 번째")
//    val intent= Intent(context, todayMytaminActivity::class.java)
//    if(position==3){
//        intent.putExtra("step",position+3)
//    }
//    else{
//        intent.putExtra("step",position+1)
//    }
//    val bundle = Bundle()
//    bundle.putSerializable("statusData",statusData)
//
//    if (myHomeViewModel.getLatestMytamin.value !=null){
//        val bundleL = Bundle()
//        bundleL.putSerializable("LatestMytamin",myHomeViewModel.getLatestMytamin.value)
//        intent.putExtra("bundleL",bundleL)
//    }else{
//
//    }
//
//    intent.putExtra("bundle",bundle)
//    //  val k = resultBoolean
//    //   intent.putExtra("resultBoolean",resultBoolean)
//
//    startActivity(intent)

}