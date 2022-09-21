package kr.ac.kpu.ce2017154024.mytamin.utils

import android.util.Log
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun Int?.parseIntToTimeLine():String{
    val minute : Int? = this?.div(60)
    val second  = this?.rem(60)
    var parseSecond = second.toString()
    if (second!!<10){
        parseSecond="0$second"
    }
    return "0$minute:$parseSecond"
}

fun parseTimeToState(username:String):String{
    val tmp = System.currentTimeMillis()
    val timedata=Date(tmp)
    when(timedata.hours){
        in 0..4 ->{
            return "${username}님, 푹 쉬고 내일 만나요"
        }
        in 5..11 ->{
            return "${username}님, 오늘도\n 힘차게 시작해볼까요?"
        }
        else -> {
            return "${username}님, 어떤 하루를\n보내고 계신가요?"
        }
    }
    Log.d(TAG,"현재 시간 ${timedata.hours}")
    Log.d(TAG,"현재 시간 ${timedata.minutes}")

}
