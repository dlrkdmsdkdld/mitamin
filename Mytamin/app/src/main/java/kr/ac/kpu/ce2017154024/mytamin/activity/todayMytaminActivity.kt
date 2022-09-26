package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityTodayMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.MytaminStepOneFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.MytaminStepThreeFragment
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import java.util.*

class todayMytaminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mytaminBinding: ActivityTodayMytaminBinding
    private lateinit var mytaminViewModel: todayMytaminViewModel

    private var step=0  // 마이타민 단계별 차등을 주기위한 코드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"todayMytaminActivity onCreate")
        step=intent.getIntExtra("step",0)
        Log.d(TAG,"step $step")

        mytaminBinding = ActivityTodayMytaminBinding.inflate(layoutInflater)
        setContentView(mytaminBinding.root)
        mytaminViewModel=ViewModelProvider(this).get(todayMytaminViewModel::class.java)
        mytaminViewModel.setstep(step)
        mytamin_next_btn.setOnClickListener(this)
        mytamin_exit_btn.setOnClickListener(this)
       // setOnClickjoin()
        //stepParse(step)
        replaceFragment(step)

    }
    fun replaceFragment(Pstep:Int){
        when(Pstep){
            1->{
                val MytaminStepOneFragment = MytaminStepOneFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepOneFragment).commit()
                mytamin_vitamin_right.visibility=View.VISIBLE
                mytamin_vitamin_left.visibility=View.INVISIBLE
            }
            2->{
                val MytaminStepOneFragment = MytaminStepOneFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepOneFragment).commit()
                mytamin_vitamin_right.visibility=View.INVISIBLE
                mytamin_vitamin_left.visibility=View.VISIBLE
                mytamin_indicator_two.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_three.setImageResource(R.drawable.ic_idcator_no)
                mytamin_indicator_four.setImageResource(R.drawable.ic_idcator_no)
            }
            3->{
                val MytaminStepThreeFragment = MytaminStepThreeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepThreeFragment).commit()
                mytamin_indicator_two.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_three.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_four.setImageResource(R.drawable.ic_idcator_no)

            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun onClick(p0: View?) {
        when(p0){

            mytamin_next_btn ->{
                step+=1
                replaceFragment(step)
                mytaminViewModel.setstep(step)


            }
            mytamin_pass_btn ->{
                step+=1
                replaceFragment(step)
                mytaminViewModel.setstep(step)
            }
            mytamin_back_btn ->{
                onBackPressed()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"마이타민 액티비티 파괴")
    }
//    fun submitDay(inputMonth:Int=0,inputDay:Int=0){
//        if(inputDay==0 && inputMonth==0){
//            val tmp = System.currentTimeMillis()
//            val timedata= Date(tmp)
//            val dateFormat = SimpleDateFormat("M월d일")
//            val parseTime=dateFormat.format(timedata)
//            Log.d(TAG,"todayMytaminActivity submitDay parseTime $parseTime")
//            mytamin_frametwo_calendar_text.text = parseTime
//        }else{
//            mytamin_frametwo_calendar_text.text = "${inputMonth}월 ${inputDay}일"
//        }
//    }


    /*
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
    }*/
}