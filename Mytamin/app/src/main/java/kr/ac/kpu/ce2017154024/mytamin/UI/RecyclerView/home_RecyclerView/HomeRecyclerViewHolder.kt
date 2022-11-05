package kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_recycler_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.changeDP

class HomeRecyclerViewHolder (itemView: View,HomeRecylcerViewInterface:IHomeRecyclerView,context: Context)
    :RecyclerView.ViewHolder(itemView),
    View.OnClickListener {//home_item_state_text
    private var MyHomeRecylcerViewInterface:IHomeRecyclerView

    private var step = itemView.home_item_step_text
    private val state = itemView.home_item_state_text
    private val startbtn =itemView.home_item_btn
    private val image = itemView.home_item_step_image
    private val context = context
    init {
        this.MyHomeRecylcerViewInterface=HomeRecylcerViewInterface
        startbtn.setOnClickListener(this)
    }
    fun bindWithView(stepdata:Int,Statedata:String,on:Boolean=true,imageD:Int,position:Int){
        step.text=stepdata.toString()
        state.text=Statedata
        image.setImageResource(imageD)
        Log.d(TAG,"바인드함")
        //3,4번째 리싸이클러뷰는 안되게막기위해 설정함 - 추후에 이미지만 보이게하면될듯?
        if (on==false){
            startbtn.isEnabled=false
            startbtn.setBackgroundResource(R.drawable.ic_large_button_disabled)
        }
        if (position ==0){
            changeDP(20,context)

            val params = itemView.home_item_card.layoutParams

             itemView.home_item_card.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(changeDP(20,context),0,changeDP(12,context),0)
            }

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