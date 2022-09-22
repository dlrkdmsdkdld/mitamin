package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityTodayMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.MytaminBottomSheetFragment
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.explainTextStepOne
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.explainTextStepTwo
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToTimeLine
import java.text.SimpleDateFormat
import java.util.*

class todayMytaminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mytaminBinding: ActivityTodayMytaminBinding
    private lateinit var mytaminViewModel: todayMytaminViewModel

    private var step=0  // 마이타민 단계별 차등을 주기위한 코드
    var startbtn =1 // 타이머 시작버튼 처음에는 1 -> 작동하고있는데 멈추고싶으면 2 ->다시하고싶을땐 0
    var selectemojiState = 0 // 이모지 버튼 클릭했을 때 저장
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"todayMytaminActivity onCreate")
        step=intent.getIntExtra("step",0)
        Log.d(TAG,"step $step")

        mytaminBinding = ActivityTodayMytaminBinding.inflate(layoutInflater)
        setContentView(mytaminBinding.root)
        mytaminViewModel=ViewModelProvider(this).get(todayMytaminViewModel::class.java)
        setOnClickjoin()
        stepParse(step)


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun onClick(p0: View?) {
        when(p0){
            mytamin_start_btn->{
                firstbreath()

            }
            todayMytamin_nextBtn ->{
                step+=1
                stepParse(step,false)
                //progress 증가
                val currentProgress = mytamin_progress.progress
                mytaminViewModel.getInterval().subscribe(){
                    mytamin_progress.progress=currentProgress + it.toInt()
                }

            }
            mytamin_back_btn ->{
                onBackPressed()
            }
            mytamin_frametwo_image_one->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_one_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_one_text
                selectemojiState=1
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_image_two->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_two_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_two_text
                selectemojiState=2
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_image_three->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_three_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_three_text
                selectemojiState=3
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_image_four->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_four_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_four_text
                todayMytamin_nextBtn.isEnabled=true
                selectemojiState=4
            }
            mytamin_frametwo_image_five->{
                mytamin_frametwo_select_ImageView.setImageResource(Constant.frametwo_image_five_src)
                mytamin_frametwo_state.text  = Constant.frametwo_image_five_text
                selectemojiState=5
                todayMytamin_nextBtn.isEnabled=true
            }
            mytamin_frametwo_calendar_btn->{
                val bottomSheetDialogFragment=MytaminBottomSheetFragment()
                bottomSheetDialogFragment.show(supportFragmentManager,bottomSheetDialogFragment.tag)

            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"마이타민 액티비티 파괴")
    }
    fun submitDay(inputMonth:Int=0,inputDay:Int=0){
        if(inputDay==0 && inputMonth==0){
            val tmp = System.currentTimeMillis()
            val timedata= Date(tmp)
            val dateFormat = SimpleDateFormat("M월d일")
            val parseTime=dateFormat.format(timedata)
            Log.d(TAG,"todayMytaminActivity submitDay parseTime $parseTime")
            mytamin_frametwo_calendar_text.text = parseTime
        }else{
            mytamin_frametwo_calendar_text.text = "${inputMonth}월 ${inputDay}일"
        }
    }

    fun firstbreath(){
        // 시간이 1초당 1씩줄는거 코루틴으로 observe후 text에 쏴줌
        if (startbtn==1) { // 스타트버튼 시작
            mytaminViewModel.timerStart()
            val timeObserver = Observer<Int> { currentTime ->
                mytamin_timer_text.text = currentTime.parseIntToTimeLine()
                if (currentTime==0){
                    mytamin_timer_text.text ="다시하기"
                    startbtn=0
                    mytamin_start_btn.background= getDrawable(R.drawable.ic_baseline_change_circle_24)
                    //스위치가 체크되어있으면 자동으로 다음 단계로 넘어가짐
                    if(mytamin_switch.isChecked){
                        todayMytamin_nextBtn.performClick()
                        startbtn=2
                    }
                }else{
                    mytamin_start_btn.background= getDrawable(R.drawable.ic_baseline_pause_circle_filled_24)
                }
            }
            mytaminViewModel.timerCount.observe(this, timeObserver)
            startbtn=2
        }else if (startbtn==2){ //일시정지
            mytaminViewModel.timerPause()
            mytamin_start_btn.background= getDrawable(R.drawable.ic_baseline_play_circle_filled_24) //  이미지변경
            startbtn=1
        }else if (startbtn==0){
            if(step==0) mytaminViewModel.timerset(60)
            else mytaminViewModel.timerset(180)

            mytaminViewModel.timerStart()
            startbtn=2
        }
    }
    fun stepParse(inputStep:Int,startNow:Boolean=true){
        when(inputStep){
            0 ->{
                if (startNow){
                    mytamin_progress.progress=100
                }
                mytamin_step_text.text = "숨 고르기"
                mytamin_frameone.visibility=View.VISIBLE
                mytamin_explain_text.text = explainTextStepOne
                mytamin_frametwo.visibility=View.INVISIBLE
                mytamin_start_btn.isEnabled=true
                mytamin_timer_text.text = "01:00"
                mytaminViewModel.timerset(60)
                //프레임2 버튼 클릭안되게하는 작업
                mytamin_frametwo_calendar_btn.isEnabled=false
                mytamin_frametwo_image_one.isEnabled=false
                mytamin_frametwo_image_two.isEnabled=false
                mytamin_frametwo_image_three.isEnabled=false
                mytamin_frametwo_image_four.isEnabled=false
                mytamin_frametwo_image_five.isEnabled=false

            }
            1 ->{
                mytamin_step_text.text = "감각 깨우기"
                if (startNow){
                    mytamin_progress.progress=200
                }
                mytamin_frameone.visibility=View.VISIBLE
                mytamin_explain_text.text = explainTextStepTwo
                mytamin_frametwo.visibility=View.INVISIBLE
                mytamin_start_btn.isEnabled=true
                mytamin_timer_text.text = "03:00"
                mytaminViewModel.timerset(180)
                mytamin_start_btn.background= getDrawable(R.drawable.ic_baseline_play_circle_filled_24) //  이미지변경
                //프레임2 버튼 클릭안되게하는 작업
                mytamin_frametwo_calendar_btn.isEnabled=false
                mytamin_frametwo_image_one.isEnabled=false
                mytamin_frametwo_image_two.isEnabled=false
                mytamin_frametwo_image_three.isEnabled=false
                mytamin_frametwo_image_four.isEnabled=false
                mytamin_frametwo_image_five.isEnabled=false
            }
            2->{
                if (startNow){
                    mytamin_progress.progress=300
                }
                mytamin_frameone.visibility=View.INVISIBLE
                mytamin_frametwo.visibility=View.VISIBLE
                mytamin_start_btn.isEnabled=false
                todayMytamin_nextBtn.isEnabled=false
                //프레임2 버튼 클릭되게하는 작업
                submitDay()
                mytamin_frametwo_calendar_btn.isEnabled=true
                mytamin_frametwo_image_one.isEnabled=true
                mytamin_frametwo_image_two.isEnabled=true
                mytamin_frametwo_image_three.isEnabled=true
                mytamin_frametwo_image_four.isEnabled=true
                mytamin_frametwo_image_five.isEnabled=true
            }

        }
    }
    private fun setOnClickjoin(){
        mytamin_start_btn.setOnClickListener(this)
        todayMytamin_nextBtn.setOnClickListener(this)
        mytamin_back_btn.setOnClickListener(this)
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