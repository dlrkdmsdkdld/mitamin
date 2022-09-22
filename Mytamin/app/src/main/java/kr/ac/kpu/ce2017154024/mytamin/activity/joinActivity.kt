package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityJoinBinding

class joinActivity : AppCompatActivity() {
    private lateinit var mbinding : ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityJoinBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
//        mbinding.joinNextBtn.setOnClickListener{
//            view : View ->
//            view.findNavController().navigate(R.id.)
//        }

    }
}