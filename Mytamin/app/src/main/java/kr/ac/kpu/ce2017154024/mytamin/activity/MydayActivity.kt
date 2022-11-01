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
import com.google.android.material.tabs.TabLayoutMediator
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
        val viewPager = mbinding?.mydayViewpager
        val tabLayout = mbinding?.mydayTablayout
        viewPager.adapter = mydayViewPagerAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
        showSampleData(isLoading = true)
        mbinding?.mydayBackBtn.setOnClickListener(this)
        myMydayViewmodel.getDaynoteContent.observe(this, Observer {
            if (firstLoading){
                firstLoading=false
                showSampleData(isLoading = false)

            }
        })

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
                onBackPressed()
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
}