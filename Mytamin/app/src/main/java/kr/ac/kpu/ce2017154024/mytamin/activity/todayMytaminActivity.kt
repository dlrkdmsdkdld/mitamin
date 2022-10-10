package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityTodayMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.*
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel

class todayMytaminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mytaminBinding: ActivityTodayMytaminBinding
    private lateinit var mytaminViewModel: todayMytaminViewModel
    private val workManager=WorkManager.getInstance(application)
    private var step=0  // 마이타민 단계별 차등을 주기위한 코드
    private lateinit var resultBoolean :Status
    private lateinit var LatestMytamin :LatestMytamin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"todayMytaminActivity onCreate")
        step=intent.getIntExtra("step",0)
        Log.d(TAG,"step $step")

        var bundle = intent.getBundleExtra("bundle")
        resultBoolean = bundle?.getSerializable("statusData") as Status
       Log.d(TAG,"resultBoolean $resultBoolean")
       Log.d(TAG,"resultBooleantype ${resultBoolean?.javaClass?.name}")
        if (resultBoolean.reportIsDone || resultBoolean.careIsDone){
            var bundleL = intent.getBundleExtra("bundleL")
            LatestMytamin = bundleL?.getSerializable("LatestMytamin") as LatestMytamin


        }

        mytaminBinding = ActivityTodayMytaminBinding.inflate(layoutInflater)
        setContentView(mytaminBinding.root)
        mytaminViewModel=ViewModelProvider(this).get(todayMytaminViewModel::class.java)
        mytaminViewModel.setstep(step)
        mytaminViewModel.setstatus(resultBoolean)
        if (resultBoolean.reportIsDone ){
            mytaminViewModel.reportset(LatestMytamin.todayReport)
            mytaminViewModel.setselectemojiState(LatestMytamin.mentalConditionCode)
        }
        if (resultBoolean.careIsDone){
            mytaminViewModel.setcareMsg1(LatestMytamin.careMsg1)
            mytaminViewModel.setcareMsg2(LatestMytamin.careMsg2)
            mytaminViewModel.setcareCategoryCodeMsg(LatestMytamin.careCategory)
        }
        connectClicklistner()
       // setOnClickjoin()
        //stepParse(step)
        replaceFragment(step)
        setEnableCorrection(false)

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
    fun setEnableCorrection(can:Boolean){
        mytamin_correction_btn.isEnabled=can
        if (can){
            mytamin_correction_btn.background = getDrawable(R.drawable.ic_large_button_outline)
        }else{mytamin_correction_btn.background = getDrawable(R.drawable.ic_large_button_disabled)}

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
                mytamin_layout1.visibility=View.INVISIBLE
                mytamin_pass_btn.isEnabled=false
                mytamin_next_btn.isEnabled=false
                mytamin_correction_btn.isEnabled=false
                mytamin_layout2.visibility=View.VISIBLE
            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    fun Correctionstep3(){
        if (resultBoolean.reportIsDone==true){
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("수정하시겠습니까?")
                .setMessage("확인버튼을 누르시면 수정이 되고 거절하시면 그다음 단계로넘어갑니다.")
                .setPositiveButton("수정",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 수정 버튼 선택시 수행

                             setEnableCorrection(false)
                    })
                .setNegativeButton("넘어가기",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 넘어가기 버튼 선택시 수행

                        if (resultBoolean.careIsDone){ // 4단계도했으면 메인액티비티로 돌아감
                            val intent= Intent(this,MainActivity::class.java)
                            finishAffinity()
                            startActivity(intent)
                        }else{ // 4단계안한상태에서 넘어가면 4단계로 넘어감감
                           this.step =6
                            setEnableCorrection(false)
                            replaceFragment(step)
                        }
                    }
                )
            builder.create()
            builder.show()
        }else{
            setEnableCorrection(false)

        }
    }
    fun Correctionstep4(){ //4단계를 미리한상태에서 3단계에서 다음으로넘어갔을때
        if (resultBoolean.careIsDone){
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("수정하시겠습니까?")
                .setMessage("확인버튼을 누르시면 수정이 되고 넘어가면 메인메뉴로 넘어갑니다.")
                .setPositiveButton("수정",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 수정 버튼 선택시 수행

                        setEnableCorrection(false)
                    })
                .setNegativeButton("넘어가기",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 넘어가기 버튼 선택시 수행
                        val intent= Intent(this,MainActivity::class.java)
                        finishAffinity()
                        startActivity(intent)
                    }
                )
            builder.create()
            builder.show()
        }else{
            setEnableCorrection(false)

        }
    }

    fun next_btn(step:Int){

        when(step){
            2->{
                    mytaminViewModel.completeBreath()
            }
            3->{
                mytaminViewModel.completeSense()
                Correctionstep3()
            }
            6->{mytaminViewModel.completeReport()//5단계까지 마치고 next버튼눌렀을때 데이터전송
                Correctionstep4()
                 }
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
                if (step==3){
                    Correctionstep3()
                }

                replaceFragment(step)
                mytaminViewModel.setstep(step)
                Log.d(TAG,"현재 단계 : -> $step")
            }
            mytamin_exit_btn ->{
                mytaminViewModel.timerDestory()//타이머 코루틴 종료
                val intent= Intent(this,MainActivity::class.java)
                finishAffinity()
                startActivity(intent)
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
            mytamin_correction_btn->{
                if(mytaminViewModel.getstatus.value?.reportIsDone==true && step==5){
                    mytaminViewModel.CorrectionReport(LatestMytamin.reportId)
                }
                if(mytaminViewModel.getstatus.value?.careIsDone==true  && step==6){
                    mytaminViewModel.correctionCare(LatestMytamin.careId)
                }

                val intent= Intent(this,MainActivity::class.java)
                finishAffinity()
                startActivity(intent)

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
        mytamin_correction_btn.setOnClickListener(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"마이타민 액티비티 파괴")
    }

}