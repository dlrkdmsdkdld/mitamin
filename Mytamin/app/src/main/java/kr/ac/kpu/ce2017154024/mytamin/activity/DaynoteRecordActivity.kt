package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding

class DaynoteRecordActivity : AppCompatActivity() {
    private lateinit var mbinding: ActivityRecordDaynoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_daynote_record)
        mbinding = ActivityRecordDaynoteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.record_container) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
        val navController = navHostFragment.navController

    }
}