package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityFindPasswordBinding

class FindPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var customProgressDialog: Dialog
    private lateinit var mBinding:ActivityFindPasswordBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.find_container) as NavHostFragment
        customProgressDialog= LoadingDialog(this)

        //네비게이션 컨트롤러 가져옴
        navController = navHostFragment.navController
        mBinding?.findCompleteBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.findCompleteBtn ->{
                next()
            }
            mBinding?.findBackBtn ->{
                finish()
            }
        }
    }
    fun enableComplete(isenable:Boolean){
        if (isenable)mBinding?.findCompleteBtn.isEnabled = true
        else mBinding?.findCompleteBtn.isEnabled = true
    }
    private fun next(){
        when(navController.currentDestination?.id){
            R.id.findOneFragment ->{
                navController.navigate(R.id.findTwoFragment)
            }
            R.id.findTwoFragment ->{
                navController.navigate(R.id.findThreeFragment)
                mBinding?.findCompleteBtn.text = "로그인하러 가기"
                mBinding?.findTopLayout.visibility=View.GONE
            }
            else ->{

                finish()
            }
        }

    }
    fun showLoading(bool :Boolean){
        if (bool) customProgressDialog.show()
        else customProgressDialog.dismiss()
    }
}