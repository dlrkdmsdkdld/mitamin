package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.daynote_parent_item.view.*
import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class DaynoteParentVeiwHolder(itemView: View, Interface :IDaynoteChildInterface):RecyclerView.ViewHolder(itemView) {
    private var childInterface = Interface
    private var title = itemView.daynote_parent_text

    private var recyclerView = itemView.daynote_parent_recycler

    fun bindWithView(titledata:Int,daynoteData: ArrayList<daynoteData>){
        Log.d(TAG,"bindWithView titledata = $titledata daynoteData = $daynoteData")
        title.text = "${titledata}년"
        recyclerView.apply {
            val childAdapter = DaynoteChildAdapter(childInterface,daynoteData)
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)

            adapter =childAdapter

        }
    }


}