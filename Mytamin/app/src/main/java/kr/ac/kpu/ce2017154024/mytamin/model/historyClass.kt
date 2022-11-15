package kr.ac.kpu.ce2017154024.mytamin.model


data class randomCare(val careMsg1:String , val careMsg2:String,val takeAt:String)
data class feeling(val feeling:String , val count:Int)

data class weeklyMental(val dayOfWeek:String , val mentalConditionCode:Int)
data class monthMytamin(val day:Int , val mentalConditionCode:Int)

data class careMytamin(val careMsg1:String , val careMsg2:String ,val careCategory:String ,val takeAt:String)
data class monthCareMytamin(val time:String,val arrayCareMytamin:ArrayList<careMytamin>)
data class careCategoryCodeList(val careCategoryCodeList:ArrayList<Int>)

data class dayMytaminReport(val reportId:Int , val canEdit:Boolean, val mentalConditionCode :Int ,val   mentalCondition:String, val feelingTag:String,val todayReport:String  )
data class dayMytaminCare(val careId:Int, val canEdit: Boolean,val careCategory: String,val careMsg1: String,val careMsg2: String)
data class dayMytamin(val day:String, val mytaminId:Int?, val takeAt: String?,
                      var report:dayMytaminReport?, var care:dayMytaminCare?)

data class mytaminAlarm(val isOn:Boolean,val whentime:String?)
data class mydayAlarm(val isOn:Boolean,val whentime:String?)

data class mytaminAlarmTime(val mytaminHour:String,val mytaminMin:String)





