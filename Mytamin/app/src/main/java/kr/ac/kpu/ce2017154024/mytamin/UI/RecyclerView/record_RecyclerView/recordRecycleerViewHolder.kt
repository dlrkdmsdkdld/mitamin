package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.record_RecyclerView

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat
import kotlinx.android.synthetic.main.record_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView

class recordRecycleerViewHolder(itemView: View,iHomeRecyclerView: IHomeRecyclerView ):
    View.OnClickListener , RecyclerView.ViewHolder(itemView){
    private val myiHomeRecyclerView = iHomeRecyclerView
    private val image = itemView.record_item_image
    private val btn = itemView.record_item_btn
    init {
        btn.setOnClickListener(this)
    }
    fun bindWithView(bitmap: Bitmap){
        val drawable = BitmapDrawable(Resources.getSystem(),bitmap)
        image.setBackgroundCompat(drawable)
        Glide.with(itemView)
            .load(bitmap)
            .centerCrop()
            .into(image)
    }


    override fun onClick(p0: View?) {
        when(p0){
            btn ->{
                this.myiHomeRecyclerView.onSearchItemClicked(adapterPosition)
            }
        }
    }
}