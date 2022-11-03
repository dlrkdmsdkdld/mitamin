package kr.ac.kpu.ce2017154024.mytamin.UI.RecyclerView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import kr.ac.kpu.ce2017154024.mytamin.R
import kotlin.properties.Delegates

class CustomMonthView(context: Context) : MonthView(context) {
    private val mPointPaint = Paint()

    /**
     * 今天的背景色
     */
    private val mCurrentDayPaint = Paint()

    /**
     * 圆点半径
     */
    private var mPointRadius = 3f
    var mRadius by Delegates.notNull<Int>()

    init {
        mCurrentDayPaint.isAntiAlias = true
        mCurrentDayPaint.style = Paint.Style.STROKE
        mCurrentDayPaint.color = ContextCompat.getColor(context, R.color.purple_700)
        mPointPaint.isAntiAlias = true
        mPointPaint.style = Paint.Style.FILL
        mPointPaint.textAlign = Paint.Align.CENTER
        mPointPaint.color = ContextCompat.getColor(context, R.color.purple_200)
    }

    /**
     * draw select calendar
     *
     * @param canvas    canvas
     * @param calendar  select calendar
     * @param x         calendar item x start point
     * @param y         calendar item y start point
     * @param hasScheme is calendar has scheme?
     * @return if return true will call onDrawScheme again
     *///캘린더뷰 셀 선택햇을때 실행되는함수
    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        if (!calendar.isCurrentDay) {
            mSelectedPaint.style = Paint.Style.STROKE
            mSelectedPaint.strokeWidth = 2f
            mSelectedPaint.color = ContextCompat.getColor(context, R.color.black)
            val rectF = RectF(
                x.toFloat(),
                y.toFloat(),
                (x + mItemWidth).toFloat(),
                (y + mItemHeight).toFloat()
            )

            canvas.drawRoundRect(rectF, 16f, 16f, mSelectedPaint)


            println("선택됨")
        }
        return true
    }

    /**
     * draw scheme if calendar has scheme
     *
     * @param canvas   canvas
     * @param calendar calendar has scheme
     * @param x        calendar item x start point
     * @param y        calendar item y start point
     */ // 특정날짜에 색칠해줄때 자동실행되는함수
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
        val cx: Int = x + mItemWidth / 2
        val cy: Int = y + mItemHeight / 2
        val radius: Int = Math.min(mItemWidth, mItemHeight) / 5 * 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mSchemePaint)
        // println("$x $y scheme 실행됨")

    }

    override fun onPreviewHook() {
        super.onPreviewHook()
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2
    }

    /**
     * draw text
     *
     * @param canvas     canvas
     * @param calendar   calendar
     * @param x          calendar item x start point
     * @param y          calendar item y start point
     * @param hasScheme  is calendar has scheme?
     * @param isSelected is calendar selected?
     */

    override fun onDrawText(
        canvas: Canvas?,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val cx = x + mItemWidth / 2
        calendar?.let {
            canvas?.let { cit ->
                if (it.isCurrentDay) {
//                    val rectF = RectF(x.toFloat(), y.toFloat(), (x + mItemWidth).toFloat(), (y + mItemHeight).toFloat())
//                    cit.drawRoundRect(rectF, 16f, 16f, mCurrentDayPaint)
                    if (hasScheme) {
                        //  绘制当天的标记点
                        cit.drawCircle(
                            (x + mItemWidth / 2).toFloat(),
                            (y + mItemHeight - 13).toFloat(),
                            mPointRadius,
                            mPointPaint
                        )
                    }
                }
                val drawPaint = if (isSelected && !it.isCurrentDay) {
                    mSelectTextPaint
                } else if (hasScheme) {
                    if (it.isCurrentMonth) mSchemeTextPaint else mOtherMonthTextPaint
                } else {
                    if (it.isCurrentDay) mCurDayTextPaint else if (it.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
                }
                cit.drawText(it.day.toString(), cx.toFloat(), mTextBaseLine + y, drawPaint)
            }
        }
    }
}