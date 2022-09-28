package kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_recycler_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

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
    fun bindWithView(stepdata:Int,Statedata:String,on:Boolean=true){
        step.text=stepdata.toString()
        state.text=Statedata
        Log.d(TAG,"바인드함")
        //3,4번째 리싸이클러뷰는 안되게막기위해 설정함 - 추후에 이미지만 보이게하면될듯?
        if (on==false){
            startbtn.isEnabled=false
        }

    }


    override fun onClick(p0: View?) {
        when(p0){
            startbtn ->{
                this.MyHomeRecylcerViewInterface.onSearchItemClicked(adapterPosition)
            }
        }
    }
}