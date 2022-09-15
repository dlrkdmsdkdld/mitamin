package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class HistoryFragment : Fragment() {
    private var mBinding : FragmentHistoryBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(TAG,"HistoryFragment onCreateView")
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(TAG,"HistoryFragment onDestroyView")
    }

}