package kr.ac.kpu.ce2017154024.mytamin.model


data class randomCare(val careMsg1:String , val careMsg2:String,val takeAt:String)
data class feeling(val feeling:String , val count:Int)

data class weeklyMental(val dayOfWeek:String , val mentalConditionCode:Int)
data class monthMytamin(val day:Int , val mentalConditionCode:Int)

data class careMytamin(val careMsg1:String , val careMsg2:String ,val careCategory:String ,val takeAt:String)
data class monthCareMytamin(val time:String,val arrayCareMytamin:ArrayList<careMytamin>)
data class careCategoryCodeList(val careCategoryCodeList:ArrayList<Int>)
