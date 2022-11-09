package kr.ac.kpu.ce2017154024.mytamin.fragment.find

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentFindThreeBinding


class FindThreeFragment : Fragment() {

    private lateinit var mbinding:FragmentFindThreeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding=FragmentFindThreeBinding.inflate(inflater,container,false)

        return mbinding.root
    }


}