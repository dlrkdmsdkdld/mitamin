package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.work.*
import kr.ac.kpu.ce2017154024.mytamin.MytaminWorker
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityRecordDaynoteBinding
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.BitmapRequestBody
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.DAYNOTEDATE
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.NOTE
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.WISHTEXT
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToMonth
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel
import okhttp3.MultipartBody

class DaynoteRecordActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding: ActivityRecordDaynoteBinding
    private lateinit var navController:NavController
    // private val myRecordViewmodel: RecordViewmodel by viewModels { RecordViewModelFactory(application) }
     private lateinit var myRecordViewmodel: RecordViewmodel
     enum class RecordType{
         basic,
         modify
     }

    private lateinit var customProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_daynote_record)
        mbinding = ActivityRecordDaynoteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        myRecordViewmodel= ViewModelProvider(this).get(RecordViewmodel::class.java)
        customProgressDialog= LoadingDialog(this)

        //네비게이션들을 담는 호스트
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.record_container) as NavHostFragment

        //네비게이션 컨트롤러 가져옴
         navController = navHostFragment.navController
        mbinding?.recordCompleteBtn.setOnClickListener(this)
        mbinding?.recordBackBtn?.setOnClickListener(this)
        if (intent.hasExtra("array_bundle")){
            val bundle=intent.getBundleExtra("array_bundle")
            val wishList  = bundle?.getSerializable("wishlistArray") as Array<WishList>
            myRecordViewmodel.setWishList(wishList)

        }
        if (intent.hasExtra("daynotebundle")){
            val bundle=intent.getBundleExtra("daynotebundle")
            val daynotebundle = bundle?.getSerializable("data") as daynoteData
            daynotebundle?.let {myRecordViewmodel.setmodifyDaynote(daynotebundle) }
            myRecordViewmodel.recordtype = RecordType.modify
        }

    }
    fun setEnableNextBtnPart(can:Boolean){
        mbinding.recordCompleteBtn.isEnabled = can

    }
    fun selectwishList(can:Boolean){
        if(can){
            mbinding?.recordTitleText.setText("완료한 위시리스트")
        }
        else{
            mbinding?.recordTitleText.setText("기록 남기기")
        }
    }
    fun basicWorker(){
        var stringURI =ArrayList<String>()
        myRecordViewmodel.getUrlList.value?.forEach {
            stringURI.add(it)
        }
        Log.d(TAG, " stringURI stringURI $stringURI")
        if (stringURI!=null) {
            val inputData= Data.Builder().putStringArray(MytaminWorker.EXTRA_URI_ARRAY,
                stringURI.toArray(arrayOfNulls<String>(stringURI.size))).build()
            NOTE= myRecordViewmodel.getnote.value.toString()
            myRecordViewmodel.getwishId.value?.let {
                WISHTEXT = myRecordViewmodel.getwishId.value!!
                DAYNOTEDATE ="${myRecordViewmodel.getyear.value}.${myRecordViewmodel.getmonth.value.parseIntToMonth()}"
                Log.d(TAG, "DAYNOTEDATE : $DAYNOTEDATE ")
                //MydayActivity().

                val uploadWorkRequest:OneTimeWorkRequest =
                    OneTimeWorkRequestBuilder<MytaminWorker>()
                        .setInputData(inputData)
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .addTag("newdaynote")
                        .build()

                WorkManager.getInstance(applicationContext).enqueue(uploadWorkRequest)
                   // .enqueue(uploadWorkRequest)


                finish()
            }

        }
    }
    fun modifyWorker(){
        customProgressDialog.show()
        val imageList  = ArrayList<MultipartBody.Part>()
        myRecordViewmodel.getbitmapList.value?.forEach {
            val bitmapRequestBody = it?.let {  BitmapRequestBody(it) }
            val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("fileList", "file.jpeg", bitmapRequestBody)
            imageList.add(bitmapMultipartBody)
        }
        InformationRetrofitManager.instance.modifynote(fileList = imageList, wishid = myRecordViewmodel.getwishId.value!!, note = myRecordViewmodel.getnote.value!!, noteId = myRecordViewmodel.getmodifyDaynote.value!!.daynoteId
        ){responseStatus, i ->
            customProgressDialog.dismiss()
            if (i==200){
                //성공
            }
            finish()
        }
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
                            when(myRecordViewmodel.recordtype){
                                RecordType.basic->{
                                    basicWorker()
                                }
                                RecordType.modify->{
                                    modifyWorker()
                                }
                            }
                        }


//                        myRecordViewmodel.getbitmapList.value?.forEach {
//                            val bitmapRequestBody = it?.let {  BitmapRequestBody(it) }
//                            val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
//                            imageList.add(bitmapMultipartBody)
//                        }
//                        InformationRetrofitManager.instance.imageListAPI(imageList){ responseStatus, i ->
//                            Log.d(TAG,"InformationRetrofitManager  i -> $i")
//                        }


                    R.id.selectRecordFragment ->{
                        Log.d(TAG, " 현재 프래그먼트는 카테고리고르는 프래그먼트")
                        onBackPressed()
//                        navController.navigate(R.id.action_selectRecordFragment_to_recordFragment)
//                        navController.popBackStack()

                    }
                }
            }


        }
    }

     fun permissionDenied(text:String) {
        Toast.makeText(this, "$text", Toast.LENGTH_LONG
        ).show()
    }
}