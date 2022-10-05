package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityTodayMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.*
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel

class todayMytaminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mytaminBinding: ActivityTodayMytaminBinding
    private lateinit var mytaminViewModel: todayMytaminViewModel
    private val workManager=WorkManager.getInstance(application)
    private var step=0  // 마이타민 단계별 차등을 주기위한 코드
    private lateinit var resultBoolean :Status
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"todayMytaminActivity onCreate")
        step=intent.getIntExtra("step",0)
        Log.d(TAG,"step $step")

        var bundle = intent.getBundleExtra("bundle")
        resultBoolean = bundle?.getSerializable("statusData") as Status
       Log.d(TAG,"resultBoolean $resultBoolean")
       Log.d(TAG,"resultBooleantype ${resultBoolean?.javaClass?.name}")

        mytaminBinding = ActivityTodayMytaminBinding.inflate(layoutInflater)
        setContentView(mytaminBinding.root)
        mytaminViewModel=ViewModelProvider(this).get(todayMytaminViewModel::class.java)
        mytaminViewModel.setstep(step)
        connectClicklistner()
       // setOnClickjoin()
        //stepParse(step)
        replaceFragment(step)

    }
    fun setEnableNextBtn(can:Boolean){
        mytamin_next_btn.isEnabled = can
        if (can){
            mytamin_next_btn.background = getDrawable(R.drawable.ic_large_button_abled)
        }else{mytamin_next_btn.background = getDrawable(R.drawable.ic_large_button_disabled)}
    }
    fun setEnableNextBtnPartTwo(can:Boolean){
        mytamin_next_btn_part2.isEnabled = can
        if (can){
            mytamin_next_btn_part2.background = getDrawable(R.drawable.ic_large_button_abled)
        }else{mytamin_next_btn_part2.background = getDrawable(R.drawable.ic_large_button_disabled)}
    }
    fun replaceFragment(Pstep:Int){
        when(Pstep){
            1->{
                val MytaminStepOneFragment = MytaminStepOneFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepOneFragment).commit()
                mytamin_vitamin_right.visibility=View.VISIBLE
                mytamin_vitamin_left.visibility=View.INVISIBLE
                mytamin_indicator_two.setImageResource(R.drawable.ic_idcator_no)
            }
            2->{
                val MytaminStepOneFragment = MytaminStepOneFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepOneFragment).commit()
                mytamin_vitamin_right.visibility=View.INVISIBLE
                mytamin_vitamin_left.visibility=View.VISIBLE
                mytamin_indicator_two.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_three.setImageResource(R.drawable.ic_idcator_no)

                mytamin_layout1.visibility=View.VISIBLE
                mytamin_pass_btn.isEnabled=true
                setEnableNextBtnPartTwo(false)
                mytamin_layout2.visibility=View.INVISIBLE
            }
            3->{
                val MytaminStepThreeFragment = MytaminStepThreeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepThreeFragment).commit()
                setEnableNextBtnPartTwo(false)
                mytamin_indicator_two.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_three.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_four.setImageResource(R.drawable.ic_idcator_no)
                mytamin_layout1.visibility=View.INVISIBLE
                mytamin_pass_btn.isEnabled=false
                mytamin_next_btn.isEnabled=false
                mytamin_layout2.visibility=View.VISIBLE
            }
            4->{
                val MytaminStepFourFragment = MytaminStepFourFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepFourFragment).commit()

            }
            5->{
                val MytaminStepFiveFragment = MytaminStepFiveFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepFiveFragment).commit()

            }
            6->{
                val MytaminStepSixFragment = MytaminStepSixFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepSixFragment).commit()
                mytamin_indicator_two.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_three.setImageResource(R.drawable.ic_indicator_yes)
                mytamin_indicator_four.setImageResource(R.drawable.ic_indicator_yes)
            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    fun next_btn(step:Int){

        when(step){
            2->{
                    mytaminViewModel.completeBreath()
            }
            3->{
                mytaminViewModel.completeSense()
            }
            6->mytaminViewModel.completeReport()//5단계까지 마치고 next버튼눌렀을때 데이터전송
            7->{
                mytaminViewModel.completeCare()
                val intent= Intent(this,MainActivity::class.java)
                finishAffinity()
                startActivity(intent)

            }
        }

/*        if(resultBoolean.careIsDone==false){
            val intent= Intent(this,MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }*/
        replaceFragment(step)
        mytaminViewModel.timerDestory()
        mytaminViewModel.setstep(step)
        Log.d(TAG,"현재 단계 : -> $step")
    }
    override fun onClick(p0: View?) {
        step= mytaminViewModel.getstep.value!!
        when(p0){

            mytamin_next_btn ->{
                step+=1
                next_btn(step)
                setEnableNextBtn(false)
            }
            mytamin_next_btn_part2 ->{
                step+=1
                next_btn(step)
                setEnableNextBtnPartTwo(false)
            }
            mytamin_pass_btn ->{
                step+=1
                mytaminViewModel.timerDestory()
                replaceFragment(step)
                mytaminViewModel.setstep(step)
                Log.d(TAG,"현재 단계 : -> $step")
            }
            mytamin_exit_btn ->{
                onBackPressed()
                mytaminViewModel.timerDestory()//타이머 코루틴 종료
            }
            mytamin_back_btn->
            {
                setEnableNextBtn(false)
                step-=1
                mytaminViewModel.timerDestory()
                replaceFragment(step)
                mytaminViewModel.setstep(step)
                Log.d(TAG,"현재 단계 : -> $step")
            }

        }
    }

    fun connectClicklistner(){
        setEnableNextBtn(false)
        mytamin_next_btn.setOnClickListener(this)
        mytamin_exit_btn.setOnClickListener(this)
        mytamin_pass_btn.setOnClickListener(this)
        mytamin_back_btn.setOnClickListener(this)
        mytamin_next_btn_part2.setOnClickListener(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"마이타민 액티비티 파괴")
    }

}