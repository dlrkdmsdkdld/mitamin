package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.mydayViewPagerAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityJoinBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMydayBinding

class MydayActivity : AppCompatActivity() {
    private lateinit var mbinding:ActivityMydayBinding
    private val tabTitleArray = arrayOf(
        "위시리스트",
        "데이노트"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityMydayBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        val viewPager = mbinding?.mydayViewpager
        val tabLayout = mbinding?.mydayTablayout
        viewPager.adapter = mydayViewPagerAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()


    }
}