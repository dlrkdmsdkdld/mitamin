package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.RecordDaynoteActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentDaynoteBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentWishlistBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel

class DaynoteFragment : Fragment(),View.OnClickListener {
    private var mBinding : FragmentDaynoteBinding?=null
    private val myMydayViewmodel: MydayViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDaynoteBinding.inflate(inflater,container,false)
        mBinding =binding
        myMydayViewmodel.getDaynoteContent.observe(viewLifecycleOwner , Observer {
            if (it) {
                mBinding?.daynoteNoLayout?.visibility = View.INVISIBLE
                //mBinding?.wishlistTitleNoLayout?.visibility=View.INVISIBLE
                mBinding?.daynoteNoBtn?.isEnabled=false
            }
        })
        Log.d(Constant.TAG,"DaynoteFragment onCreateView")
        mBinding?.daynoteNoBtn?.setOnClickListener(this)
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"DaynoteFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.daynoteNoBtn ->{
                val intent = Intent(requireContext(),RecordDaynoteActivity::class.java)
                startActivity(intent)
            }
        }
    }
}