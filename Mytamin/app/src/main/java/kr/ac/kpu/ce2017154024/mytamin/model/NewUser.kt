package kr.ac.kpu.ce2017154024.mytamin.model

data class NewUser(val email:String,val password:String,val nickname:String,val mytaminHour:String="",val mytaminMin:String="") {


}
data class email(val email:String)

data class emailCertificate(val email:String,val authCode:String)

data class ChangePassword(val password:String , val email:String)
