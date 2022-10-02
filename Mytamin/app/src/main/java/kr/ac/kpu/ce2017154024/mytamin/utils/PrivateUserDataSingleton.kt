package kr.ac.kpu.ce2017154024.mytamin.utils


object PrivateUserDataSingleton{
    lateinit var userEmail:String
    lateinit var userPassword:String
    lateinit var accessToken:String//=""
    fun isTokenINitialized() = ::accessToken.isInitialized
    lateinit var refreshToken:String


}