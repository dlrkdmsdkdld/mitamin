package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView.CareParentAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityCareHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.history.BottomCategoryCareFragment
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.CareHistoryViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HistoryViewModel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel

class CareHistoryActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding:ActivityCareHistoryBinding
    private lateinit var myrecylcerview : CareParentAdapter
    private lateinit var myviewmodel : CareHistoryViewModel
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