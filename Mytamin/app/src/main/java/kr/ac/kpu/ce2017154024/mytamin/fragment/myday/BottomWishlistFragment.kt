package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.BottomWishlistBinding
import kr.ac.kpu.ce2017154024.mytamin.model.modifyWish
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel


class BottomWishlistFragment(statetext: String, idd: Int,position:Int) : BottomSheetDialogFragment(),View.OnClickListener {
    private val statetext = statetext
    private val idd = idd

    private val position = position
    private lateinit var mbinding: BottomWishlistBinding
    private val myMydayViewmodel: MydayViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding = BottomWishlistBinding.inflate(layoutInflater)
        mbinding?.bottomWishTitle.text = statetext
        mbinding?.bottomDeleteBtn.setOnClickListener(this)
        mbinding?.bottomModifyBtn.setOnClickListener(this)
        return mbinding.root
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.bottomDeleteBtn ->{
                myMydayViewmodel.setWishlistDelete(idd)
                onDestroyView()
            }
            mbinding?.bottomModifyBtn->{
                myMydayViewmodel.setWishlistModify(modifyWish(id = this.idd, statetext = this.statetext ,position= this.position))
                onDestroyView()
            }
        }
    }


}