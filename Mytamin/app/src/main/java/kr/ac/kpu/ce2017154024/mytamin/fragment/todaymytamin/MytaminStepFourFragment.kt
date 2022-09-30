package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jakewharton.rxbinding4.view.detaches
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepFourBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.chipStringdata
import kr.ac.kpu.ce2017154024.mytamin.utils.chipStringdata.previousChildCount
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminStepFourFragment : Fragment(),ChipGroup.OnCheckedStateChangeListener {
    private var mBinding : FragmentMytaminStepFourBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    private lateinit var chipGroup: ChipGroup
    private  lateinit var stateText:Array<String>
    private var chipChildCount:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepFourBinding.inflate(inflater,container,false)
        mBinding =binding
        chipGroup=mBinding?.mytaminStepFourChipgroup!!
        chipGroup.clearDisappearingChildren()
      //  val childCount = chipGroup.childCount
      //  Log.d(Constant.TAG,"MytaminStepFourFragment childCount $childCount")
        chipGroup.setOnCheckedStateChangeListener(this)

        Log.d(Constant.TAG,"MytaminStepFourFragment onCreateView")
        val emojitState = todayMytaminViewModel.selectemojiState.value
        setChip(emojitState!!)
        chipChildCount=chipGroup.childCount
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepFourFragment onDestroyView")
    }
    fun setChip(state:Int){
        when(state){
            1->{
                stateText=chipStringdata.verysad
                chipStringdata.verysad.forEach { statetext ->
                    chipGroup?.addView(Chip(requireContext()).apply {
                        text  =statetext
                        isCheckable=true
                        isClickable=true
                        tag = statetext
                    })
                }
            }
            2->{
                stateText=chipStringdata.sad
                chipStringdata.sad.forEach { statetext ->
                    chipGroup?.addView(Chip(requireContext()).apply {
                        text  =statetext
                        isCheckable=true
                        isClickable=true
                        tag = statetext
                    })
                }
            }
            3-> {
                stateText=chipStringdata.soso
                chipStringdata.soso.forEach { statetext ->
                    chipGroup?.addView(Chip(requireContext()).apply {
                        text = statetext
                        isCheckable = true
                        isClickable = true
                        tag = statetext
                    })
                }
            }
            4-> {
                stateText=chipStringdata.good
                chipStringdata.good.forEach { statetext ->
                    chipGroup?.addView(Chip(requireContext()).apply {
                        text = statetext
                        isCheckable = true
                        isClickable = true
                        tag = statetext
                    })
                }
            }
            5-> {
                stateText=chipStringdata.verygood
                chipStringdata.verygood.forEach { statetext ->
                    chipGroup?.addView(Chip(requireContext()).apply {
                        text = statetext
                        isCheckable = true
                        isClickable = true
                        tag = statetext
//                        setCloseIconVisible(true)
//                        this.setCloseIconResource(R.drawable.ic_x)
//                        setOnCloseIconClickListener {
//                            Log.d(TAG,"삭제버튼클릭")
//                            val tab =it.tag
//
//                            chipGroup.removeView(chipGroup.getChildAt(1))
//                        }
                    })
                }
            }
        }
    }

    override fun onCheckedChanged(group: ChipGroup, checkedIds: MutableList<Int>) {
        var tmp = mutableListOf<String>()
        if (checkedIds.count()>=1){(activity as todayMytaminActivity).setEnableNextBtn(true)}
        else{(activity as todayMytaminActivity).setEnableNextBtn(false)}
        if (checkedIds.count()==3){
            //액티비티가 파괴되면 그전에 있던 id값들이 누적돼서 이렇게해야함
            for(i in 1+previousChildCount until group.childCount+1+previousChildCount){
                if(i in checkedIds){
                }
                else{
                    group.getChildAt(i-1 - previousChildCount).isClickable=false
                }
            }
        }else{
            for(i in 1+previousChildCount until group.childCount+1){
                group.getChildAt(i-1 - previousChildCount).isClickable=true
            }

        }
        checkedIds.forEach {
            val chip = group.getChildAt(it-1  - previousChildCount )
            val tmpS=  chip.getTag()
            tmp.add(tmpS.toString())
        }
        todayMytaminViewModel.setdiagnosis(tmp)
        Log.d(TAG,"chip  mutableListOf mutableListOf -> ${todayMytaminViewModel.selectediagnosis.value}")

    }

    override fun onDestroy() {
        previousChildCount=chipChildCount+previousChildCount
        //뷰가 삭제되도 어찌된일인지 chipgroup 새로생성되는 child id가 이전꺼로부터 갱신되어서 싱글톤변수이용함 ex. 이전꺼 childcount:15 다음꺼 childcount:30됨
        super.onDestroy()
    }
}