package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityFindPasswordBinding
import kr.ac.kpu.ce2017154024.mytamin.model.ChangePassword
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.findPasswordViewmodel

class FindPasswordActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var customProgressDialog: Dialog
    private lateinit var mBinding:ActivityFindPasswordBinding
    private lateinit var navController: NavController
    private lateinit var myRecordViewmodel: findPasswordViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.find_container) as NavHostFragment
        customProgressDialog= LoadingDialog(this)
        myRecordViewmodel= ViewModelProvider(this).get(findPasswordViewmodel::class.java)

        //네비게이션 컨트롤러 가져옴
        navController = navHostFragment.navController
        mBinding?.findCompleteBtn.setOnClickListener(this)
        mBinding?.findBackBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.findCompleteBtn ->{
                next()
            }

            mBinding?.findBackBtn ->{
                onBackPressed()
            }
        }
    }
    fun enableComplete(isenable:Boolean){
        mBinding?.findCompleteBtn.isEnabled = isenable
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
                PasswordAPI()
            }
            else ->{

                finish()
            }
        }

    }
    private fun PasswordAPI(){
        val tmp = myRecordViewmodel.getpassword.value?.let { myRecordViewmodel.getemail.value?.let { it1 ->
            ChangePassword(it,
                it1
            )
        } }
        tmp?.let {
            JoinRetrofitManager.instance.changePassword(tmp){
                if (it == RESPONSE_STATUS.OKAY)Toast.makeText(this,"비밀번호 재설정 완료했어요",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun showLoading(bool :Boolean){
        if (bool) customProgressDialog.show()
        else customProgressDialog.dismiss()
    }
}