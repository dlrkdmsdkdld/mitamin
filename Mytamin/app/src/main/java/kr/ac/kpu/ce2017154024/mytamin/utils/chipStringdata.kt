package kr.ac.kpu.ce2017154024.mytamin.utils

import android.util.Log
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

object chipStringdata {

    val verysad= arrayOf<String>("지치는","실망한","무서운","혼란스러운","화나는","자책하는","상처받은","실망한","역겨운","끔찍한","수치스러운","미어지는","우울한","서러운","죄스러운")
    val sad = arrayOf<String>("피곤한","기운없는","실망한","걱정되는","긴장되는","불편한","못마땅한","쓸쓸한","우울한","서운한","미안한","어이없는","부끄러운","지겨운","답답한")
    val soso = arrayOf<String>("평온한","무념무상인","무난한","덤덤한","지루한","심심한","권태로운","귀찮은","무기력한","쓸쓸한","외로운","허전한","부러운")
    val good = arrayOf("즐거운","행복한","기쁜","감사한","편안한","평화로운","아늑한","따뜻한","만족한","뿌듯한","설레는","개운한","홀가분한")
    val verygood = arrayOf("신나는","즐거운","찬란한","활기찬","행복한","감사한","감동적인","기대되는","만족한","뿌듯한","설레는","상쾌한","후련한")
    fun lenghtChipState(){
        Log.d(TAG,"verysad:${verysad.size}, sad:${sad.size} , soso:${soso.size} , good:${good.size} , verygood:${verygood.size}   ")
    }
    var previousChildCount=0
}