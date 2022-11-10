package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityManagementBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMydayBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton

class ManagementActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding: ActivityManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityManagementBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        mbinding.managementEmailText.setText(PrivateUserDataSingleton.userEmail)
        mbinding?.managementInitBtn.setOnClickListener(this)
        mbinding?.managementBackBtn.setOnClickListener(this)
        mbinding?.managementLogoutBtn.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.managementBackBtn ->{
                finish()
            }
            mbinding?.managementInitBtn ->{
                val i = Intent(this,UserInitActivity::class.java)
                startActivity(i)
            }
            mbinding?.managementLogoutBtn->{
                PreferenceUtil.clearUserData()
                val intent = Intent(this,firstActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}