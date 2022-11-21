package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_join_step_one.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepFiveBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel

class MytaminStepFiveFragment : Fragment() {
    private var mBinding : FragmentMytaminStepFiveBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepFiveBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MytaminStepFiveFragment onCreateView")
        if(todayMytaminViewModel._report.value!=null){         // 그전에 쓴것이있으면 적용
            mBinding?.mytaminStepFiveText?.setText("${todayMytaminViewModel._report.value}")
        }

        mBinding?.mytaminStepFiveText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                todayMytaminViewModel.reportset(p0.toString())
                Log.d(Constant.TAG,"MytaminStepFiveFragment todayMytaminViewModel -. ${todayMytaminViewModel._report.value}")
                if (p0.toString()!=""){
                    if (todayMytaminViewModel.getstatus.value!!.reportIsDone){

                        (activity as todayMytaminActivity).setEnableCorrection(true)
                    }else{
                        (activity as todayMytaminActivity).setEnableNextBtn(true)
                    }
                }else{
                    if (todayMytaminViewModel.getstatus.value!!.reportIsDone){
                        (activity as todayMytaminActivity).setEnableCorrection(false)
                    }else{
                        (activity as todayMytaminActivity).setEnableNextBtn(false)
                    }
                }
            }

        })


        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepFiveFragment onDestroyView")
    }
}