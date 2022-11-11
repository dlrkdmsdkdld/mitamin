package kr.ac.kpu.ce2017154024.mytamin.UI

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.WeekView
import kr.ac.kpu.ce2017154024.mytamin.R


class CustomWeekView(context: Context) :WeekView(context) {
    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        hasScheme: Boolean
    ): Boolean {


        mSelectedPaint.style = Paint.Style.FILL
        mSelectedPaint.strokeWidth = 1f
        mSelectedPaint.color = ContextCompat.getColor(context, R.color.primary)
//            val rectF = RectF(
//                x.toFloat(),
//                y.toFloat(),
//                (x + mItemWidth).toFloat(),
//                (y + mItemHeight).toFloat()
//            )
//            canvas.drawRoundRect(rectF, 16f, 16f, mSelectedPaint)
        val cx : Int =  x + mItemWidth / 2
        val cy : Float =  y
          //  val radius : Int=Math.min(mItemWidth, mItemHeight) / 5 * 2
        val radius=dp2px(resources,11)
        canvas.drawCircle(cx.toFloat(), mTextBaseLine/3, radius.toFloat(), mSelectedPaint)

        println("선택됨")

       // cit.drawText(it.day.toString(), cx.toFloat(), mTextBaseLine/2 , mCurDayTextPaint)


        return true
    }
    fun px2dp(resource: Resources, px: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            px,
            resource.getDisplayMetrics()
        )
    }
    fun dp2px(resource: Resources, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resource.displayMetrics
        ).toInt()
    }
    val r = context.resources
    val codeoneimage = r.getDrawable(R.drawable.ic_calendar_1,null).toBitmap()
    val codetwoimage =r.getDrawable(R.drawable.ic_calendar_2,null).toBitmap()
    val codethreeimage = r.getDrawable(R.drawable.ic_calendar_3,null).toBitmap()
    val codefourimage = r.getDrawable(R.drawable.ic_calendar_4,null).toBitmap()
    val codefiveimage = r.getDrawable(R.drawable.ic_calendar_5,null).toBitmap()
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar?, x: Int) {
        val cx: Int = x + mItemWidth/3 -10
        val cy = y+(mItemHeight/2)
        when (mSchemePaint.color) {
            -2236963 ->{
                canvas.drawBitmap(codeoneimage, cx.toFloat(), cy, null)
            }
            -32937 -> {
                //위치 바꾸기
                canvas.drawBitmap(codetwoimage, cx.toFloat(), cy, null)

            }
            -2353 -> {
                canvas.drawBitmap(codefiveimage, cx.toFloat(), cy, null)
            }
            -8586240 -> {
                canvas.drawBitmap(codethreeimage, cx.toFloat(),cy, null)
            }
            -12606465 ->{
                canvas.drawBitmap(codefourimage, cx.toFloat(), cy, null)

            }
            else ->{
//                        val radius: Int = Math.min(mItemWidth, mItemHeight) / 5 * 2
//                      canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mSchemePaint)
            }
        }
    }

    override fun onDrawText(canvas: Canvas?, calendar: Calendar?, x: Int, hasScheme: Boolean, isSelected: Boolean) {
        val cx = x + mItemWidth / 2
        calendar?.let {
            canvas?.let { cit->
                mCurDayTextPaint.color = Color.BLACK
                cit.drawText(it.day.toString(), cx.toFloat(), mTextBaseLine/2 , mCurDayTextPaint)
            }


        }
    }
}