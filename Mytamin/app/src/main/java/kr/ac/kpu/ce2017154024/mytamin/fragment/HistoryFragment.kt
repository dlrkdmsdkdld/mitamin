package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.content.ContentUris
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.CareHistoryActivity
import kr.ac.kpu.ce2017154024.mytamin.activity.WeeklyActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding
import kr.ac.kpu.ce2017154024.mytamin.model.weeklyMental
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.primaryColor
import kr.ac.kpu.ce2017154024.mytamin.utils.PreferenceUtil
import kr.ac.kpu.ce2017154024.mytamin.utils.parseIntToMonth
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HistoryViewModel


class HistoryFragment : Fragment(),View.OnClickListener {
    private var mBinding : FragmentHistoryBinding?=null
    private val myviewmodel : HistoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryBinding.inflate(inflater,container,false)
        mBinding =binding
        mBinding?.historyMonthText?.text="${mBinding?.cvCalendar?.curMonth}월"

        Log.d(TAG,"HistoryFragment onCreateView")
        ///처음 년 ,  월 설정
        myviewmodel.setSelectMonthAndYear("${mBinding?.cvCalendar?.curYear}.${mBinding?.cvCalendar?.curMonth.parseIntToMonth()}")
        myviewmodel.getMonthMytaminAPI()


        myviewmodel.getrandomcare.observe(viewLifecycleOwner, Observer {
            mBinding?.historyRandomCare1?.text = it.careMsg1
            mBinding?.historyRandomCare2?.text = it.careMsg2
            mBinding?.historyRadomTake?.text = it.takeAt
        })
        myviewmodel.getmostFeel.observe(viewLifecycleOwner, Observer {
            var count =1
            it.forEach {
                when(count){
                    1->{
                        mBinding?.historyMost1Status?.text= it.feeling
                        mBinding?.historyMost1Count?.text = "${it.count}회"
                    }
                    2->{
                        mBinding?.historyMost2Status?.text = it.feeling
                        mBinding?.historyMost2Count?.text = "${it.count}회"
                    }
                    3->{
                        mBinding?.historyMost3Status?.text = it.feeling
                        mBinding?.historyMost3Count?.text = "${it.count}회"
                    }
                }
                count+=1

            }
        })
        myviewmodel.getweekMental.observe(viewLifecycleOwner, Observer {
            drawLineChart(it)
        })
        listener()
        mBinding?.cvCalendar?.setOnCalendarSelectListener(object:CalendarView.OnCalendarSelectListener{
            override fun onCalendarOutOfRange(calendar: Calendar?) {

            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                val intent = Intent(requireContext(),WeeklyActivity::class.java)


                val day = "${calendar?.year}.${calendar?.month.parseIntToMonth()}.${calendar?.day.parseIntToMonth()}"
                intent.putExtra("day",day)
                startActivity(intent)



                startActivity(intent)
                Log.d(TAG, " calendar?.day ${calendar?.day}  calendar month :${calendar?.month}   calendar year : ${calendar?.year}")
            }

        })


        return mBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var m = mBinding!!.cvCalendar!!.curMonth
        var y = mBinding!!.cvCalendar!!.curYear
        mBinding?.cvCalendar?.setOnMonthChangeListener { year, month ->
            mBinding?.historyMonthText?.text ="${month}월"
            myviewmodel.setSelectMonthAndYear("${year}.${month.parseIntToMonth()}")
            m=month
            y=year
            myviewmodel.getMonthMytaminAPI()
        }
        myviewmodel.getmonthmytamin.observe(viewLifecycleOwner, Observer {
            val mapdata: MutableMap<String, Calendar> = mutableMapOf()
            Log.d(TAG,"getmonthmytamin m ->$m y ->$y")

            it.forEach {
                when(it.mentalConditionCode){
                    1->{
                        mapdata.put(getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.Gray),"").toString(),
                            getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.Gray),"")
                        )
                    }
                    2->{
                        mapdata.put(getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.primary),"").toString(),
                            getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.primary),"")
                        )
                    }
                    3->{
                        mapdata.put(getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.LawnGreen),"").toString(),
                            getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.LawnGreen),"")
                        )
                    }
                    4->{
                        mapdata.put(getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.subBlue),"").toString(),
                            getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.subBlue),"")
                        )
                    }
                    5->{
                        mapdata.put(getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.layoutYellow),"").toString(),
                            getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.layoutYellow),"")
                        )
                    }
                    9->{
                        mapdata.put(getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.black),"").toString(),
                            getSchemeCalendar(y,m,it.day,ContextCompat.getColor(requireContext(), R.color.black),"")
                        )
                    }
                    else ->{}

                }
            }
            mBinding?.cvCalendar?.setSchemeDate(mapdata)

        })
    }
    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color:Int, text: String
    ): Calendar { //캘린더 리턴해주는 함수
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text

        return calendar
    }
    private fun drawLineChart(data: ArrayList<weeklyMental>){
        val lineChart = mBinding!!.historyChart

//        val legend = chart?.legend
//        legend?.textSize =
        var entries = ArrayList<Entry>()
        var datacount = 0
        data.forEach {
            entries.add(Entry(datacount.toFloat(),it.mentalConditionCode.toFloat()))
            datacount+=1
        }
        Log.d(TAG, " entries :$entries")
        lineChart.legend.apply {
            this.isEnabled = false
        }

        val xAxis = lineChart.xAxis
        xAxis.apply {
            position=XAxis.XAxisPosition.BOTTOM
            granularity=1f
            textSize=12f
            textColor=ContextCompat.getColor(requireContext(), R.color.Gray)
            spaceMax =0.1f
            spaceMin=0.1f
            setDrawAxisLine(true)
            setDrawGridLines(false)

            textColor = ContextCompat.getColor(requireContext(), R.color.subGray)
            axisLineColor=ContextCompat.getColor(requireContext(), R.color.LineColorshort)

        }

        lineChart.axisLeft.apply {
            //isEnabled=false
            axisLineWidth=2f
            this.setDrawLabels(false)
            setDrawGridLines(true)
            this.setDrawAxisLine(false)
            this.axisMaximum = 5f
            this.axisMinimum=0f
            granularity=1f
            this.gridColor=  ContextCompat.getColor(requireContext(), R.color.LineColorshort)

        }

        lineChart.axisRight.apply {
            isEnabled = false
        }
        var stringData = ArrayList<String>()
        data.forEach {
            stringData.add(it.dayOfWeek)
        }
        Log.d(TAG,"stringData :$stringData")
        xAxis.valueFormatter = IndexAxisValueFormatter(stringData)

       // xAxis.valueFormatter = MyXAxisFormatter(stringData)
        Log.d(TAG,"valueFormatter :${xAxis.valueFormatter}")
        val lineDataSet1 = LineDataSet(entries,"entries")
        val chartData = LineData()
        chartData.addDataSet(lineDataSet1)
        lineDataSet1.apply {
            circleRadius=4f
            lineWidth=2f
            circleHoleRadius=3f
            setDrawValues(false)
            setDrawCircles(true)
            setDrawCircleHole(true)
            circleHoleColor = ContextCompat.getColor(requireContext(), R.color.background_white)
            color=ContextCompat.getColor(requireContext(), R.color.primary)
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary))
            setDrawHighlightIndicators(false)
            setDrawHorizontalHighlightIndicator(false)
        }
        lineChart.apply {
            description.isEnabled=false

            this.data  =chartData
            extraBottomOffset=15f
            invalidate()
        }


        Log.d(TAG, "entries ->$entries ")




    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(TAG,"HistoryFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
            mBinding?.historyRefreshBtn ->{
                myviewmodel.randomCareAPI()
            }
            mBinding?.historyNextBtn ->{
                mBinding?.cvCalendar?.scrollToNext()
            }
            mBinding?.historyPreviousBtn ->{
                mBinding?.cvCalendar?.scrollToPre()
            }
            mBinding?.historyCareBtn->{
                val intent = Intent(requireContext(),CareHistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun listener(){

        mBinding?.historyRefreshBtn?.setOnClickListener(this)
        mBinding?.historyNextBtn?.setOnClickListener(this)
        mBinding?.historyPreviousBtn?.setOnClickListener(this)
        mBinding?.historyCareBtn?.setOnClickListener(this)
    }
}