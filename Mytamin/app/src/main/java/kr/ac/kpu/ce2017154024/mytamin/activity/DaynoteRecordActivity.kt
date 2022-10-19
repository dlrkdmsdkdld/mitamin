package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

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
        mbinding?.recordCompleteBtn.setOnClickListener(this)
        mbinding?.recordBackBtn?.setOnClickListener(this)

    }
    fun setEnableNextBtnPartTwo(can:Boolean){
        mbinding.recordCompleteBtn.isEnabled = can
        if (can){
            mbinding.recordCompleteBtn.background = getDrawable(R.drawable.round_layout_background_orange)
        }else{mbinding.recordCompleteBtn.background = getDrawable(R.drawable.round_layout_background_gray)}
    }
    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.recordBackBtn ->{
                onBackPressed()
            }
            mbinding?.recordCompleteBtn->{
                //navController.currentDestination?.id
                when(navController.currentDestination?.id){
                    R.id.recordFragment ->{
                        Log.d(TAG, " 현재 프래그먼트는 record프래그먼트")
                    }
                    R.id.selectRecordFragment ->{
                        Log.d(TAG, " 현재 프래그먼트는 카테고리고르는 프래그먼트")

                    }
                }
            }


        }
    }
     fun permissionDenied(text:String) {
        Toast.makeText(this, "$text", Toast.LENGTH_LONG
        ).show()
    }
}