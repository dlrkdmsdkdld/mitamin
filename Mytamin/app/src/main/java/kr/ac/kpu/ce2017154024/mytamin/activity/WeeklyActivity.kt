package kr.ac.kpu.ce2017154024.mytamin.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.LoadingDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.ActivityWeeklyBinding
import kr.ac.kpu.ce2017154024.mytamin.model.LatestMytamin
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.model.dayMytamin
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HistoryRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import kr.ac.kpu.ce2017154024.mytamin.utils.getSchemeCalendar
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToMonth

class WeeklyActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mbinding : ActivityWeeklyBinding
    private  var ArraydayMytamin =ArrayList<dayMytamin>()
    private var selectmytaminId :Int= 100000000
    private lateinit var customProgressDialog: Dialog
    private lateinit var canEditMytamin :dayMytamin
    private lateinit var latestmytamin :LatestMytamin

    private lateinit var status: Status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityWeeklyBinding.inflate(layoutInflater)
        setContentView(mbinding.root)
        mbinding?.weeklyBackBtn.setOnClickListener(this)
        mbinding?.weeklyTrashBtn.setOnClickListener(this)
        customProgressDialog= LoadingDialog(this)

        mbinding?.yesMytaminStep3Btn.setOnClickListener(this)
        mbinding?.yesMytaminStep4Btn.setOnClickListener(this)


        val day = intent.getStringExtra("day")
        day?.let {
            var dayDate = it.split(".")
            Log.d(TAG,"dayDate ->$dayDate")
            mbinding?.weeklyCalendar.scrollToCalendar(dayDate[0].toInt(),dayDate[1].toInt(),dayDate[2].toInt())
            setSchema(null,it, dayDate[1].toInt() ,dayDate[0].toInt())
            mbinding?.weeklyTimeText.text = "${dayDate[0].toInt()}년 ${dayDate[1].toInt()}월"
        }
        selectCalend()
        mbinding?.weeklyCalendar.setOnWeekChangeListener(object :CalendarView.OnWeekChangeListener{
            override fun onWeekChange(weekCalendars: MutableList<Calendar>?) {
                val nowWeek = weekCalendars?.get(1)
                mbinding?.weeklyTimeText.text = "${nowWeek?.year}년 ${nowWeek?.month}월"
                weekCalendars?.let { setSchema(it,"${nowWeek?.year}.${nowWeek?.month.parseIntToMonth()}.${nowWeek?.day.parseIntToMonth()}",nowWeek!!.month,nowWeek!!.year) }

            }

        })




    }
    private fun setSchema(date:MutableList<Calendar>?,day:String,mont:Int,Year:Int){
        HistoryRetrofitManager.instance.getWeekMytamin(day){responseStatus, ArraydayMytamins ->
            ArraydayMytamins?.let {
                this.ArraydayMytamin = ArraydayMytamins
//                val y = mbinding?.weeklyCalendar.curYear
//                val m = mbinding?.weeklyCalendar.curMonth
                var count = 0
                val mapdata: MutableMap<String, Calendar> = mutableMapOf()
                ArraydayMytamins.forEach {
                    val day = it.day.toInt()
                    var y = Year
                    var m = mont
                    date?.let {
                         y =  date.get(count).year
                         m = date.get(count).month
                    }

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
                    count+=1
                }
                mbinding?.weeklyCalendar.setSchemeDate(mapdata)
            }
        }
    }
    private fun selectCalend(){
        mbinding?.weeklyCalendar.setOnCalendarSelectListener(object :CalendarView.OnCalendarSelectListener{
            override fun onCalendarOutOfRange(calendar: Calendar?) {

            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                ArraydayMytamin?.let {
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

            }

        })
    }
    private fun parseLatesmytamin(canEditMytamin: dayMytamin) {
        canEditMytamin?.report?.let {

        }
        if (canEditMytamin?.report!=null &&canEditMytamin?.care!=null){
            status = Status(false,false,true,true )
            latestmytamin =LatestMytamin(takeAt = "",canEditReport=true,canEditCare=false,reportId=canEditMytamin.report!!.reportId
                , mentalConditionCode = canEditMytamin.report!!.mentalConditionCode, feelingTag = canEditMytamin.report!!.feelingTag, mentalConditionMsg = canEditMytamin.report!!.mentalCondition,
                todayReport = canEditMytamin.report!!.todayReport, careId = canEditMytamin.care!!.careId, careCategory = canEditMytamin.care!!.careCategory, careMsg1=canEditMytamin.care!!.careMsg1, careMsg2 = canEditMytamin.care!!.careMsg2
            )
        }else if (canEditMytamin?.report==null&&canEditMytamin?.care!=null){
            status = Status(false,false,false,true )
            latestmytamin =LatestMytamin(takeAt = "",canEditReport=true,canEditCare=false,reportId=0
                , mentalConditionCode =0, feelingTag ="", mentalConditionMsg = "",
                todayReport = "", careId = canEditMytamin.care!!.careId, careCategory = canEditMytamin.care!!.careMsg1, careMsg1=canEditMytamin.care!!.careMsg1, careMsg2 = canEditMytamin.care!!.careMsg2
            )

        }else if (canEditMytamin?.report!=null&&canEditMytamin?.care==null) {
            status = Status(false,false,true,false )
            latestmytamin =LatestMytamin(takeAt = "",canEditReport=true,canEditCare=false,reportId=canEditMytamin.report!!.reportId
                , mentalConditionCode = canEditMytamin.report!!.mentalConditionCode, feelingTag = canEditMytamin.report!!.feelingTag, mentalConditionMsg = canEditMytamin.report!!.mentalCondition,
                todayReport = canEditMytamin.report!!.todayReport, careId = 0, careCategory ="", careMsg1="", careMsg2 = ""
            )
        }




    }
    private fun setData(data:dayMytamin){
        selectmytaminId= data.mytaminId ?: 100000000
        data.takeAt?.let { mbinding?.weeklyYesDate.text = it }
        if (data.report == null){
            mbinding?.yesMytaminFeelingTag.text = null
            mbinding?.yesMytaminMentalConditionMsg.text =null
            mbinding?.yesmytaminTodayReport.text=null
            mbinding?.yesMytaminImage.setImageResource(R.drawable.ic_x_box)
            mbinding?.yesMytaminStep3Btn.visibility=View.GONE
        }
        data.report?.let {
            if(!it.canEdit) mbinding?.yesMytaminStep3Btn.visibility=View.GONE
            else{ mbinding?.yesMytaminStep3Btn.visibility=View.VISIBLE
                canEditMytamin =data
                parseLatesmytamin(canEditMytamin)
            }
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
        if (data.care == null){
            mbinding?.yesMytaminStep4Btn.visibility = View.GONE
            mbinding?.yesmytaminCareMsg1.text = ""
            mbinding?.yesmytaminCareMsg2.text = ""
        }
        data.care?.let {
            if(!it.canEdit) mbinding?.yesMytaminStep4Btn.visibility=View.GONE
            else mbinding?.yesMytaminStep4Btn.visibility=View.VISIBLE
            mbinding?.yesmytaminCareMsg1.text = it.careMsg1
            mbinding?.yesmytaminCareMsg2.text = it.careMsg2
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            mbinding?.weeklyBackBtn ->{
                finish()
                }
            mbinding?.weeklyTrashBtn -> {
                if (selectmytaminId != 100000000) {
                    customProgressDialog.show()
                    HistoryRetrofitManager.instance.deleteMytamin(selectmytaminId) { responseStatus, i ->
                        customProgressDialog.dismiss()
                        if (responseStatus != RESPONSE_STATUS.OKAY) {
                            Toast.makeText(this, "마이타민 삭제 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
            mbinding?.yesMytaminStep3Btn ->{
                gotodayMytaminActivity(3)
            }
            mbinding?.yesMytaminStep4Btn ->{
                gotodayMytaminActivity(6)
            }
        }
    }
    private fun gotodayMytaminActivity(step:Int){
        val intent= Intent(this,todayMytaminActivity::class.java)
        intent.putExtra("step",step)
        val bundle = Bundle()
        bundle.putSerializable("statusData",status)
        intent.putExtra("bundle",bundle)
        val bundleL = Bundle()
        bundleL.putSerializable("LatestMytamin",latestmytamin)
        intent.putExtra("bundleL",bundleL)
        startActivity(intent)
    }
}