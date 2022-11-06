package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityWeeklyBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HomeRetrofitManager

class WeeklyActivity : AppCompatActivity() {
    private lateinit var mbinding : ActivityWeeklyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityWeeklyBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        //mbinding?.weeklyCalendar.set
        val day = intent.getStringExtra("day")
        day?.let {
            HistoryRetrofitManager.instance.getWeekMytamin(day){responseStatus, randomCare ->

            }
        }


    }
}