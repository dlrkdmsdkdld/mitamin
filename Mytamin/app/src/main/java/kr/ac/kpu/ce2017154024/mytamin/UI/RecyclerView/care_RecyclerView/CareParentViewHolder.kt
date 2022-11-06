package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_care_parent.view.*
import kr.ac.kpu.ce2017154024.mytamin.model.monthCareMytamin

class CareParentViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    private val timeText = itemView.care_parent_time
    private val recyclerView = itemView.care_parent_recylcer

    fun bind(data: monthCareMytamin){
        timeText.text = data.time
        recyclerView.apply {
            val childAdapter = CareChildAdapter(data.arrayCareMytamin)
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = childAdapter
        }
    }

}