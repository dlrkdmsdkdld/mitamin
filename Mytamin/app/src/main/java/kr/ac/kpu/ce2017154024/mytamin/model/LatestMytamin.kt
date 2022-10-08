package kr.ac.kpu.ce2017154024.mytamin.model

data class LatestMytamin(val takeAt:String="",val canEditReport:Boolean,val canEditCare:Boolean,val mentalConditionCode:Int=0
    ,val feelingTag:String ="", val mentalConditionMsg:String="" , val todayReport:String="" ,val  careMsg1:String="",val careMsg2:String=""
) {
}