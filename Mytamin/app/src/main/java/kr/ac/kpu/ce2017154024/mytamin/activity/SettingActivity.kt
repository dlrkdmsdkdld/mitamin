package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var mbinding :ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
    }
}