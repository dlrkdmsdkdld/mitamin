package kr.ac.kpu.ce2017154024.mytamin.UI

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kr.ac.kpu.ce2017154024.mytamin.databinding.SnackbarWishlistBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG

class WishlistSnackbar(view: View,wishlistid:Int) {

    companion object {

        fun make(view: View, wishlistid: Int) = WishlistSnackbar(view, wishlistid)
    }
    private val context = view.context
    private val inflater = LayoutInflater.from(context)
    private var cancel :Boolean = true
    private val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
        .addCallback(object :BaseTransientBottomBar.BaseCallback<Snackbar>(){
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                Log.d(TAG, " 스낵바 삭제됨")
                if (cancel){
                    InformationRetrofitManager.instance.deleteWishlist(wishlistid)
                }
            }
        })
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
    private val snackbarBinding :SnackbarWishlistBinding = SnackbarWishlistBinding.inflate(inflater)

    init {
        initView()
        initData()
    }
    fun show(){
        snackbar.show()
    }
    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }
    private fun initData(){
        snackbarBinding.snackbarBtn.setOnClickListener {
            cancel=false
        }
    }

}