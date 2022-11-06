package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView.CareParentAdapter
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityCareHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class CareHistoryActivity : AppCompatActivity() {
    private lateinit var mbinding:ActivityCareHistoryBinding
    private lateinit var myrecylcerview : CareParentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding=ActivityCareHistoryBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        Log.d(TAG,"CareHistoryActivity  onCreate")
        val k = ArrayList<Int>()

        HistoryRetrofitManager.instance.CareHistoryFilter(k){responseStatus, monthCareMytamin ->
            Log.d(TAG,"monthCareMytamin : $monthCareMytamin")
            monthCareMytamin?.let {
                myrecylcerview = CareParentAdapter(it)
                mbinding?.careRecycler.apply {
                    adapter = myrecylcerview
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                }


            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"CareHistoryActivity  onDestroy")

    }
}