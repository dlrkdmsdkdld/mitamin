package kr.ac.kpu.ce2017154024.mytamin.model

import java.io.Serializable

data class ReportData(val mentalConditionCode:Int,val tag1:String,val tag2:String="",val tag3:String="",val todayReport:String ){}
data class Status (val breathIsDone:Boolean,val senseIsDone:Boolean,val reportIsDone:Boolean,val careIsDone:Boolean):Serializable{

}
