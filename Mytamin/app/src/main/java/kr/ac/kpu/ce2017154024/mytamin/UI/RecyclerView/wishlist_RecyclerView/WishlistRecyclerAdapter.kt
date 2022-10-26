package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.HomeRecyclerViewHolder
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class WishlistRecyclerAdapter(WishlistRecyclerview: IWishRecyclerAdapter,idArray:List<WishList>)  : RecyclerView.Adapter<WishlistRecyclerViewHolder>(){
    private var IWishlistRecyclerview:IWishRecyclerAdapter?=null

    private lateinit var WishiListArray:List<WishList>
    init {

        this.WishiListArray= idArray
        this.IWishlistRecyclerview=WishlistRecyclerview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistRecyclerViewHolder {
        val itemViewHolder = WishlistRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wishlist_item,parent,false),this.IWishlistRecyclerview!!
        )
        return itemViewHolder
    }

    override fun getItemCount(): Int {
        return this.WishiListArray.size
    }

    override fun onBindViewHolder(holder: WishlistRecyclerViewHolder, position: Int) {
        holder.bindWithView(this.WishiListArray[position])
    }


}