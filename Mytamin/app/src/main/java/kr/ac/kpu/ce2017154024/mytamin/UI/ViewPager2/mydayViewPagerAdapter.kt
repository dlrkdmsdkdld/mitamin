package kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.ac.kpu.ce2017154024.mytamin.fragment.myday.DaynoteFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.myday.WishlistFragment

private const val NUM_TABS = 2

class mydayViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return WishlistFragment()
            0 -> return DaynoteFragment()
        }
        return DaynoteFragment()
    }


}