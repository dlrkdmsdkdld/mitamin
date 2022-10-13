package kr.ac.kpu.ce2017154024.mytamin.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING.introduce_first_talk_one
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING.introduce_first_talk_two
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
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
fun checkEmail(email:String):Boolean{
    var email = email.trim() //공백제거
    val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val p = Pattern.matches(emailValidation, email) // 서로 패턴이 맞닝?
    if (p) {
        //이메일 형태가 정상일 경우
        return true
    } else {
        //또는 questionEmail.setTextColor(R.color.red.toInt())
        return false
    }
}
fun parseStatus(Status:Status):ArrayList<Boolean>{
    val parseStatus = ArrayList<Boolean>()
    parseStatus.add(Status.breathIsDone)
    parseStatus.add(Status.senseIsDone)
    parseStatus.add(Status.reportIsDone)
    parseStatus.add(Status.careIsDone)
    return parseStatus
}
//절대경로변환
fun getFullPathFromUri(ctx: Context, fileUri: Uri?): String? {
    var fullPath: String? = null
    val column = "_data"
    var cursor = ctx.contentResolver.query(fileUri!!, null, null, null, null)
    if (cursor != null) {
        cursor.moveToFirst()
        var document_id = cursor.getString(0)
        if (document_id == null) {
            for (i in 0 until cursor.columnCount) {
                if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                    fullPath = cursor.getString(i)
                    break
                }
            }
        } else {
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
            cursor.close()
            val projection = arrayOf(column)
            try {
                cursor = ctx.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    MediaStore.Images.Media._ID + " = ? ",
                    arrayOf(document_id),
                    null
                )
                if (cursor != null) {
                    cursor.moveToFirst()
                    fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                }
            } finally {
                if (cursor != null) cursor.close()
            }
        }
    }
    return fullPath
}