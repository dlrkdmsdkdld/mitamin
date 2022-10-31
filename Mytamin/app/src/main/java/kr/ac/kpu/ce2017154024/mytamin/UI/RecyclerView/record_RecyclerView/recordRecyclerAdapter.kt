package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.record_RecyclerView

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView

class recordRecyclerAdapter(HomeRecyclerview: IHomeRecyclerView):RecyclerView.Adapter<recordRecycleerViewHolder>(){

    private var myIHomeRecyclerView = HomeRecyclerview
    private var bitmapArray = ArrayList<Bitmap>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recordRecycleerViewHolder {
        val itemholder = recordRecycleerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.record_item,parent,false),this.myIHomeRecyclerView
        )
        return itemholder
    }
    fun addBitmap(bitmap: Bitmap){
        this.bitmapArray.add(bitmap)
    }
    fun submitBitmap(bitmapArr: ArrayList<Bitmap>){
        bitmapArray.clear()
        bitmapArray =bitmapArr
    }
    override fun onBindViewHolder(holder: recordRecycleerViewHolder, position: Int) {
        holder.bindWithView(bitmapArray[position])
    }

    override fun getItemCount(): Int {
        return bitmapArray.size
    }
}