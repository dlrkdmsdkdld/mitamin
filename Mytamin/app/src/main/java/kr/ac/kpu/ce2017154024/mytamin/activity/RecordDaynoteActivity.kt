package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMydayBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding

class RecordDaynoteActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding: ActivityRecordDaynoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityRecordDaynoteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        mbinding?.recordBackBtn.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.recordBackBtn ->{
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}