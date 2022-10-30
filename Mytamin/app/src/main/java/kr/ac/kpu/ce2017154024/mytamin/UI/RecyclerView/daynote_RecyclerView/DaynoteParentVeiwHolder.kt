package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.daynote_parent_item.view.*

class DaynoteParentVeiwHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private var title = itemView.daynote_parent_text
    private var recyclerView = itemView.daynote_parent_recycler

    fun bindWithView(titledata:String){
        title.text = titledata
        recyclerView.apply {
         //   adapter =
        }
    }


}