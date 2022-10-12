package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityLoginBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityProfileEditBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import java.security.Permissions
import java.util.jar.Manifest

class ProfileEditActivity : AppCompatActivity(),View.OnClickListener {

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
        mbinding?.profileEditImage.setOnClickListener(this)
       // mbinding?.profileEditImage

    }

    override fun onClick(p0: View?) {

        when(p0){
            mbinding?.profileEditImage ->{
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
//        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }){
//            openGallery()
//        } else {
//            permissionDenied(requestCode)
//        }


    }
//    private fun permissionGranted(requestCode: Int) {
//        when(requestCode){
//            android.Manifest.permission.READ_EXTERNAL_STORAGE ->{
//
//            }
//        }
//
//            PERMISSION_Album -> openGallery()
//
//    }

//    private fun permissionDenied(requestCode: Int) {
//        when (requestCode) {
//            PERMISSION_CAMERA -> Toast.makeText(
//                this,
//                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
//                Toast.LENGTH_LONG
//            ).show()
//
//            PERMISSION_Album -> Toast.makeText(
//                this,
//                "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = MediaStore.Images.Media.CONTENT_TYPE
//        startActivityForResult(intent, REQUEST_STORAGE)
//
//    }
}

