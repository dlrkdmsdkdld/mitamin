package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivitySettingBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.record.BottomYearMonthFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.setting.AlarmCalendarFragment
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import kr.ac.kpu.ce2017154024.mytamin.viewModel.settingViewmodel

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mbinding :ActivitySettingBinding
    private lateinit var customProgressDialog: Dialog
    lateinit var mysettingviewmodel: settingViewmodel
    var first:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        mysettingviewmodel=ViewModelProvider(this).get(settingViewmodel::class.java)

        customProgressDialog = LoadingDialog(this)
        customProgressDialog.show()
        alramStateObserver()
        mytaminAlarmTimeObserver()
        mbinding?.settingEatAlarmBtn?.setOnClickListener(this)
        mbinding?.settingEatAlarm.setOnCheckedChangeListener { compoundButton, b ->
            //처음에 oncreate 될때도 자꾸 바텀시트가 열려서 처음 들어온건지 확ㅇ니
            if (b && !first){
                Log.d(TAG,"활성화됨")
                mbinding?.settingEatAlarmBtn.performClick()
            }else if(!b && !first) {
                mytaminAlarmOffAPI()
                Log.d(TAG,"비활성화됨")
            }
            first=false

        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.settingEatAlarmBtn ->{
                val calendarFragment = AlarmCalendarFragment()
                calendarFragment.show(supportFragmentManager,calendarFragment.tag)

            }
        }
    }
    private fun mytaminAlarmTimeObserver(){
        mysettingviewmodel.getmytaminTime.observe(this,Observer{ resetTime->
            mysettingviewmodel.getmytaminAlarmData.value?.let {
                    customProgressDialog.show()
                    mysettingviewmodel.mytaminAlarmOn(resetTime)
                    first=true
                    mbinding?.settingEatAlarm.isChecked=true
                    first=false
            }
        })
    }
    private fun alramStateObserver(){
        mysettingviewmodel.getmytaminAlarmData.observe(this, Observer {
            mbinding?.settingEatAlarm.isChecked=it.isOn
            mbinding?.settingEatAlarmBtn.isEnabled=it.isOn
            if (it.isOn){
                mbinding?.settingEatTimeText.setTextColor(resources.getColor(R.color.primary,null))
                mbinding?.settingEatTimeImage.setBackgroundResource(R.drawable.icon_arrow_down_primary)
            }else{
                mbinding?.settingEatTimeText.setTextColor(resources.getColor(R.color.notEnabled,null))
                mbinding?.settingEatTimeImage.setBackgroundResource(R.drawable.icon_arrow_down_gray)

            }
            first=false
            it.whentime?.let { mbinding?.settingEatTimeText.text =it }
            if (customProgressDialog.isShowing)customProgressDialog.dismiss()
        })
        mysettingviewmodel.getmyDayData.observe(this, Observer {
            mbinding?.settingMydaySwitch.isChecked=it.isOn
            it.whentime?.let{mbinding?.settingMydayText.text=it}
            if (customProgressDialog.isShowing)customProgressDialog.dismiss()
        })
    }
    private fun mytaminAlarmOffAPI(){
        customProgressDialog.show()
        HistoryRetrofitManager.instance.mytaminAlarmOff {
            mysettingviewmodel.getAlarmAPI()
        }
    }
}