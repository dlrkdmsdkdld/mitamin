package kr.ac.kpu.ce2017154024.mytamin.RecyclerView.home_RecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_recycler_item.view.*

class HomeRecyclerViewHolder (itemView: View,HomeRecylcerViewInterface:IHomeRecyclerView)
    :RecyclerView.ViewHolder(itemView),
    View.OnClickListener {//home_item_state_text
    private var MyHomeRecylcerViewInterface:IHomeRecyclerView
    private var step = itemView.home_item_step_text
    private val state = itemView.home_item_state_text
    private val startbtn =itemView.home_item_btn
    init {
        this.MyHomeRecylcerViewInterface=HomeRecylcerViewInterface
        startbtn.setOnClickListener(this)
    }
    fun bindWithView(stepdata:Int,Statedata:String){
        step.text=stepdata.toString()
        state.text=Statedata

    }


    override fun onClick(p0: View?) {
        when(p0){
            startbtn ->{
                this.MyHomeRecylcerViewInterface.onSearchItemClicked(adapterPosition)
            }
        }
    }
}