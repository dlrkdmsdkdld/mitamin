package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityManagementBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMydayBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton

class ManagementActivity : AppCompatActivity() {
    private lateinit var mbinding: ActivityManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityManagementBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        mbinding.managementEmailText.setText(PrivateUserDataSingleton.userEmail)
        mbinding?.managementLogoutBtn.setOnClickListener {
            PreferenceUtil.clearUserData()
            val intent = Intent(this,firstActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        mbinding?.managementBackBtn.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}