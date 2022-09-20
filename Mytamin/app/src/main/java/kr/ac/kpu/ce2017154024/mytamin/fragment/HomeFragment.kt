package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import kr.ac.kpu.ce2017154024.mytamin.RecyclerView.home_RecyclerView.HomeRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.RecyclerView.home_RecyclerView.IHomeRecyclerView
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHomeBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class HomeFragment : Fragment(),View.OnClickListener,IHomeRecyclerView {
    private var mBinding : FragmentHomeBinding?=null
    //홈 리싸이클러뷰
    private lateinit var myHomeRecyclerAdapter: HomeRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"HomeFragment onCreateView")
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //리싸이클러뷰 설정
        this.myHomeRecyclerAdapter = HomeRecyclerAdapter(this)
        home_recyclerView.adapter = myHomeRecyclerAdapter
        todayMytaminBtn.setOnClickListener(this)
    }

    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"HomeFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            todayMytaminBtn -> {
                val intent = Intent(context,todayMytaminActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSearchItemClicked(position: Int) {
        Log.d(TAG,"클릭한 리싸이클러뷰 $position 번째")
    }

}