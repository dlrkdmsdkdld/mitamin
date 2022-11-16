package kr.ac.kpu.ce2017154024.mytamin.utils


object PrivateUserDataSingleton{
    lateinit var userEmail:String
    lateinit var userPassword:String
    lateinit var accessToken:String
    fun isTokenINitialized() :Boolean= ::accessToken.isInitialized
    lateinit var refreshToken:String
    var Createdyear:Int = 0
    var Createdmonth:Int= 0

    lateinit var NOTE:String
     var WISHTEXT:Int = 0
    lateinit var DAYNOTEDATE:String
}