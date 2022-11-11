package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding4.view.clicks
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kotlinx.android.synthetic.main.fragment_introduce_three_step.*
import kr.ac.kpu.ce2017154024.mytamin.UI.MytaminCorrectionDialog
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepThreeBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminStepThreeFragment : Fragment() ,View.OnClickListener{
    private var mBinding : FragmentMytaminStepThreeBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    private var nowselect:Int =100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepThreeBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MytaminStepOneFragment onCreateView")

        (activity as todayMytaminActivity).setEnableCorrection(false)
        val dialog = MytaminCorrectionDialog(requireContext(),"하루 진단하기")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (todayMytaminViewModel.getstatus.value?.reportIsDone != false){
            dialog.show()
            dialog.setOnClickListener(object :MytaminCorrectionDialog.OnClickedDialogBtn{
                override fun OnNegativeBtn() {
                   Log.d(TAG,"OnNegativeBtn")
                    dialog.dismiss()
                    (activity as todayMytaminActivity).onBackPressed()

                }

                override fun OnPositiveBtn() {
                    Log.d(TAG,"OnPositiveBtn")
                    dialog.dismiss()

                }

            })
        }

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickjoin()
        Log.d(Constant.TAG,"MytaminStepOneFragment todayMytaminViewModel.selectemojiState.value ${todayMytaminViewModel.selectemojiState.value}")
        if (todayMytaminViewModel.selectemojiState.value !=null){
            when(todayMytaminViewModel.selectemojiState.value){
                1->{
                    mBinding?.mytaminFrametwoImageOne?.performClick()
                }
                2->{
                    mBinding?.mytaminFrametwoImageTwo?.performClick()

                }
                3->{
                    mBinding?.mytaminFrametwoImageThree?.performClick()

                }
                4->{
                    mBinding?.mytaminFrametwoImageFour?.performClick()

                }
                5->{
                    mBinding?.mytaminFrametwoImageFive?.performClick()

                }
            }
        }

    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepOneFragment onDestroyView")
    }
    fun nochoiceImage(choice:Int){
        when(choice){
            1->{
                mBinding?.mytaminFrametwoImageOne?.setBackgroundResource(Constant.frametwo_image_one_src_no)
            }
            2->{
                mBinding?.mytaminFrametwoImageTwo?.setBackgroundResource(Constant.frametwo_image_two_src_no)
            }
            3->{
                mBinding?.mytaminFrametwoImageThree?.setBackgroundResource(Constant.frametwo_image_three_src_no)
            }
            4->{
                mBinding?.mytaminFrametwoImageFour?.setBackgroundResource(Constant.frametwo_image_four_src_no)

            }
            5->{
                mBinding?.mytaminFrametwoImageFive?.setBackgroundResource(Constant.frametwo_image_five_src_no)
            }
            else->{

            }
        }
    }
    override fun onClick(p0: View?) {
        when(p0){

            mBinding?.mytaminFrametwoImageOne->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_one_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_one_text
                nochoiceImage(nowselect)
                mBinding?.mytaminFrametwoImageOne?.setBackgroundResource(Constant.frametwo_image_one_src)
                nowselect=1
                todayMytaminViewModel.setselectemojiState(1)
                (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
            }
            mBinding?.mytaminFrametwoImageTwo->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_two_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_two_text
                nochoiceImage(nowselect)
                mBinding?.mytaminFrametwoImageTwo?.setBackgroundResource(Constant.frametwo_image_two_src)
                nowselect=2
                todayMytaminViewModel.setselectemojiState(2)
                (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
            }
            mBinding?.mytaminFrametwoImageThree->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_three_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_three_text
                nochoiceImage(nowselect)
                mBinding?.mytaminFrametwoImageThree?.setBackgroundResource(Constant.frametwo_image_three_src)
                nowselect=3
                todayMytaminViewModel.setselectemojiState(3)
                (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
            }
            mBinding?.mytaminFrametwoImageFour->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_four_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_four_text
                nochoiceImage(nowselect)
                nowselect=4
                (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
                mBinding?.mytaminFrametwoImageFour?.setBackgroundResource(Constant.frametwo_image_four_src)
                todayMytaminViewModel.setselectemojiState(4)
            }
            mBinding?.mytaminFrametwoImageFive->{
                mBinding?.mytaminFrametwoSelectImageView?.setImageResource(Constant.frametwo_image_five_src)
                mBinding?.mytaminFrametwoState?.text  = Constant.frametwo_image_five_text
                nochoiceImage(nowselect)
                nowselect=5
                todayMytaminViewModel.setselectemojiState(5)
                (activity as todayMytaminActivity).setEnableNextBtnPartTwo(true)
                mBinding?.mytaminFrametwoImageFive?.setBackgroundResource(Constant.frametwo_image_five_src)
            }

        }
    }
    private fun setOnClickjoin(){
        //프레임 2
        mBinding?.mytaminFrametwoImageOne?.setOnClickListener(this)
        mBinding?.mytaminFrametwoImageTwo?.setOnClickListener(this)
        mBinding?.mytaminFrametwoImageThree?.setOnClickListener(this)
        mBinding?.mytaminFrametwoImageFour?.setOnClickListener(this)
        mBinding?.mytaminFrametwoImageFive?.setOnClickListener(this)
        ///
    }
}