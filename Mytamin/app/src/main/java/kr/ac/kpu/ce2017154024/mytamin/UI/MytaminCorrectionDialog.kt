package kr.ac.kpu.ce2017154024.mytamin.UI

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.dialog_mytamin_close.*
import kotlinx.android.synthetic.main.dialog_mytamin_correction.*
import kotlinx.android.synthetic.main.dialog_mytamin_correction.dialog_negative_btn
import kotlinx.android.synthetic.main.dialog_mytamin_correction.dialog_positive_btn
import kr.ac.kpu.ce2017154024.mytamin.R

class MytaminCorrectionDialog(context: Context,step:Int) : Dialog(context)  {
    private lateinit var onClickListener: OnClickedDialogBtn
    private var tmp = step
    fun setOnClickListener(listener: OnClickedDialogBtn)
    {
        onClickListener = listener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_mytamin_correction)
        if (tmp==3) {
            dialog_mytamin_title.text = "3. 하루 진단하기"
            dialog_mytamin_subtitle2.text = "하루 진단하기를 수정하시겠어요?"
        }else{
            dialog_mytamin_image.setImageResource(R.drawable.ic_dialog_step4)
            dialog_mytamin_title.text = "4. 칭찬 처방하기"
            dialog_mytamin_subtitle2.text = "칭찬 처방하기를 수정하시겠어요?"

        }

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
