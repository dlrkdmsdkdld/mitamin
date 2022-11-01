package kr.ac.kpu.ce2017154024.mytamin.fragment.record

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.IWishRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.SingleSelectWishAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.WishlistRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.activity.DaynoteRecordActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentRecordBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentSelectRecordBinding
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.viewModel.RecordViewmodel


class SelectRecordFragment : Fragment(), IWishRecyclerAdapter,View.OnClickListener {
    private var mBinding : FragmentSelectRecordBinding?=null
    private val myRecordViewmodel: RecordViewmodel by activityViewModels()
    private lateinit var myWishlistRecyclerAdapter: SingleSelectWishAdapter
    private var select = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSelectRecordBinding.inflate(inflater,container,false)
        mBinding =binding
        (activity as DaynoteRecordActivity).selectwishList(true)
        (activity as DaynoteRecordActivity).setEnableNextBtnPart(false)
        Log.d(Constant.TAG,"SelectRecordFragment onCreateView")
        if (myRecordViewmodel.getwishListArray.value !=null){
            val array = myRecordViewmodel.getwishListArray.value
            val arrayList = ArrayList<WishList>()
            array?.forEach {
                arrayList.add(it)
            }
            this.myWishlistRecyclerAdapter= SingleSelectWishAdapter(this,arrayList)
        }
        mBinding?.recordRecyclerWish?.adapter = this.myWishlistRecyclerAdapter
        return mBinding?.root
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"SelectRecordFragment onDestroyView")
    }
    //findNavController().navigate(R.id.action_recordFragment_to_selectRecordFragment)

    override fun onSearchItemClicked(position: Int, statetext: String, id: Int) {
        (activity as DaynoteRecordActivity).setEnableNextBtnPart(true)
        Log.d(TAG,"StateText -> $statetext")
        Log.d(TAG,"position ->$position id->$id")
        myRecordViewmodel.setcategoryText(statetext)
        myRecordViewmodel.setwishId(id)
    }

    override fun onClick(p0: View?) {
        when(p0){

        }
    }
}