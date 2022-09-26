package kr.ac.kpu.ce2017154024.mytamin.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING.introduce_first_talk_one
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING.introduce_first_talk_two
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
fun Int?.parseIntToHH():String{
    val hour:Int? =this
    var parseHour = hour.toString()
    if (hour!!<10){
        parseHour="0$hour"
    }
    return parseHour
}
fun Int?.parseIntToMM():String{
    val minute:Int?=this
    var parseminute = minute.toString()
    if (minute!!<10){
        parseminute="0$minute"
    }
    return parseminute
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
fun parseTimeToHome():String{
    val tmp = System.currentTimeMillis()
    val home_dateFormat= SimpleDateFormat("MM.dd.E", Locale("En", "KR"))
    val parseData=home_dateFormat.format(tmp)
    Log.d(TAG,"현재 날짜 ${parseData}")
    return parseData
}

// 에딧 텍스트에 대한 익스텐션
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit){
    this.addTextChangedListener(object: TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    })
}
//문자열이 제이슨 형태인지, 제이슨 배열 형태인지
fun String?.isJsonObject():Boolean {
    if (this?.startsWith("{" ) == true &&this.endsWith("}")){
        return  true
    }else{
        return false
    }
    //return this?.startsWith("{" ) == true &&this.endsWith("}")
}
fun String?.isJsonArray():Boolean{
    if (this?.startsWith("[" ) == true &&this.endsWith("]")){
        return  true
    }else{
        return false
    }
}
fun String?.IntroduceHello():String{
    val nickname=this
    val result = introduce_first_talk_one+nickname+introduce_first_talk_two
    return result
}
