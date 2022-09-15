package kr.ac.kpu.ce2017154024.mytamin.utils

import java.text.SimpleDateFormat

fun Int?.parseIntToTimeLine():String{
    val minute : Int? = this?.div(60)
    val second  = this?.rem(60)
    var parseSecond = second.toString()
    if (second!!<10){
        parseSecond="0$second"
    }
    return "0$minute:$parseSecond"


}