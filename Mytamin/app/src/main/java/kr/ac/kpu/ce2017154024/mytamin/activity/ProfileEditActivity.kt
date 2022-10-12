package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var mbinding: ActivityProfileEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding=ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        Log.d(Constant.TAG,"ProfileEditActivity onCreate")
        if (intent.hasExtra("profile_image")){
            val b = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("profile_image")
            ,0,intent.getByteArrayExtra("profile_image")!!.size)
            mbinding?.profileEditImage.setImageBitmap(b)
        }

       // mbinding?.profileEditImage

    }
}

