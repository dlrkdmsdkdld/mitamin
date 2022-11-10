package kr.ac.kpu.ce2017154024.mytamin.fragment.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.activity.CareHistoryActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomHistoryCareCategoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.CareHistoryViewModel


class BottomCategoryCareFragment : BottomSheetDialogFragment(),View.OnClickListener {
    private lateinit var mbinding:BottomHistoryCareCategoryBinding
    private val myViewmodel: CareHistoryViewModel by activityViewModels()
    private var select:Int?=null
    private var selectSet = mutableSetOf<Int>()
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
    //이미 있으면 없애고 없으면 추가
    private fun plusandminus(i :Int):Boolean{
        var ISdata = false
        selectSet.forEach {
            if (it==i) ISdata=true
        }
        if (ISdata){
            selectSet.remove(i)
            return false
        }else{
            selectSet.add(i)
            return true
        }
    }
    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.bottomCare1 ->{
                select =1
                val tmp = plusandminus(1)
                if (tmp){
                    mbinding?.careHistoryCheck1.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck1.visibility =View.GONE
                }

            }
            mbinding?.bottomCare2 ->{

                select=2
                val tmp = plusandminus(2)
                if (tmp){
                    mbinding?.careHistoryCheck2.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck2.visibility =View.GONE
                }
            }
            mbinding?.bottomCare3 ->{
                select=3
                val tmp = plusandminus(3)
                if (tmp){
                    mbinding?.careHistoryCheck3.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck3.visibility =View.GONE
                }
            }
            mbinding?.bottomCare4 ->{
                select=4
                val tmp = plusandminus(4)
                if (tmp){
                    mbinding?.careHistoryCheck4.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck4.visibility =View.GONE
                }
            }
            mbinding?.bottomCare5 ->{
                select=5
                val tmp = plusandminus(5)
                if (tmp){
                    mbinding?.careHistoryCheck5.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck5.visibility =View.GONE
                }
            }
            mbinding?.bottomCare6 ->{
                select=6
                val tmp = plusandminus(6)
                if (tmp){
                    mbinding?.careHistoryCheck6.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck6.visibility =View.GONE
                }
            }
            mbinding?.bottomCare7 ->{
                select=7
                val tmp = plusandminus(7)
                if (tmp){
                    mbinding?.careHistoryCheck7.visibility=View.VISIBLE
                }else{
                    mbinding?.careHistoryCheck7.visibility =View.GONE
                }
            }
            mbinding?.bottomCareComplete ->{
                if (selectSet.isNotEmpty()){
                    val tmpt = selectSet.toIntArray()
                    myViewmodel.addcategory(tmpt)
                    (activity as CareHistoryActivity).addCategory(tmpt)
                }

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