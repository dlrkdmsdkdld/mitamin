package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_introduce.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.ViewPager2.IntroduceViewPagerFragmentAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityIntroduceBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING

class IntroduceActivity : AppCompatActivity() {
    private lateinit var mbinding: ActivityIntroduceBinding
    private val NUM_PAGES = 4
    private var inputHour = 0
    private var inputMinute = 0
     //  viewpager
    private lateinit var viewPager:ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityIntroduceBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG,"IntroduceActivity onCreate")
        //mbinding?.introduceViewpager.adapter = IntroduceActivity@adapter!!
        val viewPager:ViewPager2 = mbinding?.introduceViewpager
        val viewpagerFramgnetAdapter = IntroduceViewPagerFragmentAdapter(this)
        viewPager.adapter=viewpagerFramgnetAdapter
        viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position==3){
                    mbinding?.introduceNextBtn.text=JOINSTRING.start
                }else{
                    mbinding?.introduceNextBtn.text=JOINSTRING.next
                }
            }
        })
        val dotsIndicator=mbinding?.introduceIndicator
        dotsIndicator.attachTo(viewPager)
        mbinding?.introduceNextBtn.setOnClickListener {
            if (viewPager.currentItem!=3){
                viewPager.currentItem=viewPager.currentItem+1
            }else{
                mbinding?.introduceNextBtn.text=JOINSTRING.start
            }

        }



    }
    fun submitTime(hour:Int,minute:Int){
        inputHour=hour
        inputMinute= minute
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constant.TAG,"IntroduceActivity onDestroy")
    }
}