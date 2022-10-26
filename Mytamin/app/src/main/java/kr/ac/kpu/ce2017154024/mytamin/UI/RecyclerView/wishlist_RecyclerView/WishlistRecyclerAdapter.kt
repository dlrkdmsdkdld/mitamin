package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView

import android.R
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.wishlist_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG


class WishlistRecyclerAdapter(WishlistRecyclerview: IWishRecyclerAdapter,idArray:List<WishList>)  : RecyclerView.Adapter<WishlistRecyclerViewHolder>(){
    private var IWishlistRecyclerview:IWishRecyclerAdapter?=null
    private var selectedPosition = -1

    private lateinit var WishiListArray:List<WishList>
    init {

        this.WishiListArray= idArray
        this.IWishlistRecyclerview=WishlistRecyclerview
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
//        if (this.selectedPosition == position) {
//            holder.itemView.isSelected = true //using selector drawable
//            holder.itemView.wishlist_layout_item.setBackgroundResource(kr.ac.kpu.ce2017154024.mytamin.R.drawable.round_layout_background_orange)
//            holder.itemView.wishlist_count_text.setTextColor(Color.parseColor("#FFFFFFFF"))
//            holder.itemView.wishlist_count_item.setTextColor(Color.parseColor("#FFFFFFFF"))
//        } else {
//            holder.itemView.isSelected = false
//            holder.itemView.wishlist_layout_item.setBackgroundResource(kr.ac.kpu.ce2017154024.mytamin.R.drawable.round_layout_stroke_gray)
//            holder.itemView.wishlist_count_text.setTextColor(Color.parseColor("#FF7F57"))
//            holder.itemView.wishlist_count_item.setTextColor(Color.parseColor("#FF7F57"))
//        }
//
//        holder.itemView.setOnClickListener { v ->
//            if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
//            selectedPosition = holder.adapterPosition
//            notifyItemChanged(selectedPosition)
//
//            this.IWishlistRecyclerview?.onSearchItemClicked(position,holder.itemView.wishlist_title_item.text.toString(), holder.getId())
//        }
    }


}