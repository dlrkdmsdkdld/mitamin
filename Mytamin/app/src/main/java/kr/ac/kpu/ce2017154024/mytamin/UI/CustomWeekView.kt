package kr.ac.kpu.ce2017154024.mytamin.UI

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.WeekView

class CustomWeekView(context: Context?) :WeekView(context) {
    override fun onDrawSelected(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
        hasScheme: Boolean
    ): Boolean {
        return true
    }

    override fun onDrawScheme(canvas: Canvas?, calendar: Calendar?, x: Int) {

    }

    override fun onDrawText(canvas: Canvas?, calendar: Calendar?, x: Int, hasScheme: Boolean, isSelected: Boolean) {
        val cx = x + mItemWidth / 2
        calendar?.let {
            canvas?.let { cit->
                mCurDayTextPaint.color = Color.BLACK
                cit.drawText(it.day.toString(), cx.toFloat(), mTextBaseLine + y, mCurDayTextPaint)
            }


        }
    }
}