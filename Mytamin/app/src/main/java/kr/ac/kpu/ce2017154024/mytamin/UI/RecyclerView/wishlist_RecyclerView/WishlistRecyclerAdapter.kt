package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView

import android.R
import android.graphics.Color
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.wishlist_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class WishlistRecyclerAdapter(WishlistRecyclerview: IWishRecyclerAdapter)  : RecyclerView.Adapter<WishlistRecyclerViewHolder>(){
    private var IWishlistRecyclerview:IWishRecyclerAdapter?=null
    private var selectedPosition = -1

    private  var WishiListArray= ArrayList<WishList>()
    init {

        this.IWishlistRecyclerview=WishlistRecyclerview
    }
    fun submitData(dataArray:ArrayList<WishList>){
        WishiListArray = dataArray
    }
    private lateinit var myholder:WishlistRecyclerViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistRecyclerViewHolder {
        val itemViewHolder = WishlistRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(kr.ac.kpu.ce2017154024.mytamin.R.layout.wishlist_item,parent,false),this.IWishlistRecyclerview!!
        )
        return itemViewHolder
    }
    fun selectView(b:Boolean){
        myholder.selectView(b)
    }
    override fun getItemCount(): Int {
        return this.WishiListArray.size
    }
    override fun onBindViewHolder(holder: WishlistRecyclerViewHolder, position: Int) {
        holder.bindWithView(this.WishiListArray[position])
        Log.d(TAG,"select position")
        holder.itemView.wishlist_edit_item.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent?.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER){
                holder.itemView.wishlist_title_item.text=holder.itemView.wishlist_edit_item.text.toString()
                Log.d(TAG, " holder.itemView.wishlist_edit_item.text.toString()  : ${holder.itemView.wishlist_edit_item.text.toString()}")
                holder.itemView.wishlist_edit_item.visibility=View.GONE
                holder.itemView.wishlist_title_item.visibility = View.VISIBLE
                // 요기에 수정데이터 전송하면될듯
                true
            }
            else{
                false
            }

        }


    }


}