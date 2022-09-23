package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityJoinBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.joinStepOneFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.joinStepThreeFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.joinStepTwoFragment
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.joinViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel

class joinActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding : ActivityJoinBinding
    private lateinit var myjoinViewModel :joinViewModel
    private var joinStepTwoFragment:joinStepTwoFragment?=null
    private var joinStepThreeFragment:joinStepThreeFragment?=null
    private var step =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityJoinBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.join_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        myjoinViewModel= ViewModelProvider(this).get(joinViewModel::class.java)

        joinStepTwoFragment= joinStepTwoFragment()
        joinStepThreeFragment= joinStepThreeFragment()
        join_next_btn.setOnClickListener(this)



    }

    fun onFragmentChaned( index:Int) {
        if(index==0){
            joinStepTwoFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.join_fragment,
                    it
                ).commit()
            }

        }
        else if (index==1){
            joinStepThreeFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.join_fragment,
                    it
                ).commit()
            }
       
        }
        else if(index==2){

        }
        val currentProgress = join_progress.progress
        myjoinViewModel.getInterval().subscribe(){
            join_progress.progress=currentProgress + it.toInt()
        }

    }

    override fun onClick(p0: View?) {
        when(p0){
            join_next_btn ->{
                onFragmentChaned(step)
                step+=1
            }
        }
    }
}