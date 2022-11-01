package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivitySettingBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.record.BottomYearMonthFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.setting.AlarmCalendarFragment

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mbinding :ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

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
}