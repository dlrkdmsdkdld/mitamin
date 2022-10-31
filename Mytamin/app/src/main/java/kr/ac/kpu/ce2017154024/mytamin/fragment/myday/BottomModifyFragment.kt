package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.DaynoteDetailActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomModifyFragmentBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.select


class BottomModifyFragment : BottomSheetDialogFragment(),View.OnClickListener{

    private lateinit var mbinding : BottomModifyFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding = BottomModifyFragmentBinding.inflate(layoutInflater)
        mbinding?.bottomDeleteBtn.setOnClickListener(this)
        mbinding?.bottomModifyBtn.setOnClickListener(this)

        return mbinding?.root
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.bottomModifyBtn ->{
                (activity as DaynoteDetailActivity).choiceOption(select.modify)
                onDestroyView()
            }
            mbinding?.bottomDeleteBtn ->{
                (activity as DaynoteDetailActivity).choiceOption(select.delete)
                onDestroyView()
            }
        }
    }


}