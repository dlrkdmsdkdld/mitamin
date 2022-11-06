package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_care_child.view.*
import kr.ac.kpu.ce2017154024.mytamin.model.careMytamin

class CareChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val care_child_msg1Text = itemView.care_child_msg1
    private val care_child_msg2Text = itemView.care_child_msg2
    private val care_child_takeText = itemView.care_child_take
    private val care_child_categoryText = itemView.care_child_category

    fun bind(data: careMytamin){
        care_child_msg1Text.text = data.careMsg1
        care_child_msg2Text.text = data.careMsg2
        care_child_takeText.text = data.takeAt
        care_child_categoryText.text = data.careCategory
    }

}