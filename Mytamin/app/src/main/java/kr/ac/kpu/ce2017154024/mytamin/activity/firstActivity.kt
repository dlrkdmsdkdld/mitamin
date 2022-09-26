package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_first.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityFirstBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil

class firstActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Log.d(TAG,"firstActivity onCreate")
        first_join_btn.setOnClickListener(this)
        first_login_btn.setOnClickListener(this)
       // PreferenceUtil.clearUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"firstActivity onDestroy")
    }

    override fun onClick(p0: View?) {
        when(p0){
            first_join_btn ->{
                val intent = Intent(applicationContext,joinActivity::class.java)
                startActivity(intent)
                finish()
            }
            first_login_btn ->{
                val intent = Intent(applicationContext,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}