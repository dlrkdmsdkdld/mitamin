package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.HomeFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.UserInformationFragment
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.fragment

class MainActivity : AppCompatActivity(){
    private lateinit var mbinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG,"MainActivity onCreate")


        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.myNavHost) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
        val navController = navHostFragment.navController
        //바텀네비게이션뷰와 네비게이션을 묶어준다
        NavigationUI.setupWithNavController(mbinding.myBottomNav , navController)
       // mbinding.myBottomNav.setOnItemReselectedListener(this)
        if (intent.hasExtra("fragment")){
            val fragment :fragment= intent.getSerializableExtra("fragment") as fragment
            fragmentReplace(fragment,navController)
        }
    }
    fun fragmentReplace(data:fragment,navController: NavController){
        when(data){
            fragment.information ->{
                navController.popBackStack()
                navController.navigate(R.id.userInformationFragment)


            }
            fragment.home ->{
                navController.popBackStack()
                navController.navigate(R.id.homeFragment)

            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constant.TAG,"MainActivity onDestroy")
    }
}


