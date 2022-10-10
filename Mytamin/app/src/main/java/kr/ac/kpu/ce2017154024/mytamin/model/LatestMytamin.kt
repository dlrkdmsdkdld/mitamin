package kr.ac.kpu.ce2017154024.mytamin.model

import java.io.Serializable

data class LatestMytamin(val takeAt:String="",val canEditReport:Boolean,val canEditCare:Boolean,val reportId:Int=0,val mentalConditionCode:Int=0
    ,val feelingTag:String ="", val mentalConditionMsg:String="" , val todayReport:String="" ,val careId:Int=0,val  careMsg1:String="",val careMsg2:String=""
): Serializable {
}