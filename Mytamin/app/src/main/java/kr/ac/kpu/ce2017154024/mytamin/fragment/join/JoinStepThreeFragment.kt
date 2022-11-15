package kr.ac.kpu.ce2017154024.mytamin.fragment.join

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_join_step_three.*
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.activity.joinActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentJoinStepThreeBinding
import kr.ac.kpu.ce2017154024.mytamin.retrofit.join.JoinRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant
import kr.ac.kpu.ce2017154024.mytamin.utils.JOINSTRING
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import kr.ac.kpu.ce2017154024.mytamin.viewModel.joinViewModel
import java.util.concurrent.TimeUnit

class joinStepThreeFragment : Fragment() {

    private var mBinding: FragmentJoinStepThreeBinding?=null
    private var nameCompositeDisposable = CompositeDisposable()
    private val joinViewModel by activityViewModels<joinViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentJoinStepThreeBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(Constant.TAG,"joinStepThreeFragment onCreateView")

        val edittextChangeObservable = mBinding?.joinStepThreeNameText?.textChanges()

//        val checkNameTextSubscription :Disposable=
//            edittextChangeObservable!!.debounce(500,TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .subscribeBy(
//                    onNext ={
//                        var tmp = it.toString().replace(" ","")
//                        var inputName = it.toString().trim()
//                        (it.toString()=="")
//                        if (it.toString()=="" ){tmp="1"}
//                        if (it.toString()==tmp && it.toString()!="" ){
//                            Handler(Looper.getMainLooper()).post(Runnable {
//                                mBinding?.joinStepThreeNameLayout?.helperText = "사용 가능한 닉네임이에요!"
//                            CheckNameAPICall(it.toString())
//                            })
//                        }else{
//                            Handler(Looper.getMainLooper()).post(Runnable {
//                                mBinding?.joinStepThreeNameLayout?.helperText= "사용할 수 없는 닉네임이에요!"
//                                (activity as joinActivity).canEnableNextbtn(false)
//
//
//                            })
//                        }
//                    }
//                )

        return mBinding?.root
    }
    private fun CheckNameAPICall(query:String):Boolean?{
        var result: Boolean = true
        JoinRetrofitManager.instance.checkName(inputemail = query, completion ={ responseStatus, checkOverlapData ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY -> {
                    Log.d(Constant.TAG,"api 호출 성공 check  = $checkOverlapData")
                    if (checkOverlapData?.status==200){
                        result = checkOverlapData.result
                        if (result==false){//"@drawable/ic_baseline_check_24"
                            join_step_three_name_layout.helperText=JOINSTRING.goodNickname
                            (activity as joinActivity).canEnableNextbtn(true)
                            joinViewModel.setname(query!!)


                        }else{
                            join_step_three_name_layout.error=JOINSTRING.wrongNickNameoverlap

                            (activity as joinActivity).canEnableNextbtn(false)
                        }
                    }
                }
            }
        } )
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        join_step_three_name_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                join_step_three_name_text.hint=""
                mBinding?.joinStepThreeNameLayout?.hint=""
                if (p0.toString().trim()!=""){
                    (activity as joinActivity).canEnableNextbtn(false)
                    mBinding?.joinStepThreeNameLayout?.helperText="멋진 이름이네요:)"

                }
                else (activity as joinActivity).canEnableNextbtn(false)


            }

        })
    }
    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(Constant.TAG,"joinStepThreeFragment onDestroyView")
    }


}