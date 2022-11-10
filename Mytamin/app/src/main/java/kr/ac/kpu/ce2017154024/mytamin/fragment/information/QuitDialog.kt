package kr.ac.kpu.ce2017154024.mytamin.fragment.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.DialogQuitBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding


class QuitDialog : DialogFragment(){
    private lateinit var mbinding:DialogQuitBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding= DialogQuitBinding.inflate(inflater,container,false)


        return mbinding.root
    }
}