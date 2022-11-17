package kr.ac.kpu.ce2017154024.mytamin.UI

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import kr.ac.kpu.ce2017154024.mytamin.R
import kotlin.properties.Delegates

class CustomMonthView(context: Context) : MonthView(context) {
    private val mPointPaint = Paint()
    private val mycontext = context
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

//        if (!calendar.isCurrentDay) {
//            mSelectedPaint.style = Paint.Style.STROKE
//            mSelectedPaint.strokeWidth = 2f
//            mSelectedPaint.color = ContextCompat.getColor(context, R.color.black)
//            val rectF = RectF(
//                x.toFloat(),
//                y.toFloat(),
//                (x + mItemWidth).toFloat(),
//                (y + mItemHeight).toFloat()
//            )
//
//            canvas.drawRoundRect(rectF, 16f, 16f, mSelectedPaint)
//
//
//            println("선택됨")
//        }
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
    val r = mycontext.resources
    val codeoneimage = r.getDrawable(R.drawable.ic_calendar_1,null).toBitmap()
    val codetwoimage =r.getDrawable(R.drawable.ic_calendar_2,null).toBitmap()
    val codethreeimage = r.getDrawable(R.drawable.ic_calendar_3,null).toBitmap()
    val codefourimage = r.getDrawable(R.drawable.ic_calendar_4,null).toBitmap()
    val codefiveimage = r.getDrawable(R.drawable.ic_calendar_5,null).toBitmap()
    var done:Boolean = false
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
        val cx = x + (mItemWidth*14/48) -8
        val cy: Int = y + mItemHeight /3 +20
        when (mSchemePaint.color) {
                -2236963 ->{
                    canvas.drawBitmap(codeoneimage, cx.toFloat(), cy.toFloat(), null)
                }
                -32937 -> {
                    //위치 바꾸기
                    canvas.drawBitmap(codetwoimage, cx.toFloat(), cy.toFloat(), null)

                }
                -2353 -> {
                    canvas.drawBitmap(codefiveimage, cx.toFloat(), cy.toFloat(), null)
                }
                -8586240 -> {
                    canvas.drawBitmap(codethreeimage, cx.toFloat(), cy.toFloat(), null)
                }
                -12606465 ->{
                    canvas.drawBitmap(codefourimage, cx.toFloat(), cy.toFloat(), null)

                }
            else ->{
//                        val radius: Int = Math.min(mItemWidth, mItemHeight) / 5 * 2
//                      canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mSchemePaint)
            }
        }



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
        val font =resources.getFont(R.font.appsubfont_m)
        val cx = x + mItemWidth / 2
        calendar?.let {
            canvas?.let { cit ->
                if (it.isCurrentDay) {
//                    val rectF = RectF(x.toFloat(), y.toFloat(), (x + mItemWidth).toFloat(), (y + mItemHeight).toFloat())
//                    cit.drawRoundRect(rectF, 16f, 16f, mCurrentDayPaint)
//                    if (hasScheme) {
//                        //  绘制当天的标记点
//                        cit.drawCircle(
//                            (x + mItemWidth / 2).toFloat(),
//                            (y + mItemHeight - 13).toFloat(),
//                            mPointRadius,
//                            mPointPaint
//                        )
//                    }
                }
              //  mSchemeTextPaint.color = Color.BLACK

                mCurDayTextPaint.color = Color.RED
                mSelectTextPaint.color = Color.BLACK
                mCurMonthTextPaint.color = Color.BLACK
                mSchemeTextPaint.color =  Color.BLACK
                val drawPaint =  if (hasScheme && !it.isCurrentDay) {
                    if (it.isCurrentMonth) mSchemeTextPaint else mOtherMonthTextPaint
                }
                else if(it.isCurrentDay){
                    mCurDayTextPaint
                }
                else {
                     if (it.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
                }

                drawPaint.isAntiAlias=true
                drawPaint.typeface = font
               // drawPaint.setTypeface(font)
                //val mTextBaseLine = mTextBaseLine /3*2
                if (it.isCurrentDay){
                    drawPaint.color=Color.RED
                    cit.drawText(it.day.toString(), cx.toFloat(), mTextBaseLine/2+ y.toFloat(), drawPaint)
                }
                else if (it.isCurrentMonth && !hasScheme){
                    drawPaint.color = Color.LTGRAY
                    cit.drawText(it.day.toString(), cx.toFloat(),mTextBaseLine/2+ y.toFloat(), drawPaint)
                }

                else if (it.isCurrentMonth && hasScheme){
                    cit.drawText(it.day.toString(), cx.toFloat(), mTextBaseLine/2+y.toFloat(), drawPaint)
                }
               // cit.drawText(it.day.toString(), x.toFloat(), mTextBaseLine + y, drawPaint)
            }
        }
    }
}