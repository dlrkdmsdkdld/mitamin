package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView.wishlist_RecyclerView

interface IWishRecyclerAdapter {
    //검색버튼 클릭
    fun onSearchItemClicked(position: Int,statetext: String,id:Int,modify:Boolean?)
}