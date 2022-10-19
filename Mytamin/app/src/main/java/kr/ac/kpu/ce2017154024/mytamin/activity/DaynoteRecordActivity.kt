package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding

class DaynoteRecordActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding: ActivityRecordDaynoteBinding
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_daynote_record)
        mbinding = ActivityRecordDaynoteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.record_container) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
         navController = navHostFragment.navController
        mbinding?.recordBackBtn?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.recordBackBtn ->{
                onBackPressed()
            }
        }
    }
}