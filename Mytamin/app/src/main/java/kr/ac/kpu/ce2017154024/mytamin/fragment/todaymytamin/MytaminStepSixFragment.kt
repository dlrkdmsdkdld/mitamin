package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepSixBinding
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.chipStringdata.category
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminStepSixFragment : Fragment(),View.OnClickListener {
    private var mBinding : FragmentMytaminStepSixBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    private var status:Status?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepSixBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MytaminStepSixFragment onCreateView")
        mBinding?.mytaminStepSixLayout?.setOnClickListener(this)
        todayMytaminViewModel.getcareCategoryCode.observe(viewLifecycleOwner , Observer {
            Log.d(TAG,"현재 선택 카테고리 $it")
            val selectcategory = category[it-1]
            mBinding?.mytaminStepSixCategory?.text = selectcategory
        })

        if (todayMytaminViewModel.getstatus.value?.careIsDone ==true){
            mBinding?.mytaminStepSixCareText?.setText(todayMytaminViewModel.getcareMsg1.value)
            mBinding?.mytaminStepSixCareSub?.setText(todayMytaminViewModel.getcareMsg2.value)
            mBinding?.mytaminStepSixCategory?.text = todayMytaminViewModel.getcareCategoryCodeMsg.value
            var k =0
            category.forEach {
                if (it == todayMytaminViewModel.getcareCategoryCodeMsg.value){
                    todayMytaminViewModel.setcareCategoryCode(k+1)
                    Log.d(Constant.TAG,"카테고리코드 ${todayMytaminViewModel.getcareCategoryCode.value}")
                }
                k+=1
            }
        }

        status= todayMytaminViewModel.getstatus.value?:null

        mBinding?.mytaminStepSixCareText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                todayMytaminViewModel.setcareMsg1(p0.toString())

                if (todayMytaminViewModel.getcareCategoryCode.value!=null &&p0.toString()!="" && (todayMytaminViewModel.getcareMsg2.value !="" &&todayMytaminViewModel.getcareMsg2.value!=null  )){
                    if (status?.careIsDone==true){
                        (activity as todayMytaminActivity).setEnableCorrection(true)
                    }else{
                        (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
                    }
                }else{
                    if (status?.careIsDone==false){
                        (activity as todayMytaminActivity).setEnableCorrection(false)
                    }else{
                        (activity as todayMytaminActivity).setEnableNextBtnPartTwo(false)
                    }
                }
            }

        })
        mBinding?.mytaminStepSixCareSub?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                todayMytaminViewModel.setcareMsg2(p0.toString())
                if (todayMytaminViewModel.getcareCategoryCode.value!=null &&p0.toString()!="" && (todayMytaminViewModel.getcareMsg1.value !="" &&todayMytaminViewModel.getcareMsg1.value!=null)){
                    if (status?.careIsDone==true){
                        (activity as todayMytaminActivity).setEnableCorrection(true)
                    }else{
                        (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
                    }
                }else{
                    if (status?.careIsDone==false){
                        (activity as todayMytaminActivity).setEnableCorrection(false)
                    }else{
                        (activity as todayMytaminActivity).setEnableNextBtnPartTwo(false)
                    }
                }
            }

        })




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