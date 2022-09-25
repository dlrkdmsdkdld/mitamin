package kr.ac.kpu.ce2017154024.mytamin.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentYesMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant


class YesMytaminFragment : Fragment() {

    private var mBinding : FragmentYesMytaminBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentYesMytaminBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"HomeFragment의 자식 프래그먼트 YesMytaminFragment onCreateView")
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"HomeFragment의 자식 프래그먼트 YesMytaminFragment onDestroyView")
    }


}