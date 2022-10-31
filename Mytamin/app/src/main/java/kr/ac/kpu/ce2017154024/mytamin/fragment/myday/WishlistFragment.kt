package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kr.ac.kpu.ce2017154024.mytamin.activity.MydayActivity
import kr.ac.kpu.ce2017154024.mytamin.activity.NewWishListActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentWishlistBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel


class WishlistFragment : Fragment(), IWishRecyclerAdapter,View.OnClickListener {
    private var mBinding : FragmentWishlistBinding?=null
    private lateinit var myWishlistRecyclerAdapter: WishlistRecyclerAdapter
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
                mBinding?.wishlistNoBtn?.isEnabled=false
            }
        })
        Log.d(Constant.TAG,"WishlistFragment onCreateView")
        mBinding?.wishlistNoBtn?.setOnClickListener(this)
        mBinding?.wishlistCompleteBtn?.setOnClickListener(this)

        return mBinding?.root
    }

//    myRecyclerView = recordRecyclerAdapter(this)
//    if (myRecordViewmodel.getbitmapList.value != null){
//        Log.d(TAG ,"비트맵 어레이 크기 -> ${myRecordViewmodel.getbitmapList.value!!.size}" )
//        myRecyclerView.submitBitmap(myRecordViewmodel.getbitmapList.value!!)
//    }
//    mBinding?.recordRecyclerImage?.adapter=myRecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myMydayViewmodel.getwishListArray.observe(viewLifecycleOwner, Observer {
            myWishlistRecyclerAdapter.submitData(it)
            myWishlistRecyclerAdapter.notifyDataSetChanged()
        })
        mBinding?.wishlistNewWishlist?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().trim()!=""){
                    mBinding?.wishlistCompleteBtn?.visibility=View.VISIBLE
                    mBinding?.wishlistCompleteBtn?.isEnabled = true
                }else{
                    mBinding?.wishlistCompleteBtn?.visibility=View.INVISIBLE
                    mBinding?.wishlistCompleteBtn?.isEnabled = false
                }
            }

        })
        this.myWishlistRecyclerAdapter= WishlistRecyclerAdapter(this)
        mBinding?.wishlistRecyclerview?.adapter = this.myWishlistRecyclerAdapter
        Log.d(TAG,"wishlist_recyclerview.adapter  ->${wishlist_recyclerview.adapter} ")
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"WishlistFragment onDestroyView")
    }


    override fun onSearchItemClicked(position: Int, statetext: String,id:Int) {
        Log.d(TAG,"stateText - > $statetext")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.wishlistNoBtn ->{
                val intent = Intent(context,NewWishListActivity::class.java)
                //startActivity(intent)
                startActivityForResult(intent, MydayActivity.SUB_ACTIVITY_CODE)
            }
            mBinding?.wishlistCompleteBtn ->{
                InformationRetrofitManager.instance.sendNewWishlist(mBinding?.wishlistNewWishlist?.text.toString().trim()){
                        responseStatus, wishList ->
                    myMydayViewmodel.getWishlistAPI()
                }
            }

        }
    }
}