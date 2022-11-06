package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.care_RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.model.careMytamin

class CareChildAdapter(data:ArrayList<careMytamin>): RecyclerView.Adapter<CareChildViewHolder>() {

    private  val dataArray =data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareChildViewHolder {

        val item = CareChildViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_care_child,parent,false))
        return item

    }

    override fun onBindViewHolder(holder: CareChildViewHolder, position: Int) {
        holder.bind(dataArray[position])
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }
}