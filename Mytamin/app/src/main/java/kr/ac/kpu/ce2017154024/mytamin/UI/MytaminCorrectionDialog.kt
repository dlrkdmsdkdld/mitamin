package kr.ac.kpu.ce2017154024.mytamin.UI

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_mytamin_correction.*
import kr.ac.kpu.ce2017154024.mytamin.R

class MytaminCorrectionDialog(context: Context,step:String) : Dialog(context)  {
    private lateinit var onClickListener: OnClickedDialogBtn
    private var tmp = step
    fun setOnClickListener(listener: OnClickedDialogBtn)
    {
        onClickListener = listener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_mytamin_correction)
        dialog_mytamin_subtitle.text = "${tmp}를 수정하시겠어요?"

        dialog_negative_btn.setOnClickListener {
            onClickListener.OnNegativeBtn()
        }
        dialog_positive_btn.setOnClickListener {
            onClickListener.OnPositiveBtn()

        }

    }
    interface OnClickedDialogBtn{
        fun OnNegativeBtn()
        fun OnPositiveBtn()
    }
}
