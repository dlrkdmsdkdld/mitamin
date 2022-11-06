package kr.ac.kpu.ce2017154024.mytamin.fragment.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomHistoryCareCategoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.CareHistoryViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel


class BottomCategoryCareFragment : BottomSheetDialogFragment(),View.OnClickListener {
    private lateinit var mbinding:BottomHistoryCareCategoryBinding
    private val myViewmodel: CareHistoryViewModel by activityViewModels()
    private var select:Int?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = BottomHistoryCareCategoryBinding.inflate(inflater,container,false)
        listener()




        return mbinding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.bottomCare1 ->{
                select =1
            }
            mbinding?.bottomCare2 ->{
                select=2
            }
            mbinding?.bottomCare3 ->{
                select=3
            }
            mbinding?.bottomCare4 ->{
                select=4
            }
            mbinding?.bottomCare5 ->{
                select=5
            }
            mbinding?.bottomCare6 ->{
                select=6
            }
            mbinding?.bottomCare7 ->{
                select=7
            }
            mbinding?.bottomCareComplete ->{
                select?.let {  myViewmodel.addcategory(select!!) }
                Log.d(TAG , "t선택 카테고리 select: $select")
                onDestroyView()
            }
        }
    }
    private fun listener(){
        mbinding?.bottomCare1.setOnClickListener(this)
        mbinding?.bottomCare2.setOnClickListener(this)
        mbinding?.bottomCare3.setOnClickListener(this)
        mbinding?.bottomCare4.setOnClickListener(this)
        mbinding?.bottomCare5.setOnClickListener(this)
        mbinding?.bottomCare6.setOnClickListener(this)
        mbinding?.bottomCare7.setOnClickListener(this)
        mbinding?.bottomCareComplete.setOnClickListener(this)
    }
}