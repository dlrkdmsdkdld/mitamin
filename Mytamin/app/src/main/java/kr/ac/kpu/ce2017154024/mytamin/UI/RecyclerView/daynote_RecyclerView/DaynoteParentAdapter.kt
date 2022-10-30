package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteDataParent

class DaynoteParentAdapter(FInterface:IfooterInterface, Interface :IDaynoteChildInterface,data:ArrayList<daynoteDataParent>  ) :RecyclerView.Adapter<DaynoteParentVeiwHolder>(){
    private var DATA = data
    private var DaynoteInterface = Interface
    private var FootInterface = FInterface
    override fun getItemCount(): Int {
        return DATA.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaynoteParentVeiwHolder {
        val itemViewHolder = DaynoteParentVeiwHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.daynote_parent_item,parent,false)
            ,FootInterface,DaynoteInterface
        )
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: DaynoteParentVeiwHolder, position: Int) {
        holder.bindWithView(DATA[position].year,DATA[position].daynoteArrayList!!)
        //DATA[position].daynoteArrayList?.let { holder.bindWithView(DATA[position].year, it) }
    }
}