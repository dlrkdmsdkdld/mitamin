package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant

class LoginActivity : AppCompatActivity() {
    private lateinit var mbinding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG,"LoginActivity onCreate")
    }
}