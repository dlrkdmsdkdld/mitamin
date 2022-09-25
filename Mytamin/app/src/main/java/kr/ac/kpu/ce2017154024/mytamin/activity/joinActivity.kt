package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_join.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityJoinBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.join.joinStepThreeFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.join.joinStepTwoFragment
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.joinViewModel

class joinActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding : ActivityJoinBinding
    private lateinit var myjoinViewModel :joinViewModel
    private var joinStepTwoFragment: joinStepTwoFragment?=null
    private var joinStepThreeFragment: joinStepThreeFragment?=null
    private var step =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityJoinBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG,"joinActivity onCreate")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.join_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        myjoinViewModel= ViewModelProvider(this).get(joinViewModel::class.java)

        joinStepTwoFragment= joinStepTwoFragment()
        joinStepThreeFragment= joinStepThreeFragment()
        join_next_btn.setOnClickListener(this)
        join_next_btn.isEnabled=false



    }
    fun canEnableNextbtn(bool:Boolean){
        join_next_btn.isEnabled=bool

    }

    fun onFragmentChaned( index:Int) {
        val fragmentManager = supportFragmentManager
        if(index==0){

            joinStepTwoFragment?.let {
                fragmentManager.beginTransaction().replace(R.id.join_fragment,
                    it
                ).commit()
            }
             join_next_btn.isEnabled=false

        }
        else if (index==1){
            joinStepThreeFragment?.let {
                fragmentManager.beginTransaction().replace(R.id.join_fragment,
                    it
                ).commit()
            }
            join_next_btn.isEnabled=false

        }
        else if(index==2){
            join_next_btn.isEnabled=false

        }
        val currentProgress = join_progress.progress
        myjoinViewModel.getInterval().subscribe(){
            join_progress.progress=currentProgress + it.toInt()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constant.TAG,"joinActivity onDestroy")
    }

    override fun onClick(p0: View?) {
        when(p0){
            join_next_btn ->{
                onFragmentChaned(step)
                step+=1
                if (step==3){
                    val intent = Intent(this,IntroduceActivity::class.java)
                    val email =myjoinViewModel.getemail.value
                    val password = myjoinViewModel.getpassword.value
                    val nickname = myjoinViewModel.getname.value
                    Log.d(TAG, "JoinActivity ->IntroduceActivity email: $email password:$password  nickname:$nickname")
                    intent.putExtra("email",email)
                    intent.putExtra("password",password)
                    intent.putExtra("nickname",nickname)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}