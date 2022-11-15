package kr.ac.kpu.ce2017154024.mytamin.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomFragmentAlarmMydayBinding
import kr.ac.kpu.ce2017154024.mytamin.viewModel.settingViewmodel


class AlarmMydayFragment : BottomSheetDialogFragment(),View.OnClickListener {
    private lateinit var mbinding:BottomFragmentAlarmMydayBinding
    private val myviewmodel: settingViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding=BottomFragmentAlarmMydayBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        mbinding?.bottomMyday1.setOnClickListener(this)
        mbinding?.bottomMyday2.setOnClickListener(this)
        mbinding?.bottomMyday3.setOnClickListener(this)
        return mbinding.root
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.bottomMyday1 ->{
                myviewmodel.setdayTime(1)
                onDestroyView()
            }
            mbinding?.bottomMyday2 ->{
                myviewmodel.setdayTime(2)

                onDestroyView()
            }
            mbinding?.bottomMyday3 ->{
                myviewmodel.setdayTime(3)
                onDestroyView()

            }
        }
    }

}