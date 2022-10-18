package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_wishlist.*
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.IWishRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.WishlistRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentWishlistBinding
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel


class WishlistFragment : Fragment(), IWishRecyclerAdapter {
    private var mBinding : FragmentWishlistBinding?=null
    private lateinit var myWishlistRecyclerAdapter: WishlistRecyclerAdapter
    private lateinit var HiddenWishlistRecyclerAdapter: WishlistRecyclerAdapter
    private val myMydayViewmodel: MydayViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWishlistBinding.inflate(inflater,container,false)
        mBinding =binding
        myMydayViewmodel.getWishlistContent.observe(viewLifecycleOwner , Observer {
            if (it) {
                mBinding?.wishlistTitleYesLayout?.visibility = View.VISIBLE
                mBinding?.wishlistTitleNoLayout?.visibility=View.INVISIBLE
                mBinding?.daynoteNoBtn?.isEnabled=false
            }
        })
        Log.d(Constant.TAG,"WishlistFragment onCreateView")
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tmptitle = ArrayList<String>()
        tmptitle.add("하하")
        tmptitle.add("하1")
        tmptitle.add("하2")
        tmptitle.add("하2213132132132")
        val tmpcount = ArrayList<Int>()
        tmpcount.add(1)
        tmpcount.add(2)
        tmpcount.add(3)
        tmpcount.add(4)
        this.myWishlistRecyclerAdapter= WishlistRecyclerAdapter(this,tmptitle,tmpcount)
        wishlist_recyclerview.adapter = this.myWishlistRecyclerAdapter

        val tmptitleH = ArrayList<String>()
        tmptitleH.add("숨겨진")
        val tmpcountH = ArrayList<Int>()
        tmpcountH.add(10)
        HiddenWishlistRecyclerAdapter = WishlistRecyclerAdapter(this,tmptitleH,tmpcountH)
        wishlist_recyclerview_hidden.adapter = this.HiddenWishlistRecyclerAdapter



        Log.d(TAG,"wishlist_recyclerview.adapter  ->${wishlist_recyclerview.adapter} ")
       // mBinding?.wishlistRecyclerview?
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"WishlistFragment onDestroyView")
    }


    override fun onSearchItemClicked(position: Int, statetext: String) {
        Log.d(TAG,"stateText - > $statetext")
    }
}