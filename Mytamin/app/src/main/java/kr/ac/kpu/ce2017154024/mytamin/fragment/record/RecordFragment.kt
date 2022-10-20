package kr.ac.kpu.ce2017154024.mytamin.fragment.record

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.record_RecyclerView.recordRecycleerViewHolder
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.record_RecyclerView.recordRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView
import kr.ac.kpu.ce2017154024.mytamin.activity.DaynoteRecordActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentRecordBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.FileDescriptor
import java.io.IOException


class RecordFragment : Fragment(),View.OnClickListener,IHomeRecyclerView {
    private var mBinding : FragmentRecordBinding?=null
    private lateinit var myRecyclerView :recordRecyclerAdapter
    private val myRecordViewmodel: RecordViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"RecordFragment onCreateView")
        mBinding?.recordCategoryLayout?.setOnClickListener(this)
        mBinding?.recordNewBtn?.setOnClickListener(this)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myRecyclerView = recordRecyclerAdapter(this)
        if (myRecordViewmodel.getbitmapList.value != null){
            Log.d(TAG ,"비트맵 어레이 크기 -> ${myRecordViewmodel.getbitmapList.value!!.size}" )
            myRecyclerView.submitBitmap(myRecordViewmodel.getbitmapList.value!!)
        }
        mBinding?.recordRecyclerImage?.adapter=myRecyclerView

    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"RecordFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.recordCategoryLayout ->{
                findNavController().navigate(R.id.action_recordFragment_to_selectRecordFragment)
            }
            mBinding?.recordNewBtn ->{
                val requestPermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(requestPermissions,101)

            }

        }
    }

    override fun onSearchItemClicked(position: Int) {
        Log.d(TAG, "posisiton -> $position")
        myRecordViewmodel.removeBitmapList(position)
        myRecyclerView.notifyDataSetChanged()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }){
            openGallery()
        } else {
            (activity as DaynoteRecordActivity).permissionDenied("저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.")
        }


    }
    fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) {
            (activity as DaynoteRecordActivity).permissionDenied("잘못된 접근입니다")
            return
        }
        when(requestCode) {
            2000 -> {
                val selectedImageURI : Uri? = data?.data
                if( selectedImageURI != null) {
                    showImage(selectedImageURI)
                }else {
                    (activity as DaynoteRecordActivity).permissionDenied("사진을 가져오지 못했습니다")
                }
            } else -> {
            (activity as DaynoteRecordActivity).permissionDenied("잘못된 접근입니다")
            }
        }

    }
    private fun showImage(uri: Uri) {
        GlobalScope.launch {    // 1
            val bitmap = getBitmapFromUri(uri) // 2
            withContext(Dispatchers.Main) {
               // myRecyclerView.addBitmap(bitmap)
                Log.d(TAG , " myRecycler view -> ${myRecyclerView.itemCount}")
                myRecordViewmodel.addbitmapList(bitmap)
                myRecyclerView.notifyDataSetChanged()
                Log.d(TAG , " myRecycler view 2-> ${myRecyclerView.itemCount}")
            }
        }
    }
    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? = activity?.contentResolver?.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()

        return image
    }
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, sink.outputStream())
        }
    }

}