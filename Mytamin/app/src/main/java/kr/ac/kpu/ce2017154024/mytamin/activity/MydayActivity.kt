package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.ac.kpu.ce2017154024.mytamin.MytaminWorker
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.mydayViewPagerAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityJoinBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMydayBinding
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.InformationViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.todayMytaminViewModel
import java.util.*

class MydayActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mbinding:ActivityMydayBinding
    private lateinit var myMydayViewmodel: MydayViewmodel
    private lateinit var newWish:WishList
    private var firstLoading=true
    private val tabTitleArray = arrayOf(
        "데이노트","위시리스트"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityMydayBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        myMydayViewmodel= ViewModelProvider(this).get(MydayViewmodel::class.java)
        Log.d(TAG," MydayActivity onCreate ")
//        val viewPager = mbinding?.mydayViewpager
        val tabLayout = mbinding?.mydayTablayout
        ///뷰페이저 + 탭레이아웃
//        viewPager.adapter = mydayViewPagerAdapter(supportFragmentManager,lifecycle)
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = tabTitleArray[position]
//        }.attach()
        ///여기까지
        //////탭레이아웃 + 프래그먼트 컨테이너 이용코드 - 뷰페이저사용시 짤려서
        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.myday_viewpager) as NavHostFragment
        //네비게이션 컨트롤러 가져옴

        val navController = navHostFragment.navController
        tabLayout.addTab(tabLayout.newTab().setText("데이노트"))
        tabLayout.addTab(tabLayout.newTab().setText("위시리스트"))
        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->navController.navigate(R.id.daynoteFragment)
                    1->navController.navigate(R.id.wishlistFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        /////////여기까지 프래그먼트사용코드
        val k = true
        showSampleData(isLoading = true)
        mbinding?.mydayBackBtn.setOnClickListener(this)
        mbinding?.mydayRefreshBtn.setOnClickListener(this)
        myMydayViewmodel.getDaynoteContent.observe(this, Observer {
            if (firstLoading){
                firstLoading=false
                showSampleData(isLoading = false)

            }
        })


        WorkManager.getInstance(applicationContext).getWorkInfosByTagLiveData("newdaynote")
            .observe(this, Observer {
                var first=true
                it.forEach {
                    val myResult = it.outputData.getBoolean("result", false)
                    if (it.state == WorkInfo.State.SUCCEEDED && myResult&& first) {
                        //문제가 이게 존나게 많이 호출되네..
                        first=false
                        Log.d(TAG, "it.id -> ${it.id}")
                        Log.d(TAG, " it.outputData.keyValueMap   ${it.outputData.keyValueMap}")
                        myMydayViewmodel.getWishlistAPI()
                        myMydayViewmodel.getdaynoteAPI()
                    }

                }
            })
//        mbinding?.mydaySwipelayout.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
//            override fun onRefresh() {
//            }
//
//        })


    }
    companion object {
        const val SUB_ACTIVITY_CODE = 1002
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModelStore.clear()
        myMydayViewmodel= ViewModelProvider(this).get(MydayViewmodel::class.java)
        Log.d(TAG,"onActivityResult onActivityResult requestCode: $requestCode resultCode:$resultCode ")
        Log.d(TAG,"onActivityResult onActivityResult SUB_ACTIVITY_CODE: $SUB_ACTIVITY_CODE RESULT_OK:$RESULT_OK ")
        if (requestCode == SUB_ACTIVITY_CODE && resultCode == RESULT_OK) {

        }
    }
    private fun showSampleData(isLoading: Boolean) {
        if (isLoading) {
            mbinding?.daynoteShimmerLayout?.startShimmer()
            mbinding?.daynoteShimmerLayout?.visibility = View.VISIBLE
            mbinding?.daynoteMainLayout?.visibility = View.GONE
        } else {
            mbinding?.daynoteShimmerLayout?.stopShimmer()
            mbinding?.daynoteShimmerLayout?.visibility = View.GONE
            mbinding?.daynoteMainLayout?.visibility = View.VISIBLE
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.mydayBackBtn ->{
                finish()
            }
            mbinding?.mydayRefreshBtn ->{
                myMydayViewmodel.getWishlistAPI()
                myMydayViewmodel.getdaynoteAPI()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG," MydayActivity onDestroy ")
    }

    override fun onRestart() {
        super.onRestart()
        myMydayViewmodel.getdaynoteAPI()
        myMydayViewmodel.getWishlistAPI()
    }
}