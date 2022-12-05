package kr.ac.kpu.ce2017154024.mytamin.fragment.record

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
import kr.ac.kpu.ce2017154024.mytamin.fragment.information.BottomProfileEditFragment
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.chipStringdata
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
    private var imageUrlArray = ArrayList<Uri>()
    private var first : Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"RecordFragment onCreateView")
        mBinding?.recordCategoryLayout?.setOnClickListener(this)
        mBinding?.recordNewBtn?.setOnClickListener(this)
        if(myRecordViewmodel.recordtype == DaynoteRecordActivity.RecordType.basic) mBinding?.recordTimeLayout?.setOnClickListener(this)
        (activity as DaynoteRecordActivity).selectwishList(false)
        myRecordViewmodel.getyear.observe(viewLifecycleOwner, Observer {
            mBinding?.recordTimeText?.setText("${myRecordViewmodel.getyear.value}년 ${myRecordViewmodel.getmonth.value}월의 마이데이")
        })
        myRecordViewmodel.getmonth.observe(viewLifecycleOwner, Observer {
            mBinding?.recordTimeText?.setText("${myRecordViewmodel.getyear.value}년 ${myRecordViewmodel.getmonth.value}월의 마이데이")
            myRecordViewmodel.checkAPI()
        })
        myRecordViewmodel.getcategoryText.observe(viewLifecycleOwner, Observer {
            mBinding?.recordCategoryText?.setText(it)
            myRecordViewmodel.getnote.value?.let { mBinding?.recordCommentText?.setText(it) }
            (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())
        })
        //날짜 가능한지 체크
        myRecordViewmodel.getok.observe(viewLifecycleOwner, Observer {
            (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())
        })

        //수정했을때만 실행됨
        myRecordViewmodel.getbitmapList.observe(viewLifecycleOwner, Observer {
            (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())
        })

        mBinding?.recordCommentText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                    myRecordViewmodel.setnote(p0.toString())
                    (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())

            }


        })



        return mBinding?.root
    }


    private fun allOk():Boolean{
        var ok=false
        if (myRecordViewmodel.recordtype == DaynoteRecordActivity.RecordType.basic){
            ok= basicTypeAllOk()
        }else{
            ok= modifyTypeAllOK()
        }
        return ok
    }
    private fun modifyTypeAllOK():Boolean{
        var addimageOk =false
        Log.d(TAG,"myRecyclerView.itemCount -> ${myRecyclerView.itemCount}")
        //수정일때는 url을 안없애서 그냥 리싸이클러뷰에서 카운트함
        myRecyclerView?.let {addimageOk= myRecyclerView.itemCount!=0 }
        var imageOk=(myRecordViewmodel.getbitmapList.value?.isNotEmpty() == true || addimageOk)

        Log.d(TAG,"myRecordViewmodel.getbitmapList.value?.isNotEmpty()  :${myRecordViewmodel.getbitmapList.value?.isNotEmpty() }")
        val textOk =  myRecordViewmodel.getnote.value?.trim() !=""
        return   imageOk && textOk
    }
    private fun basicTypeAllOk():Boolean{
        //이미 카테고리 텍스트를 선택했을때
        var imageOk = false
        var categoryOk = myRecordViewmodel.getcategoryText.value !=null
        myRecordViewmodel.getUrlList.value?.let {
            imageOk= myRecordViewmodel.getUrlList.value!!.isNotEmpty()
        }
        val textOk =  myRecordViewmodel.getnote.value?.trim() !=""
        var tiemOk =false
        myRecordViewmodel.getok.value?.let {
            tiemOk= it
        }
        return (categoryOk && imageOk && textOk && tiemOk)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myRecyclerView = recordRecyclerAdapter(this)
        if (myRecordViewmodel.getbitmapList.value != null){
            Log.d(TAG ,"비트맵 어레이 크기 -> ${myRecordViewmodel.getbitmapList.value!!.size}" )
            myRecyclerView.submitBitmap(myRecordViewmodel.getbitmapList.value!!)
        }

        mBinding?.recordRecyclerImage?.adapter=myRecyclerView
        if (first){
            myRecordViewmodel.getmodifyDaynote.value?.let {
                first = false
                it.imgList.forEach {
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(it)
                        .into(object : CustomTarget<Bitmap>(){
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                Log.d(TAG, " image리스트 it -> $it")
                                myRecordViewmodel.addbitmapList(resource)
                                myRecyclerView.notifyDataSetChanged()
                            }
                            override fun onLoadCleared(placeholder: Drawable?) {}

                        })
                }
                (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())

            }
        }
        //모두 작성했는지 체크
        (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())


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
            mBinding?.recordTimeLayout ->{
                val calendarFragment = BottomYearMonthFragment()
                calendarFragment.show(parentFragmentManager,calendarFragment.tag)
            }

        }
    }

    override fun onSearchItemClicked(position: Int) {
        Log.d(TAG, "posisiton -> $position")
        myRecordViewmodel.removeBitmapList(position)
        if (myRecordViewmodel.recordtype == DaynoteRecordActivity.RecordType.basic) myRecordViewmodel.removeUrlList(position)

        myRecyclerView.notifyDataSetChanged()
        (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())
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
        startForResult.launch(intent)
    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->
        if(result.resultCode!= Activity.RESULT_OK){
            Toast.makeText(requireContext(),"잘못된 접근입니다",Toast.LENGTH_SHORT).show()
        }else{
            val selectedImageURI : Uri? = result?.data?.data
            if( selectedImageURI != null) {
                myRecordViewmodel.addUrlList(selectedImageURI)
                showImage(selectedImageURI)
            }else {
                Toast.makeText(requireContext(),"사진을 가져오지 못했습니다",Toast.LENGTH_SHORT).show()
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
                (activity as DaynoteRecordActivity).setEnableNextBtnPart(allOk())
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


}