package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData

class DaynoteChildAdapter( footerInterface: IfooterInterface,Interface :IDaynoteChildInterface,data:ArrayList<daynoteData> ) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var DaynoteInterface = Interface
    private var daynoteArrayList =data
    private var myfooterInterface = footerInterface

    val ITEM=1
    val FOTTER=2
    class ListViewHolder(val layout: View): RecyclerView.ViewHolder(layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

//        var itemViewHolder = when(viewType){
//            FOTTER->{
//                DaynoteChildViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.daynote_child_footer,parent,false),DaynoteInterface)
//            }
//            else ->{
//                    DaynoteChildViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daynote_image_item,parent,false),DaynoteInterface)
//
//
//            }
//        }
//
//        return itemViewHolder

        return when(viewType){
            ITEM ->DaynoteChildViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daynote_image_item,parent,false),DaynoteInterface)
            FOTTER ->FooterViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.daynote_child_footer,parent,false),myfooterInterface)

            else -> {
                throw ClassCastException("Unknown viewType $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            //holder.bind(daynoteArrayList[position])
        when(holder){
            is FooterViewHolder ->{

            }
            is DaynoteChildViewHolder ->{
                holder.bindWithView(daynoteArrayList[position])
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return  if (position==daynoteArrayList.size){
            FOTTER
        }else{
            ITEM
        }
    }

    override fun getItemCount(): Int {
        return daynoteArrayList.size+1
    }


}
