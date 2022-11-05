package kr.ac.kpu.ce2017154024.mytamin.fragment.myday

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_wishlist.*
import kotlinx.android.synthetic.main.wishlist_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.IWishRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView.WishlistRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.WishlistSnackbar
import kr.ac.kpu.ce2017154024.mytamin.activity.MydayActivity
import kr.ac.kpu.ce2017154024.mytamin.activity.NewWishListActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentWishlistBinding
import kr.ac.kpu.ce2017154024.mytamin.model.modifyWish
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

        myMydayViewmodel.getWishlistDelete.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"위시리스트 아이디 : $it")
            if (it!=0){
                deleteWish(it)
                myMydayViewmodel.setWishlistDelete(0)
            }


        })
        //수정하기 버튼 눌렀을 때
        myMydayViewmodel.getWishlistModify.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, " modfiywish 데이터 값 : $it")
            if (it.id!=0 && it.position!=0){ // if문이 있는이유 : obsever때문에 wishlistFragment가 oncreate될때마다 옵저버가 실행되어서
                //원치 않을때도 수정하기가 활성화 되는 버그가있음
                mBinding?.wishlistRecyclerview?.findViewHolderForAdapterPosition(it.position)!!.itemView.apply {
                    this.wishlist_title_item.visibility = View.INVISIBLE
                    this.wishlist_edit_item.visibility = View.VISIBLE
                    myWishlistRecyclerAdapter.selectWishID(it.id)
                    this.wishlist_edit_item.setText(this.wishlist_title_item.text)
                }
                myMydayViewmodel.setWishlistModify(modifyWish(position = 0 , id = 0, statetext = ""))
            }

        })

        return mBinding?.root
    }
    private fun deleteWish(id:Int){
        //myMydayViewmodel.deleteWishlistAPI(id)
        Log.d(TAG, "deleteWish i: d :$id ")
        WishlistSnackbar.make(requireView(),id).show()
//        Snackbar.make(requireView(),"삭제하시겠습니까?",Snackbar.LENGTH_SHORT).setAction("실행취소",object :View.OnClickListener{
//            override fun onClick(p0: View?) {
//                TODO("Not yet implemented")
//            }
//
//        })
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



    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.wishlistNoBtn ->{
                val intent = Intent(context,NewWishListActivity::class.java)
                //startActivity(intent)
                startActivityForResult(intent, MydayActivity.SUB_ACTIVITY_CODE)
            }
            mBinding?.wishlistCompleteBtn ->{
                val tmp = mBinding?.wishlistNewWishlist?.text.toString().trim()
                mBinding?.wishlistNewWishlist?.setText("")
                InformationRetrofitManager.instance.sendNewWishlist(tmp){
                        responseStatus, wishList ->
                    myMydayViewmodel.getWishlistAPI()
                }
            }

        }
    }

    override fun onSearchItemClicked(position: Int, statetext: String, id: Int, modify: Boolean?) {
        Log.d(TAG,"stateText - > $statetext id ->$id")
        val bottomWish  = BottomWishlistFragment(statetext,id,position)
        bottomWish.show(parentFragmentManager,bottomWish.tag)

    }
}