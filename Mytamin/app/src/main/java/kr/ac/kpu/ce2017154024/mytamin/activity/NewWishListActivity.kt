package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityNewWishListBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager


///안쓰는 액티비티
class NewWishListActivity : AppCompatActivity() {
    private lateinit var mbinding: ActivityNewWishListBinding
    private lateinit var customProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityNewWishListBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        customProgressDialog= LoadingDialog(this)
        mbinding?.newWishText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().trim()!=""){
                    mbinding?.newWishCompleteBtn.visibility=View.VISIBLE
                }else{
                    mbinding?.newWishCompleteBtn.visibility=View.INVISIBLE

                }
            }

        })

        mbinding?.newWishCompleteBtn.setOnClickListener {
            customProgressDialog.show()
            InformationRetrofitManager.instance.sendNewWishlist(mbinding?.newWishText.text.toString().trim()){
                responseStatus, wishList ->
                customProgressDialog.dismiss()
                val intent = Intent(this,MydayActivity::class.java)
                intent.putExtra("wishList",wishList)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }


    }
}