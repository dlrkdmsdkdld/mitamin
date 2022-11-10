package kr.ac.kpu.ce2017154024.mytamin.fragment.information

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_mytamin_correction.*
import kotlinx.android.synthetic.main.dialog_quit.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.MytaminCorrectionDialog
import kr.ac.kpu.ce2017154024.mytamin.databinding.DialogQuitBinding
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHistoryBinding


class QuitDialog (context: Context): Dialog(context) {
    private lateinit var onClickListener: OnClickedDialogBtn
    fun setOnClickListener(listener: OnClickedDialogBtn)
    {
        onClickListener = listener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_quit)
        dialog_negative_btns.setOnClickListener {
            onClickListener.OnNegativeBtn()
        }
        dialog_positive_btns.setOnClickListener {
            onClickListener.OnPositiveBtn()

        }

    }
    interface OnClickedDialogBtn{
        fun OnNegativeBtn()
        fun OnPositiveBtn()
    }
}