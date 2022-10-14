package kr.ac.kpu.ce2017154024.mytamin.fragment.information

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.ProfileEditActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentBottomProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.utils.choice


class BottomProfileEditFragment : BottomSheetDialogFragment(),View.OnClickListener{
    private var mBinding : FragmentBottomProfileEditBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBottomProfileEditBinding.inflate(inflater,container,false)
        mBinding =binding

        Log.d(Constant.TAG,"BottomProfileEditFragment onCreateView")
        mBinding?.informationBottomEditLayout1?.setOnClickListener(this)
        mBinding?.informationBottomEditLayout2?.setOnClickListener(this)
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"BottomProfileEditFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.informationBottomEditLayout1 ->{
                (activity as ProfileEditActivity).choiceOption(choice.basic)
                onDestroyView()
            }
            mBinding?.informationBottomEditLayout2->{
                (activity as ProfileEditActivity).choiceOption(choice.gallery)
                onDestroyView()
            }
        }
    }


}