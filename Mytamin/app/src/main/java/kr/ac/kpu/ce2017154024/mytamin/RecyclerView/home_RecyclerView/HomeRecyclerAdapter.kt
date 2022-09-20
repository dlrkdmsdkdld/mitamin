package kr.ac.kpu.ce2017154024.mytamin.RecyclerView.home_RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.fragment.HomeFragment

class HomeRecyclerAdapter(HomeRecyclerview: IHomeRecyclerView) : RecyclerView.Adapter<HomeRecyclerViewHolder>() {
    private var iHomeRecyclerview:IHomeRecyclerView?=null
    private var stepArray=ArrayList<Int>()
    private var stateArray=ArrayList<String>()

    init {
        stepArray.add(1)
        stepArray.add(2)
        stepArray.add(3)
        stepArray.add(4)
        stateArray.add("숨 고르기")
        stateArray.add("감각 깨우기")
        stateArray.add("하루 진단하기")
        stateArray.add("칭찬 처방하기")
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
        holder.bindWithView(this.stepArray[position],this.stateArray[position])
    }

    override fun getItemCount(): Int {
        return  this.stateArray.size
    }
}