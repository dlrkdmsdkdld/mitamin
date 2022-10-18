package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMydayBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding

class RecordDaynoteActivity : AppCompatActivity() {
    private lateinit var mbinding: ActivityRecordDaynoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityRecordDaynoteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)


    }
}