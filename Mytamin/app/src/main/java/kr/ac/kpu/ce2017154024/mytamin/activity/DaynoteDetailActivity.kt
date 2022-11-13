package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.DetailImageAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityDaynoteDetailBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.information.BottomProfileEditFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.myday.BottomModifyFragment
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.choice
import kr.ac.kpu.ce2017154024.mytamin.utils.select

class DaynoteDetailActivity : AppCompatActivity() ,View.OnClickListener{
    private lateinit var daynotedata : daynoteData
    private lateinit var mbinding : ActivityDaynoteDetailBinding
    private var wishbundle:Bundle? =null
    private var daynotebundle:Bundle?=null
    private lateinit var customProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"DaynoteDetailActivity onCreate")
        super.onCreate(savedInstanceState)
        mbinding = ActivityDaynoteDetailBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        if (intent.hasExtra("bundle_data")){
            daynotebundle=intent.getBundleExtra("bundle_data")
            this.daynotedata  = daynotebundle?.getSerializable("data") as daynoteData

        }
        if (intent.hasExtra("array_bundle")){
            this.wishbundle=intent.getBundleExtra("array_bundle")

        }



        setSlideAdapter(daynotedata.imgList)
        Log.d(TAG,"daynotedata : $daynotedata")
        mbinding?.detailTitleText?.text = daynotedata.wishText
        mbinding?.detailSubText?.text = daynotedata.note
        mbinding?.detailTimeText?.text = "${daynotedata.year}. ${daynotedata.month}"
        mbinding?.detailBackBtn.setOnClickListener(this)
        mbinding?.detailMoreBtn.setOnClickListener(this)
        customProgressDialog = LoadingDialog(this)

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

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.detailBackBtn ->{
                onBackPressed()
            }
            mbinding?.detailMoreBtn ->{
                val bottomModifyFragment= BottomModifyFragment()
                bottomModifyFragment.show(supportFragmentManager,bottomModifyFragment.tag)

            }

        }
    }

    fun choiceOption(selectd: select){
        when(selectd){
            select.modify ->{
                Log.d(TAG,"수정선택")
                val intent = Intent(this@DaynoteDetailActivity,DaynoteRecordActivity::class.java)
                if (wishbundle!=null) intent.putExtra("array_bundle",wishbundle)
                intent.putExtra("daynotebundle",daynotebundle)
                startActivity(intent)
                finish()

            }
            select.delete ->{
                customProgressDialog.show()
                Log.d(TAG,"삭제선택")
                InformationRetrofitManager.instance.daynoteDelete(daynotedata.daynoteId){responseStatus ->
                    customProgressDialog.dismiss()

                }
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"DaynoteDetailActivity onDestroy")
    }
}