package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView.DaynoteParentAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView.IDaynoteChildInterface
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView.IfooterInterface
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.WishlistRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.activity.DaynoteDetailActivity
import kr.ac.kpu.ce2017154024.mytamin.activity.DaynoteRecordActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentDaynoteBinding
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.MydayViewmodel

class DaynoteFragment : Fragment(),View.OnClickListener,IDaynoteChildInterface,IfooterInterface {
    private var mBinding : FragmentDaynoteBinding?=null
    private val myMydayViewmodel: MydayViewmodel by activityViewModels()
    private lateinit var myDaynoteParentAdapter: DaynoteParentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDaynoteBinding.inflate(inflater,container,false)
        mBinding =binding
        myMydayViewmodel.getDaynoteContent.observe(viewLifecycleOwner , Observer {
            if (it) {
                mBinding?.daynoteNoLayout?.visibility = View.GONE
                mBinding?.daynoteRecyclerview?.visibility=View.VISIBLE
            }else{
                mBinding?.daynoteNoLayout?.visibility = View.VISIBLE
                mBinding?.daynoteRecyclerview?.visibility=View.GONE
            }
        })
        Log.d(Constant.TAG,"DaynoteFragment onCreateView")
        mBinding?.daynoteNoBtn?.setOnClickListener(this)
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"DaynoteFragment onDestroyView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myMydayViewmodel.getdaynoteDataArray.observe(viewLifecycleOwner, Observer {
           // noInitLayout()
            this.myDaynoteParentAdapter = DaynoteParentAdapter(this,this,it)
            mBinding?.daynoteRecyclerview?.adapter = this.myDaynoteParentAdapter
            mBinding?.daynoteRecyclerview?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        })
    }
    fun noInitLayout(){
        mBinding?.daynoteNoLayout?.visibility=View.INVISIBLE
        mBinding?.daynoteNoBtn?.isEnabled=false
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.daynoteNoBtn ->{
                goRecord()

            }
        }
    }
    private fun goRecord(){
        val intent = Intent(requireContext(),DaynoteRecordActivity::class.java)
        if (myMydayViewmodel.getWishlistContent.value == true){
            val bundle=Bundle()
            val wishlistArray = myMydayViewmodel.getwishListArray.value?.toTypedArray()
            bundle.putSerializable("wishlistArray",wishlistArray)
            intent.putExtra("array_bundle",bundle)
            startActivity(intent)
        }else{
            startActivity(intent)
        }
    }

    override fun onSearchItemClicked(position: Int, data: daynoteData) {
        Log.d(TAG,"onSearchItemClicked $data")
        val intent=Intent(requireContext(),DaynoteDetailActivity::class.java)
        val bundle=Bundle()
        bundle.putSerializable("data",data)
        intent.putExtra("bundle_data",bundle)

        val bundleWish=Bundle()
        val wishlistArray = myMydayViewmodel.getwishListArray.value?.toTypedArray()
        bundleWish.putSerializable("wishlistArray",wishlistArray)
        intent.putExtra("array_bundle",bundleWish)

        startActivity(intent)

    }

    override fun onclickfooter() {
        goRecord()
    }
}