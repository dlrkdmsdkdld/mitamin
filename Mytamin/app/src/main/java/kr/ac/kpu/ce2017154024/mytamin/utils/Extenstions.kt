package kr.ac.kpu.ce2017154024.mytamin.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.widget.EditText
import com.haibin.calendarview.Calendar
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING.introduce_first_talk_one
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING.introduce_first_talk_two
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.Createdyear
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

 class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
    override fun contentType(): MediaType = "image/jpeg".toMediaType()
    override fun writeTo(sink: BufferedSink) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, sink.outputStream())
    }
}
 fun getSchemeCalendar(year: Int, month: Int, day: Int, color:Int, text: String
): Calendar { //캘린더 리턴해주는 함수
    val calendar = Calendar()
    calendar.year = year
    calendar.month = month
    calendar.day = day
    calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
    calendar.scheme = text

    return calendar
}
fun dp2px(resource: Resources, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resource.displayMetrics
    ).toInt()
}
 fun changeDP(value : Int,context: Context) : Int{
    var displayMetrics = context.resources.displayMetrics
    var dp = Math.round(value * displayMetrics.density)
    return dp
}

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
    Log.d(TAG, " minute :$minute")
    Log.d(TAG, " parseminute :$parseminute")
    return parseminute
}
fun Int?.parseIntToMonth():String{
    val month:Int?=this
    var parseminute = month.toString()
    if (month!!<10){
        parseminute="0$month"
    }
    return parseminute
}

fun parseTimeToHome():String{
    val tmp = System.currentTimeMillis()
    val home_dateFormat= SimpleDateFormat("MM.dd.E", Locale("En", "KR"))
    val parseData=home_dateFormat.format(tmp)
    Log.d(TAG,"현재 날짜 ${parseData}")
    return parseData
}
fun parseBottomCalendarYear():ArrayList<Int>{
    val tmp = System.currentTimeMillis()
    val YYYYFormat= SimpleDateFormat("YYYY", Locale("En", "KR"))
    val parseYYYY=YYYYFormat.format(tmp)
    val arrayYear =ArrayList<Int>()
    val tmpL =parseYYYY.toInt() -Createdyear
    for (i in 0..tmpL){
        arrayYear.add(Createdyear + i)
    }

    return arrayYear
}
fun parseTimeToYear():Int{
    val tmp = System.currentTimeMillis()
    val dateFormat= SimpleDateFormat("YYYY", Locale("En", "KR"))
    val parseYear=dateFormat.format(tmp)
    Log.d(TAG,"현재 날짜 ${parseYear}")
    return parseYear.toInt()
}
fun parseTimeToMonth():Int{
    val tmp = System.currentTimeMillis()
    val dateFormat= SimpleDateFormat("MM", Locale("En", "KR"))
    val parseMonth=dateFormat.format(tmp)
    Log.d(TAG,"현재 날짜 ${parseMonth}")
    return parseMonth.toInt()
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