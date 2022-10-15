package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.HomeRecyclerViewHolder
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class WishlistRecyclerAdapter(WishlistRecyclerview: IWishRecyclerAdapter,titleArray: ArrayList<String>,countArray:ArrayList<Int>)  : RecyclerView.Adapter<WishlistRecyclerViewHolder>(){
    private var IWishlistRecyclerview:IWishRecyclerAdapter?=null

    private lateinit var TitleArray:ArrayList<String>
    private lateinit var CountArray:ArrayList<Int>
    init {
        this.TitleArray = titleArray
        this.CountArray= countArray
        this.IWishlistRecyclerview=WishlistRecyclerview
        Log.d(TAG,"TitleArray ->$TitleArray  CountArray->$CountArray   ")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistRecyclerViewHolder {
        val itemViewHolder = WishlistRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wishlist_item,parent,false),this.IWishlistRecyclerview!!
        )
        return itemViewHolder
    }

    override fun getItemCount(): Int {
        return this.TitleArray.size
    }

    override fun onBindViewHolder(holder: WishlistRecyclerViewHolder, position: Int) {
        holder.bindWithView(this.TitleArray[position],this.CountArray[position])
    }


}