package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityUserInitBinding
import kr.ac.kpu.ce2017154024.mytamin.model.initdata
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS

class UserInitActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var customProgressDialog: Dialog
    private lateinit var mbinding:ActivityUserInitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding=ActivityUserInitBinding.inflate(layoutInflater)

        setContentView(mbinding.root)

        customProgressDialog= LoadingDialog(this)
        mbinding?.initBack.setOnClickListener(this)
        mbinding?.initBtn.setOnClickListener(this)


        mbinding?.initReportBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) mbinding?.initBtn.isEnabled =true
            else{
                if (checkall()){
                }else{
                    mbinding?.initBtn.isEnabled =false
                }
            }
        }
        mbinding?.initMydayBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) mbinding?.initBtn.isEnabled =true
            else{
                if (checkall()){
                }else{
                    mbinding?.initBtn.isEnabled =false
                }
            }
        }
        mbinding?.initCareBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) mbinding?.initBtn.isEnabled =true
            else{
                if (checkall()){
                }else{
                    mbinding?.initBtn.isEnabled =false
                }
            }
        }


    }
    private fun checkall(): Boolean {
        return mbinding?.initReportBox.isChecked || mbinding?.initMydayBox.isChecked || mbinding?.initCareBox.isChecked
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.initBtn ->{
                customProgressDialog.show()
                val initdata= initdata(mbinding?.initReportBox.isChecked,mbinding?.initCareBox.isChecked,mbinding?.initMydayBox.isChecked)
                HistoryRetrofitManager.instance.deleteinitData(initdata){responseStatus ->
                    customProgressDialog.dismiss()
                    if (responseStatus==RESPONSE_STATUS.OKAY) {
                        Toast.makeText(this,"초기화완료했어요!",Toast.LENGTH_SHORT).show()
                        val i = Intent(this,MainActivity::class.java)
                        startActivity(i)
                        finishAffinity()
                    }
                    else Toast.makeText(this,"초기화실패했어요",Toast.LENGTH_SHORT).show()


                }
            }
            mbinding?.initBack ->{
                finish()
            }
        }
    }
}