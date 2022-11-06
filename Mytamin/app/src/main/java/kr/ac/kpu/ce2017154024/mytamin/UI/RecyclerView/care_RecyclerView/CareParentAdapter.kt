package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.model.monthCareMytamin

class CareParentAdapter(data:ArrayList<monthCareMytamin>) :RecyclerView.Adapter<CareParentViewHolder>(){
    var monthMytaminArray  = data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareParentViewHolder{
        val itemview = CareParentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_care_parent,parent,false)    )
        return itemview
    }

    override fun onBindViewHolder(holder: CareParentViewHolder, position: Int) {
        holder.bind(monthMytaminArray[position])
    }

    override fun getItemCount(): Int {
        return monthMytaminArray.size
    }
}