package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.ProfileEditActivity
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentInformationBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentManageMentBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class UserInformationFragment : Fragment(),View.OnClickListener {
    private var mBinding : FragmentInformationBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInformationBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"UserInformationFragment onCreateView")
        mBinding?.informationUserImage?.setOnClickListener(this)
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"UserInformationFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.informationUserImage->{
                val bitmap:Bitmap = mBinding?.informationUserImage!!.drawToBitmap()
                //mBinding?.informationUserImage!!.get
                val bos:ByteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,20,bos)
                //일단 jpeg로함
                val intent= Intent(context, ProfileEditActivity::class.java)
                intent.putExtra("profile_image",bos.toByteArray())
                startActivity(intent)
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos)
//                val  file : File =  File(Environment.getExternalStorageDirectory() + File.separator + "your_file.jpg");
//                try {
//                    file.createNewFile();
//                    val fos : FileOutputStream = FileOutputStream(file);
//                    fos.write(bos.toByteArray());
//                } catch (IOException  e) {
//                    e.printStackTrace();
//                }


            }
        }

    }

}