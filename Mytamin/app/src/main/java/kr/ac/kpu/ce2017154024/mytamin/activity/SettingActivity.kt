package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivitySettingBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.record.BottomYearMonthFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.setting.AlarmCalendarFragment
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mbinding :ActivitySettingBinding
    private lateinit var customProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        customProgressDialog = LoadingDialog(this)
        getAlramStateAPI()
        mbinding?.settingEatAlarmBtn?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.settingEatAlarmBtn ->{
                val calendarFragment = AlarmCalendarFragment()
                calendarFragment.show(supportFragmentManager,calendarFragment.tag)

            }
        }
    }
    private fun getAlramStateAPI(){
        customProgressDialog.show()
        HistoryRetrofitManager.instance.getAlarmState{responseStatus, mytaminAlarm, mydayAlarm ->
            customProgressDialog.dismiss()
            if (responseStatus==RESPONSE_STATUS.OKAY){
                mbinding?.settingEatAlarm.isChecked=mytaminAlarm!!.isOn
                mytaminAlarm!!.whentime?.let { mbinding?.settingEatTimeText.text =it }
                mbinding?.settingMydaySwitch.isChecked =mytaminAlarm!!.isOn
                mydayAlarm!!.whentime?.let { mbinding?.settingMydayText.text=it }

            }
        }
    }
}