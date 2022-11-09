package kr.ac.kpu.ce2017154024.mytamin.fragment.find

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentFindTwoBinding


class FindTwoFragment : Fragment() {
    private lateinit var mbinding:FragmentFindTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mbinding= FragmentFindTwoBinding.inflate(inflater,container,false)
        return mbinding.root
    }


}