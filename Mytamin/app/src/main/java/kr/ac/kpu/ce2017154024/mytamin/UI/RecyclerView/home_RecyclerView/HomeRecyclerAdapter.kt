package kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R

class HomeRecyclerAdapter(HomeRecyclerview: IHomeRecyclerView) : RecyclerView.Adapter<HomeRecyclerViewHolder>() {
    private var iHomeRecyclerview:IHomeRecyclerView?=null
    private var stepArray=ArrayList<Int>()
    private var stateArray=ArrayList<String>()
    private var onArray = ArrayList<Boolean>()
    init {
        stepArray.add(1)
        stepArray.add(2)
        stepArray.add(3)
        stepArray.add(4)
        stateArray.add("숨 고르기")
        stateArray.add("감각 깨우기")
        stateArray.add("하루 진단하기")
        stateArray.add("칭찬 처방하기")
        onArray.add(true)
        onArray.add(true)
        onArray.add(true)
        onArray.add(true)
        this.iHomeRecyclerview = HomeRecyclerview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val itemViewHolder = HomeRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_item,parent,false),this.iHomeRecyclerview!!
        )
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.bindWithView(this.stepArray[position],this.stateArray[position],this.onArray[position])
    }
    fun AlreadyTodayMytamin(){
        onArray[2]=false
        onArray[3]=false
    }

    override fun getItemCount(): Int {
        return  this.stateArray.size
    }
}