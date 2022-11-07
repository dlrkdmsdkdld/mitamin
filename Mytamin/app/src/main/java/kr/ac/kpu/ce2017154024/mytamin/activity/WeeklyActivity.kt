package kr.ac.kpu.ce2017154024.mytamin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityWeeklyBinding
import kr.ac.kpu.ce2017154024.mytamin.model.dayMytamin
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.getSchemeCalendar

class WeeklyActivity : AppCompatActivity() {
    private lateinit var mbinding : ActivityWeeklyBinding
    private lateinit var ArraydayMytamin :ArrayList<dayMytamin>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityWeeklyBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        mbinding?.weeklyCalendar.apply {

            this.setSelectStartCalendar(2022,11,1)
        }
        //mbinding?.weeklyCalendarLayout2.setDate(1570668133353L)
//        mbinding?.weeklyCalendar.curMonth =1
//        mbinding?.weeklyCalendar.set
        val day = intent.getStringExtra("day")
        day?.let {
            HistoryRetrofitManager.instance.getWeekMytamin(day){responseStatus, ArraydayMytamins ->
                ArraydayMytamins?.let {
                    this.ArraydayMytamin = ArraydayMytamins
                    val y = mbinding?.weeklyCalendar.curYear
                    val m = mbinding?.weeklyCalendar.curMonth
                    val mapdata: MutableMap<String, Calendar> = mutableMapOf()
                    ArraydayMytamins.forEach {
                            val day = it.day.toInt()
                            it.report?.let {
                                when(it.mentalConditionCode){
                                    1->{
                                        mapdata.put(
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.Gray),"").toString(),
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.Gray),"")
                                        )
                                    }
                                    2->{
                                        mapdata.put(
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.primary),"").toString(),
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.primary),"")
                                        )
                                    }
                                    3->{
                                        mapdata.put(
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.LawnGreen),"").toString(),
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.LawnGreen),"")
                                        )
                                    }
                                    4->{
                                        mapdata.put(
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.subBlue),"").toString(),
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.subBlue),"")
                                        )
                                    }
                                    5->{
                                        mapdata.put(
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.layoutYellow),"").toString(),
                                            getSchemeCalendar(y,m,day,
                                                ContextCompat.getColor(this, R.color.layoutYellow),"")
                                        )
                                    }
                                    else ->{}


                                }
                        }
                    }
                    mbinding?.weeklyCalendar.setSchemeDate(mapdata)
                }
            }
        }
        selectCalend()



    }
    private fun retrunShema(ArraydayMytamins:ArrayList<dayMytamin>,code:Int,y:Int,m:Int,day:Int){
        ArraydayMytamins.forEach {
            it.takeAt?.let {
                it[0-1]
            }
        }
        val mapdata: MutableMap<String, Calendar> = mutableMapOf()
        when(code){
            1->{
                mapdata.put(
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.Gray),"").toString(),
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.Gray),"")
                )
            }
            2->{
                mapdata.put(
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.primary),"").toString(),
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.primary),"")
                )
            }
            3->{
                mapdata.put(
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.LawnGreen),"").toString(),
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.LawnGreen),"")
                )
            }
            4->{
                mapdata.put(
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.subBlue),"").toString(),
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.subBlue),"")
                )
            }
            5->{
                mapdata.put(
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.layoutYellow),"").toString(),
                    getSchemeCalendar(y,m,day,
                        ContextCompat.getColor(this, R.color.layoutYellow),"")
                )
            }
            else ->{}


        }
    }
    private fun selectCalend(){
        mbinding?.weeklyCalendar.setOnCalendarSelectListener(object :CalendarView.OnCalendarSelectListener{
            override fun onCalendarOutOfRange(calendar: Calendar?) {

            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                ArraydayMytamin.forEach {
                    if (it.day == calendar?.day.toString()){
                        if (it.report == null && it.care == null){
                            mbinding?.weeklyNoLayout.visibility = View.VISIBLE
                            mbinding?.weeklyYesLayout.visibility = View.INVISIBLE
                        }else{
                            mbinding?.weeklyNoLayout.visibility = View.INVISIBLE
                            mbinding?.weeklyYesLayout.visibility = View.VISIBLE
                            setData(it)
                        }
                    }
                }
            }

        })
    }
    private fun setData(data:dayMytamin){
        data.takeAt?.let { mbinding?.weeklyYesDate.text = it }
        data.report?.let {
            mbinding?.yesMytaminFeelingTag.text = it.feelingTag
            mbinding?.yesMytaminMentalConditionMsg.text = it.mentalCondition
            mbinding?.yesmytaminTodayReport.text = it.todayReport
            when(it.mentalConditionCode){
                1 ->{mbinding?.yesMytaminImage.setImageResource(R.drawable.ic_so_bad)}
                2 ->{mbinding?.yesMytaminImage.setImageResource(R.drawable.ic_bad)}
                3 ->{mbinding?.yesMytaminImage.setImageResource(R.drawable.ic_so_so)}
                4 ->{mbinding?.yesMytaminImage.setImageResource(R.drawable.ic_good)}
                5 ->{mbinding?.yesMytaminImage.setImageResource(R.drawable.ic_so_good)}
            }

        }
        data.care?.let {
            mbinding?.yesmytaminCareMsg1.text = it.careMsg1
            mbinding?.yesmytaminCareMsg2.text = it.careMsg2
        }
    }
}