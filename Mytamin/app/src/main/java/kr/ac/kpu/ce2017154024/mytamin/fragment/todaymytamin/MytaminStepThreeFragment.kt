package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kotlinx.android.synthetic.main.fragment_mytamin_step_threeragment.*
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepThreeragmentBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminStepThreeFragment : Fragment() ,View.OnClickListener{
    private var mBinding : FragmentMytaminStepThreeragmentBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepThreeragmentBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MytaminStepOneFragment onCreateView")
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickjoin()
        todayMytaminViewModel.month.observe(viewLifecycleOwner , Observer {
            mBinding?.mytaminFrametwoCalendarText?.text = "${it}월 ${todayMytaminViewModel.day.value}일"
        })

    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepOneFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){

            mBinding?.mytaminFrametwoImageOne->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_one_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_one_text
                todayMytaminViewModel.setselectemojiState(1)
                (activity as todayMytaminActivity).setEnableNextBtn(true)
            }
            mBinding?.mytaminFrametwoImageTwo->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_two_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_two_text
                todayMytaminViewModel.setselectemojiState(2)
                (activity as todayMytaminActivity).setEnableNextBtn(true)
            }
            mytamin_frametwo_image_three->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_three_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_three_text
                todayMytaminViewModel.setselectemojiState(3)
                (activity as todayMytaminActivity).setEnableNextBtn(true)
            }
            mytamin_frametwo_image_four->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_four_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_four_text
                (activity as todayMytaminActivity).setEnableNextBtn(true)
                todayMytaminViewModel.setselectemojiState(4)
            }
            mytamin_frametwo_image_five->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_five_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_five_text
                todayMytaminViewModel.setselectemojiState(5)
                (activity as todayMytaminActivity).setEnableNextBtn(true)
            }
            mytamin_frametwo_calendar_btn->{
                val bottomSheetDialogFragment= MytaminBottomSheetFragment()
                bottomSheetDialogFragment.show(childFragmentManager,bottomSheetDialogFragment.tag)

            }
        }
    }
    private fun setOnClickjoin(){
        //프레임 2
        mytamin_frametwo_calendar_btn.setOnClickListener(this)
        mytamin_frametwo_image_one.setOnClickListener(this)
        mytamin_frametwo_image_two.setOnClickListener(this)
        mytamin_frametwo_image_three.setOnClickListener(this)
        mytamin_frametwo_image_four.setOnClickListener(this)
        mytamin_frametwo_image_five.setOnClickListener(this)
        ///
    }
}