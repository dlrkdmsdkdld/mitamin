package kr.ac.kpu.ce2017154024.mytamin.activity

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_care_history.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView.CareParentAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityCareHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.history.BottomCategoryCareFragment
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.careChipText
import kr.ac.kpu.ce2017154024.mytamin.viewModel.CareHistoryViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HistoryViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel

class CareHistoryActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding:ActivityCareHistoryBinding
    private lateinit var myrecylcerview : CareParentAdapter
    private lateinit var myviewmodel : CareHistoryViewModel
    private var categoryArray=ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding=ActivityCareHistoryBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        myviewmodel= ViewModelProvider(this).get(CareHistoryViewModel::class.java)
        mbinding?.careBackBtn.setOnClickListener(this)
        mbinding?.careCategoryBtn.setOnClickListener(this)
        Log.d(TAG,"CareHistoryActivity  onCreate")
        val k = ArrayList<Int>()

        myviewmodel.getmyMonthCareMytamin.observe(this, Observer {
            if (it.size == 0 ){
                care_no_layout.visibility=View.VISIBLE
            }else{
                care_no_layout.visibility=View.INVISIBLE

            }
            myrecylcerview = CareParentAdapter(it)
            mbinding?.careRecycler.apply {
                adapter = myrecylcerview
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        })
        myviewmodel.getcategory.observe(this, Observer {
            Log.d(TAG,"카테고리 변경됨 $it")
            myviewmodel.CategoryHistoryAPI()
        })




    }
    fun addCategory(ad:Int?){
        ad?.let {
            categoryArray.add(ad)
            mbinding?.careChipgroup.addView(createTagChip(this,careChipText[ad],ad))
        }
    }
    private fun createTagChip(context: Context, statetext: String,categorycode:Int): Chip {
        return Chip(context).apply {
            setChipBackgroundColorResource(R.color.background_white)
            setTextColor(resources.getColor(R.color.primary,null))
            isCloseIconVisible =true
            setCloseIconTintResource(R.color.primary)
            closeIcon = resources.getDrawable(R.drawable.ic_x_primary,null)
//            setBackgroundResource(R.drawable.ic_chip)
            isCheckedIconVisible=false
            text  =statetext

            isCheckable=false
            isClickable=true
            tag = categorycode
            chipStrokeWidth= 1F
            setChipStrokeColorResource(R.color.primary)
            setOnCloseIconClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    mbinding?.careChipgroup.removeView(this@apply)
                    categoryArray.remove(tag)
                    myviewmodel.removeCategory(tag as Int)
                    Log.d(TAG," categoryArray :$categoryArray")
                }
            })

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"CareHistoryActivity  onDestroy")

    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.careBackBtn -> {
                finish()
            }
            mbinding?.careCategoryBtn ->{
                val Bottom = BottomCategoryCareFragment()
                Bottom.show(supportFragmentManager,Bottom.tag)

            }
        }
    }
}