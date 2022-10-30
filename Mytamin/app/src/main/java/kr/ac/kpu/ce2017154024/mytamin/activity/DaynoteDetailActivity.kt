package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.DetailImageAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityDaynoteDetailBinding
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class DaynoteDetailActivity : AppCompatActivity() {
    private lateinit var daynotedata : daynoteData
    private lateinit var mbinding : ActivityDaynoteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"DaynoteDetailActivity onCreate")
        super.onCreate(savedInstanceState)
        mbinding = ActivityDaynoteDetailBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        if (intent.hasExtra("bundle_data")){
            val bundle=intent.getBundleExtra("bundle_data")
            this.daynotedata  = bundle?.getSerializable("data") as daynoteData

        }
        setSlideAdapter(daynotedata.imgList)
        Log.d(TAG,"daynotedata : $daynotedata")
    }
    private fun setSlideAdapter(images:ArrayList<String>){
        val pagerCallback = object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
             //   indicator.setSelected(position) // ViewPager 의 position 값이 변경된 경우 Indicator Position 변경
              //  mbinding?.detailIndicator.set
            }
        }
        val adapterD = DetailImageAdapter(this,images)
        mbinding?.detailViewPager.apply {
            adapter = adapterD
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(pagerCallback)
        }
        mbinding?.detailIndicator.attachTo(mbinding?.detailViewPager)




    }
}