package kr.ac.kpu.ce2017154024.mytamin.UI

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_mytamin_close.*
import kotlinx.android.synthetic.main.dialog_mytamin_correction.*
import kotlinx.android.synthetic.main.dialog_mytamin_correction.dialog_negative_btn
import kotlinx.android.synthetic.main.dialog_mytamin_correction.dialog_positive_btn
import kr.ac.kpu.ce2017154024.mytamin.R

class MytaminCloseDialog(context: Context, title:String,subtitle:String) : Dialog(context){
    private lateinit var onClickListener: MytaminCorrectionDialog.OnClickedDialogBtn
    private var title = title
    private var subtitle = subtitle
    fun setOnClickListener(listener: MytaminCorrectionDialog.OnClickedDialogBtn)
    {
        onClickListener = listener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_mytamin_close)
        dialog_close_title.text =title
        dialog_close_subtitle.text = subtitle
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