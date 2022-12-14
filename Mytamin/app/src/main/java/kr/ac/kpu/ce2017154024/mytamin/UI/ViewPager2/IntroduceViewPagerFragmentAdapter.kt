package kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.ac.kpu.ce2017154024.mytamin.fragment.introduce.IntroduceOneStepFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.introduce.IntroduceThreeStepFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.introduce.IntroduceTwoStepFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.introduce.IntroducefourStepFragment


class IntroduceViewPagerFragmentAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    //val fragmentList = listOf<Fragment>(IntroduceOneStepFragment(),IntroduceTwoStepFragment(),IntroduceThreeStepFragment(),IntroducefourStepFragment())
    val fragmentList = listOf<Fragment>(IntroduceOneStepFragment(),IntroduceTwoStepFragment(),IntroduceThreeStepFragment())

//    override fun getCount(): Int =4
//
//    override fun getItem(position: Int): Fragment {
//        return when(position){
//            0 -> IntroduceOneStepFragment()
//            1->IntroduceTwoStepFragment()
//            2->IntroduceThreeStepFragment()
//            else -> IntroducefourStepFragment()
//        }
//    }

    override fun getItemCount(): Int=3

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }


}