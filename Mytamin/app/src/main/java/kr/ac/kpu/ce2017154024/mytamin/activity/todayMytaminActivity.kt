package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.MytaminCloseDialog
import kr.ac.kpu.ce2017154024.mytamin.UI.MytaminCloseDialog.OnClickedDialogBtn
import kr.ac.kpu.ce2017154024.mytamin.UI.MytaminCorrectionDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityTodayMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.*
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel

class todayMytaminActivity : AppCompatActivity(), View.OnClickListener,MytaminCorrectionDialog.OnClickedDialogBtn  {

    private lateinit var navController: NavController

    private lateinit var mytaminBinding: ActivityTodayMytaminBinding
    private lateinit var mytaminViewModel: todayMytaminViewModel
    private var step=0  // 마이타민 단계별 차등을 주기위한 코드
    private lateinit var resultBoolean :Status
    private lateinit var LatestMytamin :LatestMytamin

    private lateinit var dialog:MytaminCloseDialog

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
//        if (can){
//            mytaminBinding.mytaminNextBtn.setTextColor(resources.getColor(R.color.primary,null))
//        }else{
//            mytaminBinding.mytaminNextBtn.setTextColor(resources.getColor(R.color.background_gray,null))
//
//        }
    }
    fun clickPerformNextBtn(){
        mytaminBinding.mytaminNextBtn.performClick()
    }
    fun setEnableNextBtnPartTwo(can:Boolean){
      //  mytamin_next_btn_part2.isEnabled = can
    //    if (can){
   //         mytamin_next_btn_part2.background = getDrawable(R.drawable.ic_large_button_abled)
   //     }else{mytamin_next_btn_part2.background = getDrawable(R.drawable.ic_large_button_disabled)}
    }
    fun setEnableCorrection(can:Boolean){
        mytamin_correction_btn.isEnabled=can
        if (can){
            mytamin_correction_btn.background = getDrawable(R.drawable.ic_large_button_outline)
        }else{mytamin_correction_btn.background = getDrawable(R.drawable.ic_large_button_disabled)}

    }
    fun finishTimer(){
        mytaminBinding.mytaminPassBtn.visibility=View.GONE
        mytaminBinding.mytaminNextBtn.visibility=View.VISIBLE
    }

    fun replaceFragment(Pstep:Int){
        when(Pstep){
            1->{
                val MytaminStepOneFragment = MytaminStepOneFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepOneFragment).commit()

                mytamin_progressbar.progress =200
                mytaminBinding.mytaminPassBtn.visibility=View.VISIBLE
                mytaminBinding.mytaminNextBtn.visibility=View.GONE
                mytamin_correction_btn.visibility=View.GONE
            }
            2->{
                val MytaminStepOneFragment = MytaminStepOneFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepOneFragment).commit()


                mytamin_progressbar.progress =400

                mytaminBinding.mytaminPassBtn.visibility=View.VISIBLE
                mytaminBinding.mytaminNextBtn.visibility=View.GONE
                mytamin_correction_btn.visibility=View.GONE
             //   setEnableNextBtnPartTwo(false)
            }
            3->{
                val MytaminStepThreeFragment = MytaminStepThreeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepThreeFragment).commit()
                mytamin_progressbar.progress =600
                mytaminBinding.mytaminPassBtn.visibility=View.GONE
                resultBoolean.reportIsDone?.let {
                    if (it) {
                        mytaminBinding.mytaminNextBtn.visibility=View.GONE
                        mytamin_correction_btn.visibility=View.VISIBLE
                    }else{
                        mytaminBinding.mytaminNextBtn.visibility=View.VISIBLE
                        mytamin_correction_btn.visibility=View.GONE

                    }
                }

            }
            4->{
                val MytaminStepFourFragment = MytaminStepFourFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepFourFragment).commit()
                mytaminBinding.mytaminPassBtn.visibility=View.GONE
                resultBoolean.reportIsDone?.let {
                    if (it) {
                        mytaminBinding.mytaminNextBtn.visibility=View.GONE
                        mytamin_correction_btn.visibility=View.VISIBLE
                    }else{
                        mytaminBinding.mytaminNextBtn.visibility=View.VISIBLE
                        mytamin_correction_btn.visibility=View.GONE

                    }
                }
            }
            5->{
                val MytaminStepFiveFragment = MytaminStepFiveFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepFiveFragment).commit()
                mytaminBinding.mytaminPassBtn.visibility=View.GONE
                resultBoolean.reportIsDone?.let {
                    if (it) {
                        mytaminBinding.mytaminNextBtn.visibility=View.GONE
                        mytamin_correction_btn.visibility=View.VISIBLE
                    }else{
                        mytaminBinding.mytaminNextBtn.visibility=View.VISIBLE
                        mytamin_correction_btn.visibility=View.GONE

                    }
                }
            }
            6->{
                val MytaminStepSixFragment = MytaminStepSixFragment()
                supportFragmentManager.beginTransaction().replace(R.id.today_fragmentcontainer,MytaminStepSixFragment).commit()
                mytamin_progressbar.progress =800
                mytaminBinding.mytaminPassBtn.visibility=View.GONE
                resultBoolean.careIsDone?.let {
                    if (it) {
                        mytaminBinding.mytaminNextBtn.visibility=View.GONE
                        mytamin_correction_btn.visibility=View.VISIBLE
                    }else{
                        mytaminBinding.mytaminNextBtn.visibility=View.VISIBLE
                        mytamin_correction_btn.visibility=View.GONE

                    }
                }
//                mytamin_layout1.visibility=View.INVISIBLE
//                mytamin_pass_btn.isEnabled=false
//                mytamin_next_btn.isEnabled=false
//                mytamin_correction_btn.isEnabled=false
            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    fun setStep(k : Int){
        this.step = k
        mytaminViewModel.setstep(k)
    }

    fun next_btn(step:Int){
        Log.d(TAG,"Step -> $step")
        when(step){
            2->{
                    mytaminViewModel.completeBreath()
            }
            3->{
                mytaminViewModel.completeSense()
            }
            6->{mytaminViewModel.completeReport()//5단계까지 마치고 next버튼눌렀을때 데이터전송
                 }
            7->{
                mytaminViewModel.completeCare()
                val intent= Intent(this,MainActivity::class.java)
                finishAffinity()
                startActivity(intent)

            }
        }

        replaceFragment(step)
        mytaminViewModel.timerDestory()
        mytaminViewModel.setstep(step)
        Log.d(TAG,"현재 단계 : -> $step")
    }
    fun clickExitBtn(step: Int){
        when(step){
            1 ->{
                dialog = MytaminCloseDialog(this,"숨 고르기를 그만하고 나가시겠어요?","진행 시간은 저장되지 않습니다")
                dialog.show()
                dialog.setOnClickListener(this)
            }
            2->{
                dialog = MytaminCloseDialog(this,"감각 깨우기를 그만하고 나가시겠어요?","진행 시간은 저장되지 않습니다")
                dialog.show()
                dialog.setOnClickListener(this)

            }
            3,4,5->{
                if (resultBoolean.reportIsDone){
                    dialog = MytaminCloseDialog(this,"하루 진단하기 수정을 그만두시겠어요?","수정 중이었던 내용은 저장되지 않습니다")
                }else{
                    dialog = MytaminCloseDialog(this,"하루 진단하기를 그만하고 나가시겠어요?","진단 중이었던 내용은 저장되지 않습니다")
                }
                dialog.show()
                dialog.setOnClickListener(this)
            }
            6->{
                if (resultBoolean.careIsDone){
                    dialog = MytaminCloseDialog(this,"칭찬 처방하기 수정을 그만두시겠어요?","수정 중이었던 내용은 저장되지 않습니다")
                }else{
                    dialog = MytaminCloseDialog(this,"칭찬 처방하기를 그만하고 나가시겠어요?","칭찬 중이었던 내용은 저장되지 않습니다")
                }
                dialog.show()
                dialog.setOnClickListener(this)
            }


        }

    }
    override fun onClick(p0: View?) {
        step= mytaminViewModel.getstep.value!!
        when(p0){

            mytamin_next_btn ->{
                step+=1
                mytaminViewModel.setstep(step)
                next_btn(step)
                setEnableNextBtn(false)
            }
//            mytamin_next_btn_part2 ->{
//                step+=1
//                next_btn(step)
//                setEnableNextBtnPartTwo(false)
//            }
            mytamin_pass_btn ->{
                step+=1
                mytaminViewModel.timerDestory()

                replaceFragment(step)
                mytaminViewModel.setstep(step)
                Log.d(TAG,"현재 단계 : -> $step")
            }
            mytamin_exit_btn ->{
                mytaminViewModel.timerDestory()//타이머 코루틴 종료
                clickExitBtn(step)
            //    val intent= Intent(this,MainActivity::class.java)
            //    finishAffinity()
            //    startActivity(intent)
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
                else if(mytaminViewModel.getstatus.value?.careIsDone==true  && step==6){
                    Log.d(TAG,"LatestMytamin token ::: ${PrivateUserDataSingleton.accessToken}")
                    Log.d(TAG,"LatestMytamin.careId ::: ${LatestMytamin.careId}")
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
       // mytamin_next_btn_part2.setOnClickListener(this)
        mytamin_correction_btn.setOnClickListener(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"마이타민 액티비티 파괴")
    }

    override fun OnNegativeBtn() {
        dialog.dismiss()
    }

    override fun OnPositiveBtn() {
        dialog.dismiss()
        val intent= Intent(this,MainActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

}