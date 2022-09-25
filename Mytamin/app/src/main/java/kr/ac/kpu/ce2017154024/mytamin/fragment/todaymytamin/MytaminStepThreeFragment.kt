package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kotlinx.android.synthetic.main.fragment_mytamin_step_threeragment.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepOneBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepThreeragmentBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.MytaminBottomSheetFragment
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminStepThreeFragment : Fragment() ,View.OnClickListener{
    private var mBinding : FragmentMytaminStepThreeragmentBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    private var selectemojiState = 0 // 이모지 버튼 클릭했을 때 저장
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

    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepOneFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mytamin_frametwo_image_one->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_one_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_one_text
                todayMytaminViewModel.setselectemojiState(1)
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_image_two->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_two_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_two_text
                todayMytaminViewModel.setselectemojiState(2)
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_image_three->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_three_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_three_text
                todayMytaminViewModel.setselectemojiState(3)
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_image_four->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_four_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_four_text
                todayMytamin_nextBtn.isEnabled=true
                todayMytaminViewModel.setselectemojiState(4)
            }
            mytamin_frametwo_image_five->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_five_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_five_text
                todayMytaminViewModel.setselectemojiState(5)
                todayMytamin_nextBtn.isEnabled=true
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