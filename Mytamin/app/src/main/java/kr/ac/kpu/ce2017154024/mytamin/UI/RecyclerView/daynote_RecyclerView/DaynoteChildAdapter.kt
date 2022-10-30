package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData

class DaynoteChildAdapter( Interface :IDaynoteChildInterface,data:ArrayList<daynoteData> ) :RecyclerView.Adapter<DaynoteChildViewHolder>(){
    private var DaynoteInterface = Interface
    private var daynoteArrayList =data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaynoteChildViewHolder {
        val itemViewHolder = DaynoteChildViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.daynote_image_item,parent,false)
            ,DaynoteInterface
        )
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: DaynoteChildViewHolder, position: Int) {
        holder.bindWithView(daynoteArrayList[position])
    }

    override fun getItemCount(): Int {
        return daynoteArrayList.size
    }


}