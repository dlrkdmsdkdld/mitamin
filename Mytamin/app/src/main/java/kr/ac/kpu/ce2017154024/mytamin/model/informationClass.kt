package kr.ac.kpu.ce2017154024.mytamin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class informationClass {
}

data class ProfileData(val nickname:String,val profileImgUrl:String?,val beMyMessage:String?){

}
data class MydayData(val myDayMMDD:String,val dday:String,val comment:String){}

enum class WishListType {
    Hidden,
    Normal
}
data class WishList(val wishId:Int , val wishText:String,val count:Int): Serializable{}
data class EditProfile(val isImgEdited:String,val nickname:String , val beMyMessage:String)
data class daynoteData(val daynoteId:Int , val imgList:ArrayList<String> , val year :Int,val month:Int , val wishText:String,val note:String)

data class daynoteDataParent(val year:Int , val daynoteArrayList: ArrayList<daynoteData>?)

