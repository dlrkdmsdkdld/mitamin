package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.daynote_RecyclerView

import kr.ac.kpu.ce2017154024.mytamin.model.daynoteData

interface IDaynoteChildInterface {

    //검색버튼 클릭
    fun onSearchItemClicked(position: Int,data:daynoteData)

    fun onclickfooter(){

    }
}