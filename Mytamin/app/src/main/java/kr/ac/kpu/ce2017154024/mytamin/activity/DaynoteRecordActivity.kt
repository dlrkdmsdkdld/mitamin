package kr.ac.kpu.ce2017154024.mytamin.activity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityMainBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink

class DaynoteRecordActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding: ActivityRecordDaynoteBinding
    private lateinit var navController:NavController
    private lateinit var myRecordViewmodel: RecordViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_daynote_record)
        mbinding = ActivityRecordDaynoteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        myRecordViewmodel= ViewModelProvider(this).get(RecordViewmodel::class.java)

        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.record_container) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
         navController = navHostFragment.navController
        mbinding?.recordCompleteBtn.setOnClickListener(this)
        mbinding?.recordBackBtn?.setOnClickListener(this)

    }
    fun setEnableNextBtnPartTwo(can:Boolean){
        mbinding.recordCompleteBtn.isEnabled = can
        if (can){
            mbinding.recordCompleteBtn.background = getDrawable(R.drawable.round_layout_background_orange)
        }else{mbinding.recordCompleteBtn.background = getDrawable(R.drawable.round_layout_background_gray)}
    }
    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.recordBackBtn ->{
                onBackPressed()
            }
            mbinding?.recordCompleteBtn->{
                //navController.currentDestination?.id
                when(navController.currentDestination?.id){
                    R.id.recordFragment ->{
                        Log.d(TAG, " 현재 프래그먼트는 record프래그먼트")
                        val imageList = arrayListOf<MultipartBody.Part>()
                        myRecordViewmodel.getbitmapList.value?.forEach {
                            val bitmapRequestBody = it?.let {  BitmapRequestBody(it)}
                            val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
                            imageList.add(bitmapMultipartBody)
                        }
                        InformationRetrofitManager.instance.imageListAPI(imageList){ responseStatus, i ->
                            Log.d(TAG,"InformationRetrofitManager  i -> $i")
                        }

                    }
                    R.id.selectRecordFragment ->{
                        Log.d(TAG, " 현재 프래그먼트는 카테고리고르는 프래그먼트")

                    }
                }
            }


        }
    }
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, sink.outputStream())
        }
    }
     fun permissionDenied(text:String) {
        Toast.makeText(this, "$text", Toast.LENGTH_LONG
        ).show()
    }
}