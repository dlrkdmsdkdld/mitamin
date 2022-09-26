package kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kotlinx.android.synthetic.main.fragment_mytamin_step_one.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentMytaminStepOneBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.MYTAMIN
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToTimeLine
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel


class MytaminStepOneFragment : Fragment() ,View.OnClickListener{
    private var mBinding : FragmentMytaminStepOneBinding?=null
    private val todayMytaminViewModel by activityViewModels<todayMytaminViewModel>()
    private var timer=0
    private var startbtn =1 // 타이머 시작버튼 처음에는 1 -> 작동하고있는데 멈추고싶으면 2 ->다시하고싶을땐 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMytaminStepOneBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"MytaminStepOneFragment onCreateView")
        val step=todayMytaminViewModel.getstep.value
        if (step==1){
            timer=60
            val parsetime=timer.parseIntToTimeLine()
            mBinding?.mytaminTimerText?.text = parsetime
            mBinding?.mytaminStepOneTitleText?.text= MYTAMIN.step_one_title
            mBinding?.mytaminStepOneDiagnosis?.text = MYTAMIN.step_one_diagnosis
            mBinding?.mytaminStepOneImage?.background= getDrawable(requireContext(),R.drawable.ic_step_one_image)
        }else{
            timer=180
            val parsetime=timer.parseIntToTimeLine()
            mBinding?.mytaminTimerText?.text = parsetime
            mBinding?.mytaminStepOneTitleText?.text= MYTAMIN.step_two_title
            mBinding?.mytaminStepOneDiagnosis?.text = MYTAMIN.step_two_diagnosis
            mBinding?.mytaminStepOneImage?.background= getDrawable(requireContext(),R.drawable.ic_step_two_image)
        }
        todayMytaminViewModel.timerset(timer)
        mBinding?.mytaminStartBtn?.setOnClickListener(this)
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"MytaminStepOneFragment onDestroyView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // todayMytamin_nextBtn.setOnClickListener(this)
    }
    fun firstbreath(){
        // 시간이 1초당 1씩줄는거 코루틴으로 observe후 text에 쏴줌
        if (startbtn==1) { // 스타트버튼 시작
            todayMytaminViewModel.timerStart()
            val timeObserver = Observer<Int> { currentTime ->
                mytamin_timer_text.text = currentTime.parseIntToTimeLine()
                if (currentTime==0){
                    mytamin_timer_text.text ="다시하기"
                    startbtn=0
                    mytamin_start_btn.background= getDrawable(requireContext(),R.drawable.ic_restart_button)
                    //스위치가 체크되어있으면 자동으로 다음 단계로 넘어가짐
                    if(mytamin_switch.isChecked){
                        mytamin_next_btn.performClick()
                        startbtn=2
                    }
                }else{
                    mytamin_start_btn.background= getDrawable(requireContext(),R.drawable.ic_stop_button)
                }
            }
            todayMytaminViewModel.timerCount.observe(this, timeObserver)
            startbtn=2
        }else if (startbtn==2){ //일시정지
            todayMytaminViewModel.timerPause()
            mytamin_start_btn.background= getDrawable(requireContext(),R.drawable.ic_play_button) //  이미지변경
            startbtn=1
        }else if (startbtn==0){
            todayMytaminViewModel.timerset(timer)

            todayMytaminViewModel.timerStart()
            startbtn=2
        }
    }


    override fun onClick(p0: View?) {
        when(p0){
            mytamin_start_btn ->{
                firstbreath()
            }
        }
    }
}