package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HistoryViewModel


class HistoryFragment : Fragment(),View.OnClickListener {
    private var mBinding : FragmentHistoryBinding?=null
    private val myviewmodel : HistoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryBinding.inflate(inflater,container,false)
        mBinding =binding

        Log.d(TAG,"HistoryFragment onCreateView")

        myviewmodel.getrandomcare.observe(viewLifecycleOwner, Observer {
            mBinding?.historyRandomCare1?.text = it.careMsg1
            mBinding?.historyRandomCare2?.text = it.careMsg2
            mBinding?.historyRadomTake?.text = it.takeAt
        })

        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(TAG,"HistoryFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.historyRefreshBtn ->{
                myviewmodel.randomCareAPI()
            }
        }
    }

}