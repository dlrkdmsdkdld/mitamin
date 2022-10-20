package kr.ac.kpu.ce2017154024.mytamin.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import kr.ac.kpu.ce2017154024.mytamin.R
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.HomeRecyclerAdapter
import kr.ac.kpu.ce2017154024.mytamin.UI.ViewPager2.RecyclerView.home_RecyclerView.IHomeRecyclerView
import kr.ac.kpu.ce2017154024.mytamin.activity.todayMytaminActivity
import kr.ac.kpu.ce2017154024.mytamin.databinding.FragmentHomeBinding
import kr.ac.kpu.ce2017154024.mytamin.fragment.home.NoMytaminFragment
import kr.ac.kpu.ce2017154024.mytamin.fragment.home.YesMytaminFragment
import kr.ac.kpu.ce2017154024.mytamin.model.Status
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.parseTimeToHome
import kr.ac.kpu.ce2017154024.mytamin.viewModel.HomeViewModel


class HomeFragment : Fragment(),View.OnClickListener,IHomeRecyclerView {
    private var mBinding : FragmentHomeBinding?=null
    //홈 리싸이클러뷰
    private lateinit var myHomeRecyclerAdapter: HomeRecyclerAdapter
    private val myHomeViewModel: HomeViewModel by viewModels()
//    private lateinit var myHomeViewModel: HomeViewModel
    private var resultBoolean=ArrayList<Boolean>()
    private lateinit var statusData:Status
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        mBinding =binding
        Log.d(TAG,"HomeFragment onCreateView")
        showSampleData(isLoading = true)

        val hoemdatatext = parseTimeToHome()
        mBinding?.homeDateText?.text=hoemdatatext
        //myHomeViewModel= ViewModelProvider(this).get(HomeViewModel::class.java)

//        myHomeViewModel= ViewModelProvider(this).get(HomeViewModel::class.java)
        myHomeViewModel.getcomment.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val nickname = myHomeViewModel.getnickname.value
            val alltext =nickname+"님, "+it
            colorOrangeText(alltext,nickname!!.length)
        })


        return mBinding?.root
    }
    fun colorOrangeText(textdata:String,endpoint:Int){
        val builder = SpannableStringBuilder(textdata)
        builder.setSpan(ForegroundColorSpan(Color.parseColor("#FF7F57")),0,endpoint,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        mBinding?.homeStateText?.append(builder)
    }

    private fun showSampleData(isLoading: Boolean) {
        if (isLoading) {
            mBinding?.shimmerLayout?.startShimmer()
            mBinding?.shimmerLayout?.visibility = View.VISIBLE
            mBinding?.homeMainLayout?.visibility = View.GONE
        } else {
            mBinding?.shimmerLayout?.stopShimmer()
            mBinding?.shimmerLayout?.visibility = View.GONE
            mBinding?.homeMainLayout?.visibility = View.VISIBLE
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //리싸이클러뷰 설정
        //뷰모델에서마이타민섭취확인후 그에따른 초기데이터 지정
        myHomeViewModel.getstatus.observe(viewLifecycleOwner, Observer {
            this.myHomeRecyclerAdapter = HomeRecyclerAdapter(this)
            statusData= it
            resultBoolean.clear()
            resultBoolean.add(!(it.breathIsDone) )
            resultBoolean.add(!(it.senseIsDone))
            resultBoolean.add(!(it.reportIsDone))
            resultBoolean.add(!(it.careIsDone))
            myHomeRecyclerAdapter.AlreadyTodayMytamin(resultBoolean) // 이미 한것은 다시 못하게 막음
            home_recyclerView.adapter = myHomeRecyclerAdapter //어뎁터연결
            if (it.reportIsDone == true ||it.careIsDone ==true ){
                myHomeViewModel.LatestMytaminAPI(statusData)

                  Log.d(TAG,"최근 마이타민 섭취기록있음")
            //    myHomeViewModel.LatestMytaminAPI()
            }else{
                showSampleData(isLoading = false)
                val NoMytaminFragment = NoMytaminFragment()
                childFragmentManager.beginTransaction().replace(R.id.home_fragment_container,NoMytaminFragment).commit()
                Log.d(TAG,"최근 마이타민 섭취기록없음")
            }
        })
        myHomeViewModel.getLatestMytamin.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"getlatestMytamin observe -> $it")
            showSampleData(isLoading = false)
            val yesMytaminFragment = YesMytaminFragment()
            childFragmentManager.beginTransaction().replace(R.id.home_fragment_container,yesMytaminFragment).commit()
        })

    }

    override fun onDestroyView() { // 프래그먼트 삭제될때 자동으로실행
        mBinding=null
        super.onDestroyView()
        Log.d(TAG,"HomeFragment onDestroyView")
    }

    override fun onClick(p0: View?) {
        when(p0){
//            todayMytaminBtn -> {
//                val intent = Intent(context,todayMytaminActivity::class.java)
//                startActivity(intent)
//            }
        }
    }

    override fun onSearchItemClicked(position: Int) {
        Log.d(TAG,"클릭한 리싸이클러뷰 $position 번째")
        val intent= Intent(context,todayMytaminActivity::class.java)
        if(position==3){
            intent.putExtra("step",position+3)
        }
        else{
            intent.putExtra("step",position+1)
        }
        val bundle = Bundle()
        bundle.putSerializable("statusData",statusData)

        if (myHomeViewModel.getLatestMytamin.value !=null){
            val bundleL = Bundle()
            bundleL.putSerializable("LatestMytamin",myHomeViewModel.getLatestMytamin.value)
            intent.putExtra("bundleL",bundleL)
        }else{

        }

        intent.putExtra("bundle",bundle)
      //  val k = resultBoolean
     //   intent.putExtra("resultBoolean",resultBoolean)

        startActivity(intent)
    }

}