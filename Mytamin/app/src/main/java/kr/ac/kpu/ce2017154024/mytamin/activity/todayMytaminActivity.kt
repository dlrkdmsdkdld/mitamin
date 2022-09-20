package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityTodayMytaminBinding
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel
import androidx.lifecycle.lifecycleScope
import io.reactivex.rxjava3.core.Observable
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToTimeLine
import java.util.*
import java.util.concurrent.TimeUnit

class todayMytaminActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mytaminBinding: ActivityTodayMytaminBinding
    private lateinit var mytaminViewModel: todayMytaminViewModel

    var step = 0 // 마이타민 단계별 차등을 주기위한 코드
    var startbtn =1 // 타이머 시작버튼 처음에는 1 -> 작동하고있는데 멈추고싶으면 2 ->다시하고싶을땐 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"todayMytaminActivity onCreate")
        mytaminBinding = ActivityTodayMytaminBinding.inflate(layoutInflater)
        setContentView(mytaminBinding.root)
        mytaminViewModel=ViewModelProvider(this).get(todayMytaminViewModel::class.java)
        mytamin_startbtn.setOnClickListener(this)
        todayMytamin_nextBtn.setOnClickListener(this)
        mytamin_progress.progress=100


    }

    override fun onClick(p0: View?) {
        when(p0){
            mytamin_startbtn ->{ firstbreath() }
            todayMytamin_nextBtn ->{

                when(step){
                    0->{
                        mytaminViewModel.timerset(15)
                        todayMytamin_nextBtn.isEnabled=false
                        mytamin_explain_text.text=Constant.explainTextStepTwo
                        mytamin_step_text.text = Constant.StepTextstepTwo
                        mytaminViewModel.timerPause()

                    }
                    1 ->{
                        todayMytamin_nextBtn.isEnabled=false
                        mytamin_frameone.visibility=View.INVISIBLE
                        mytamin_startbtn.isEnabled=false
                        mytamin_switch.isEnabled=false
                    }
                }
                step+=1
                //progress 증가
                val currentProgress = mytamin_progress.progress
                mytaminViewModel.getInterval().subscribe(){
                    mytamin_progress.progress=currentProgress + it.toInt()
                }

            }


        }
    }
    fun firstbreath(){
        // 시간이 1초당 1씩줄는거 코루틴으로 observe후 text에 쏴줌
        if (startbtn==1) { // 스타트버튼 시작
            mytaminViewModel.timerStart()
            val timeObserver = Observer<Int> { currentTime ->
                mytamin_startbtn.text = currentTime.parseIntToTimeLine()
                if (currentTime==0){
                    mytamin_startbtn.text ="다시하기"
                    todayMytamin_nextBtn.isEnabled=true
                    startbtn=0
                    //스위치가 체크되어있으면 자동으로 다음 단계로 넘어가짐
                    if(mytamin_switch.isChecked){
                        todayMytamin_nextBtn.performClick()
                        startbtn=2
                    }
                }
            }
            mytaminViewModel.timerCount.observe(this, timeObserver)
            startbtn=2
        }else if (startbtn==2){ //일시정지
            mytaminViewModel.timerPause()
            startbtn=1
        }else if (startbtn==0){
            if(step==0) mytaminViewModel.timerset(10)
            else mytaminViewModel.timerset(15)

            mytaminViewModel.timerStart()
            startbtn=2
        }
    }
}