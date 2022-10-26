package kr.ac.kpu.ce2017154024.mytamin.model

class informationClass {
}

data class ProfileData(val nickname:String,val profileImgUrl:String?,val beMyMessage:String?){

}
data class MydayData(val myDayMMDD:String,val dday:String,val comment:String){}

enum class WishListType {
    Hidden,
    Normal
}
data class WishList(val wishId:Int , val wishText:String,val count:Int):java.io.Serializable{}
data class EditProfile(val isImgEdited:String,val nickname:String , val beMyMessage:String)
