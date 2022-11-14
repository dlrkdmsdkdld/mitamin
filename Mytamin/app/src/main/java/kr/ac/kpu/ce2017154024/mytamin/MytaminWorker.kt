package kr.ac.kpu.ce2017154024.mytamin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Toast
import androidx.work.*
import kotlinx.coroutines.*
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.BitmapRequestBody
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.DAYNOTEDATE
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.NOTE
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.WISHTEXT
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import okhttp3.MultipartBody
import okhttp3.internal.wait
import java.io.FileDescriptor
import java.io.IOException

class MytaminWorker(ctx: Context, params: WorkerParameters) :CoroutineWorker(ctx,params) {
    companion object {
        const val EXTRA_URI_ARRAY = "EXTRA_URI_ARRAY"

    }
    val context = ctx

    override suspend fun doWork(): Result {
        Handler(Looper.getMainLooper()).post{
            Toast.makeText(MyApplication.instance, "데이노트 작성중...", Toast.LENGTH_SHORT).show()

        }
        Log.d(TAG,"MytaminWorker tags : $tags")
        Log.d(TAG, "MytaminWorker doWork Start")
        val string_array = inputData.getStringArray(EXTRA_URI_ARRAY)
        val uriArray =ArrayList<Uri>()
        string_array?.forEach {
           val tmp :Uri = Uri.parse(it)
           uriArray.add(tmp)
        }
        Log.d(TAG, "MytaminWorker doWork string_array")
        val bitmapArray =ArrayList<Bitmap>()
        uriArray.forEach {
            bitmapArray.add(getBitmapFromUri(it))
        }
        val imageList = arrayListOf<MultipartBody.Part>()
        bitmapArray.forEach {
            val bitmapRequestBody = it?.let {  BitmapRequestBody(it) }
            val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("fileList", "file.jpeg", bitmapRequestBody)
            imageList.add(bitmapMultipartBody)
        }
        Log.d(TAG, "MytaminWorker doWork imageList")
        return try {
            nowDaynoteAPI(imageList)
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error applying work")
            Result.failure()
        }

    }
    @Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri): Bitmap {

        val parcelFileDescriptor: ParcelFileDescriptor? = context.contentResolver?.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()

        return image
    }


    val KEY_RESULT = "result"

    suspend fun nowDaynoteAPI(imageList:List<MultipartBody.Part?>):Result{
       val k = InformationRetrofitManager.instance.newdaynoteCall(imageList, wishid = WISHTEXT, note = NOTE, date = DAYNOTEDATE)
        if (k){
            //k는 레트로핏 response 객체가 리턴되면  작성됨
            val boleandata= inputData.getBoolean("result",true)
            val workdata =workDataOf(KEY_RESULT to boleandata)
            return Result.success(workdata)
        }else{
            return Result.failure()

        }
    }

    fun newDaynoteAPI(imageList:List<MultipartBody.Part?>  ) {
        InformationRetrofitManager.instance.newdaynote(imageList, wishid = WISHTEXT, note = NOTE, date = DAYNOTEDATE){ responseStatus, i ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    Handler(Looper.getMainLooper()).post{
                        Result.success()
                        val a= inputData.getBoolean("d",true)
                        val b =workDataOf(KEY_RESULT to a)
                        Toast.makeText(MyApplication.instance, "데이노트 작성하기 성공", Toast.LENGTH_SHORT).show()
                    }

                }else -> Result.failure()

            }
        }

    }
    private fun doworkMytaminWorkOneTime(){
        val workRequest = OneTimeWorkRequestBuilder<MytaminWorker>().build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(workRequest)
    }
    private fun doWorkWithConstraints() {
        // 네트워크 연결  상태를 제약조건으로 추가 한다
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 제약 조건과 함께 작업 생성
        val requestConstraint  = OneTimeWorkRequestBuilder<MytaminWorker>()
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance()

        workManager?.enqueue(requestConstraint)
    }
}