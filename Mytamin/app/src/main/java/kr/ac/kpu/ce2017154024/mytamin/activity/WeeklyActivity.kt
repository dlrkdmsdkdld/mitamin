package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityWeeklyBinding

class WeeklyActivity : AppCompatActivity() {
    private lateinit var mbinding : ActivityWeeklyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityWeeklyBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
    }
}