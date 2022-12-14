package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import android.app.Application
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.daynote_child_footer.view.*
import kotlinx.android.synthetic.main.daynote_image_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.model.WishList
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant

class FooterViewHolder(itemView: View,Interface:IfooterInterface) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
    val footerInterface = Interface
    private var layout = itemView.daynote_item_footer
    init {
        layout.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0){
            layout ->{
                this.footerInterface.onclickfooter()
            }
        }
    }

}

class DaynoteChildViewHolder(itemView: View,Interface :IDaynoteChildInterface): RecyclerView.ViewHolder(itemView) ,View.OnClickListener{

    private var RecyclerViewInterface=Interface
    private var monthText = itemView.daynote_item_text
    private var imageview = itemView.daynote_item_image
    private var layout = itemView.daynote_item_layout
    private lateinit var daynote : daynoteData

    init {
        layout.setOnClickListener(this)

    }
    fun bindWithView(data: daynoteData){
        this.daynote=data
        monthText.text = "${data.month}월"
        data.imgList?.let {
            Glide.with(itemView)
                .load(data.imgList.get(0))
                .centerCrop()
                .into(imageview)
        }


    }
    override fun onClick(p0: View?) {
        when(p0){
            layout ->{
                this.RecyclerViewInterface.onSearchItemClicked(adapterPosition,daynote)
            }
        }
    }


}
