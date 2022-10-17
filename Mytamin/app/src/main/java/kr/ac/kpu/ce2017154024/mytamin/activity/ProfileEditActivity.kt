package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.information.BottomProfileEditFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.todaymytamin.MyatminCategoryFragment
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import kr.ac.kpu.ce2017154024.mytamin.utils.choice
import kr.ac.kpu.ce2017154024.mytamin.utils.fragment
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


class ProfileEditActivity : AppCompatActivity(),View.OnClickListener {
    private var correctionImage:Boolean=false
    private lateinit var mbinding: ActivityProfileEditBinding
    private var fileToUpload = null
    private lateinit var nickname:String
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

        }
        Log.d(Constant.TAG,"ProfileEditActivity nickname ->$nickname")
        mbinding?.profileEditImage.setOnClickListener(this)
        mbinding?.profileEditBackbtn.setOnClickListener(this)
        mbinding?.profileEditCompletebtn.setOnClickListener(this)
       // mbinding?.profileEditImage
        customProgressDialog= LoadingDialog(this)

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
                if (mbinding?.profileEditTobeText.text.toString() !=""){
                    completeBtn(correctionImage,true,false)
                }else{
                    completeBtn(correctionImage,true,true)
                }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
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
    fun completeBtn(image:Boolean,nickname:Boolean, tobe:Boolean){
        var isImageComplete = !image
        var isnicknameComplete = nickname
        var istobeComplete = tobe
        if (correctionImage){
            val bitmap :Bitmap = mbinding?.profileEditImage.drawable.toBitmap()
            val bitmapRequestBody = bitmap?.let {  BitmapRequestBody(it)}
            val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
            InformationRetrofitManager.instance.oneImageAPICall(bitmapMultipartBody, completion = {responseStatus, i ->
                isImageComplete =true
                if (istobeComplete &&isnicknameComplete &&isImageComplete){
                    startMainActivity()
                }
            })
        }else{

        }
        if (!istobeComplete){
            InformationRetrofitManager.instance.CorrectionBeMyMessage(mbinding?.profileEditTobeText.text.toString()) { RESPONSE_STATUS, it ->
                istobeComplete =true
                if (istobeComplete &&isnicknameComplete &&isImageComplete){
                    startMainActivity()
                }
            }

        }
        if (istobeComplete &&isnicknameComplete &&isImageComplete)startMainActivity()



    }
    fun tobeAPICall(tobe:String){
        InformationRetrofitManager.instance.CorrectionBeMyMessage(tobe) { RESPONSE_STATUS, it ->

        }
    }
    fun startMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("fragment",fragment.information)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }



}

