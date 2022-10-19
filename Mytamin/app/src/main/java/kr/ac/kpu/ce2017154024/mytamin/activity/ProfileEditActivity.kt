package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_today_mytamin.*
import kotlinx.android.synthetic.main.fragment_join_step_three.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.information.BottomProfileEditFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.MyatminCategoryFragment
import kr.ac.kpu.ce2017154024.mytamin.model.EditProfile
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.util.concurrent.TimeUnit


class ProfileEditActivity : AppCompatActivity(),View.OnClickListener {
    private var correctionImage:Boolean=false
    private lateinit var mbinding: ActivityProfileEditBinding
    private var fileToUpload = null
    private lateinit var nickname:String
    private lateinit var tobemessage:String
    private lateinit var customProgressDialog: Dialog
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
        if (intent.hasExtra("nickname")){
            nickname = intent.getStringExtra("nickname").toString()
            mbinding?.profileEditNicknameText.setText("$nickname")
        }
        if (intent.hasExtra("tobemessage")){
            tobemessage = intent.getStringExtra("tobemessage").toString()
            mbinding?.profileEditTobeText.setText("$tobemessage")
        }
        Log.d(Constant.TAG,"ProfileEditActivity nickname ->$nickname")
        mbinding?.profileEditImage.setOnClickListener(this)
        mbinding?.profileEditBackbtn.setOnClickListener(this)
        mbinding?.profileEditCompletebtn.setOnClickListener(this)
       // mbinding?.profileEditImage
        customProgressDialog= LoadingDialog(this)

        val edittextChangeObservable = mbinding.profileEditNicknameText.textChanges()

        val checkNameTextSubscription : Disposable =
            edittextChangeObservable!!.debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext ={
                        Handler(Looper.getMainLooper()).post(Runnable {
                            if (it.toString()!=""){
                                setEnableCompleteBtn(false)
                                mbinding?.profileEditNicknameLayout?.endIconDrawable=resources.getDrawable(R.drawable.ic_baseline_replay_24)
                                mbinding?.profileEditNicknameLayout?.helperText = JOINSTRING.searchingEmail
                                CheckNameAPICall(it.toString())
                            }
                        })

                    }
                )



    }
    fun setEnableCompleteBtn(can:Boolean){
        mbinding?.profileEditCompletebtn.isEnabled=can
        if (can){
            mbinding?.profileEditCompletebtn.background = getDrawable(R.drawable.round_layout_background_orange)
        }else{
            mbinding?.profileEditCompletebtn.background = getDrawable(R.drawable.ic_large_button_disabled)
        }

    }
    private fun CheckNameAPICall(query:String):Boolean?{
        var result: Boolean = true
        JoinRetrofitManager.instance.checkName(inputemail = query, completion ={ responseStatus, checkOverlapData ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY -> {
                    Log.d(Constant.TAG,"api 호출 성공 check  = $checkOverlapData")
                    if (checkOverlapData?.status==200){
                        result = checkOverlapData.result
                        if (!result || nickname == query){//"@drawable/ic_baseline_check_24"
                            mbinding?.profileEditNicknameLayout?.endIconDrawable= resources.getDrawable(R.drawable.ic_baseline_check_24)
                            mbinding?.profileEditNicknameLayout?.helperText=JOINSTRING.goodNickname
                            setEnableCompleteBtn(true)
                        }else{
                            mbinding?.profileEditNicknameLayout?.endIconDrawable= resources.getDrawable(R.drawable.ic_baseline_error_24)
                            mbinding?.profileEditNicknameLayout?.helperText=JOINSTRING.wrongNickNameoverlap
                            setEnableCompleteBtn(false)
                        }
                    }
                }
            }
        } )
        return result
    }

    override fun onClick(p0: View?) {

        when(p0){
            mbinding?.profileEditImage ->{
                val bottomProfileEditFragment= BottomProfileEditFragment()
                bottomProfileEditFragment.show(supportFragmentManager,bottomProfileEditFragment.tag)


            }
            mbinding?.profileEditBackbtn ->{
                onBackPressed()
            }
            mbinding?.profileEditCompletebtn->{
                completeBtn()
            }

        }
    }
    fun choiceOption(select: choice){
        correctionImage=true
        when(select){
            choice.basic ->{
                Log.d(TAG,"기본선택")
                mbinding?.profileEditImage.setImageResource(R.drawable.cat)
            }
            choice.gallery ->{
                Log.d(TAG,"갤러리선택")
                val requestPermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(requestPermissions,101)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }){
            openGallery()
        } else {
            permissionDenied(requestCode)
        }


    }

    private fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.", Toast.LENGTH_LONG
        ).show()
    }
//
    fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = MediaStore.Images.Media.CONTENT_TYPE
//        startActivityForResult(intent, 2000)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) {
            Toast.makeText(this,"잘못된 접근입니다",Toast.LENGTH_SHORT).show()
            return
        }
        when(requestCode) {
            2000 -> {
                val selectedImageURI : Uri? = data?.data
                if( selectedImageURI != null) {
                    showImage(selectedImageURI)
                  //  mbinding?.profileEditImage?.setImageURI(selectedImageURI)
//                    val file = File(getFullPathFromUri(this,selectedImageURI))
//                    val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
//                    val fileToUpload = MultipartBody.Part.createFormData("file",file.name,requestBody)
//                    InformationRetrofitManager.instance.oneImageAPICall(fileToUpload)
                }else {
                    Toast.makeText(this,"사진을 가져오지 못했습니다",Toast.LENGTH_SHORT).show()
                }
            } else -> {
            Toast.makeText(this,"잘못된 접근입니다",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun showImage(uri: Uri) {
        GlobalScope.launch {    // 1
            val bitmap = getBitmapFromUri(uri) // 2
            withContext(Dispatchers.Main) {
                mbinding?.profileEditImage?.setImageBitmap(bitmap)    // 3
            }
        }
    }
    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        //선택한 데이터 서버전송
//        val bitmapRequestBody = image?.let {  BitmapRequestBody(it)}
//        val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
//        InformationRetrofitManager.instance.oneImageAPICall(bitmapMultipartBody)
//////////////////////////////////


        return image
    }
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, sink.outputStream())
        }
    }
    fun completeBtn(){
        val tmpNickname = mbinding?.profileEditNicknameText.text.toString()
        customProgressDialog.show()
        val bitmap :Bitmap = mbinding?.profileEditImage.drawable.toBitmap()
        val bitmapRequestBody = bitmap?.let {  BitmapRequestBody(it)}
        val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
        Log.d(TAG,"correctionImage -> $correctionImage")
        val editTobe=mbinding?.profileEditTobeText.text.toString()
        val editname=mbinding?.profileEditNicknameText.text.toString()
        if (correctionImage){
            val result = EditProfile(isImgEdited = "T" ,nickname = editname, beMyMessage = editTobe )
            InformationRetrofitManager.instance.editProfile(file = bitmapMultipartBody,result){responseStatus, i ->
                startMainActivity()
                Log.d(TAG, "statuscode - > $i ")
            }
        }else{
            val result = EditProfile(isImgEdited = "F" ,nickname = editname, beMyMessage = editTobe )
            InformationRetrofitManager.instance.editProfile(file = null,result){responseStatus, i ->
                startMainActivity()
            }

        }

    }
    fun tobeAPICall(tobe:String){
        InformationRetrofitManager.instance.CorrectionBeMyMessage(tobe) { RESPONSE_STATUS, it ->

        }
    }
    fun startMainActivity(){
        customProgressDialog.dismiss()
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("fragment",fragment.information)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }



}

