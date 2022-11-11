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
import kotlinx.android.synthetic.main.fragment_mytamin_step_one.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
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
            mBinding?.mytaminStepOneTimerImage?.background = getDrawable(requireContext(),R.drawable.ic_step_one_image)
        }else{
            timer=180
            val parsetime=timer.parseIntToTimeLine()
            mBinding?.mytaminTimerText?.text = parsetime
            mBinding?.mytaminStepOneTitleText?.text= MYTAMIN.step_two_title
            mBinding?.mytaminStepOneDiagnosis?.text = MYTAMIN.step_two_diagnosis
            mBinding?.mytaminStepOneTimerImage?.background = getDrawable(requireContext(),R.drawable.ic_step_two_image)
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
        val auto = todayMytaminViewModel._auto.value
        if (auto == true){
            mBinding?.mytaminSwitch?.isChecked=true
            firstbreath()
        }
    }
    fun firstbreath(){
        // 시간이 1초당 1씩줄는거 코루틴으로 observe후 text에 쏴줌
        if (startbtn==1) { // 스타트버튼 시작
            todayMytaminViewModel.timerStart()
            val timeObserver = Observer<Int> { currentTime ->
                mBinding?.mytaminTimerText?.text = currentTime.parseIntToTimeLine()
                Log.d(Constant.TAG,"currentTime currentTime$currentTime")
                if (currentTime==0){
                    startbtn=0
                    mBinding?.mytaminStartBtn?.background= getDrawable(requireContext(),R.drawable.ic_restart_button)
                    //스위치가 체크되어있으면 자동으로 다음 단계로 넘어가짐
                    if(mBinding?.mytaminSwitch?.isChecked!!){
                        val nowstep = todayMytaminViewModel.getstep.value
                        todayMytaminViewModel.setstep(nowstep!!+1)
                        todayMytaminViewModel.timerDestory()
                        todayMytaminViewModel.autoset(true)
                        (activity as todayMytaminActivity).replaceFragment(nowstep!!+1)
                        startbtn=2
                    }else{
                        todayMytaminViewModel.timerDestory()
                        (activity as todayMytaminActivity).setEnableNextBtn(true)
                    }
                }else{
                    mBinding?.mytaminStartBtn?.background= getDrawable(requireContext(),R.drawable.ic_stop_button)
                }
            }
            todayMytaminViewModel.timerCount.observe(viewLifecycleOwner, timeObserver)
            startbtn=2
        }else if (startbtn==2){ //일시정지
            todayMytaminViewModel.timerPause()
            mBinding?.mytaminStartBtn?.background= getDrawable(requireContext(),R.drawable.ic_play_button) //  이미지변경
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