package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityCareHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class CareHistoryActivity : AppCompatActivity() {
    private lateinit var mbinding:ActivityCareHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding=ActivityCareHistoryBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        Log.d(TAG,"CareHistoryActivity  onCreate")
//        val k = arrayOf(1)
//        //k.add(1)
//        HistoryRetrofitManager.instance.CareHistoryFilter(k)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"CareHistoryActivity  onDestroy")

    }
}