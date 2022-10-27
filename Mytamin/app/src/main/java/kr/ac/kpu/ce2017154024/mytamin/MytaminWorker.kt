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
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.HomeRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.retrofit.token.InformationRetrofitManager
import kr.ac.kpu.ce2017154024.mytamin.utils.BitmapRequestBody
import kr.ac.kpu.ce2017154024.mytamin.utils.Constant.TAG
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.DAYNOTEDATE
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.NOTE
import kr.ac.kpu.ce2017154024.mytamin.utils.PrivateUserDataSingleton.WISHTEXT
import kr.ac.kpu.ce2017154024.mytamin.utils.RESPONSE_STATUS
import okhttp3.MultipartBody
import java.io.FileDescriptor
import java.io.IOException

class MytaminWorker(ctx: Context, params: WorkerParameters) :Worker(ctx,params) {
    companion object {
        const val EXTRA_URI_ARRAY = "EXTRA_URI_ARRAY"

    }
    val context = ctx

    override fun doWork(): Result {

        Log.d(TAG, "MytaminWorker doWork work")
        val appContext = applicationContext
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
            newDaynoteAPI(imageList)
            Result.success()
        } catch (throwable: Throwable) {
            Log.d(TAG, "Error applying work")
            Result.failure()
        }
    }
//        return try {
//            wellcomAPICall()
//            Result.success()
//        } catch (throwable: Throwable) {
//            Log.d(TAG, "Error applying work")
//            Result.failure()
//        }
//    }
    //                        myRecordViewmodel.getbitmapList.value?.forEach {
//                            val bitmapRequestBody = it?.let {  BitmapRequestBody(it) }
//                            val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file.jpeg", bitmapRequestBody)
//                            imageList.add(bitmapMultipartBody)
//                        }
//                        InformationRetrofitManager.instance.imageListAPI(imageList){ responseStatus, i ->
//                            Log.d(TAG,"InformationRetrofitManager  i -> $i")
//                        }
    @Throws(IOException::class)
    fun getBitmapFromUri(uri: Uri): Bitmap {

        val parcelFileDescriptor: ParcelFileDescriptor? = context.contentResolver?.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()

        return image
    }
    //액티비티에선 이런식으로 사용
    //val uploadWorkRequest: WorkRequest =
    //   OneTimeWorkRequestBuilder<UploadWorker>()
   // .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
    //       .build()
    //제출
//    WorkManager
//    .getInstance(myContext)
//    .enqueue(uploadWorkRequest)

//    internal fun applyBlur() {
//        workManager.enqueue(OneTimeWorkRequest.from(MytaminWorker::class.java))
//    }
    private fun imageListAPI(imageList:List<MultipartBody.Part?>){
        Log.d(TAG, "MytaminWorker doWork imageListAPI()")
        InformationRetrofitManager.instance.imageListAPI(imageList){ responseStatus, i ->
                when(responseStatus){
                    RESPONSE_STATUS.OKAY ->{
                        Handler(Looper.getMainLooper()).post{
                            Toast.makeText(MyApplication.instance, "이미지 리스트 전송 성공", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
    }
    private fun newDaynoteAPI(imageList:List<MultipartBody.Part?>){
        Log.d(TAG, "MytaminWorker doWork imageListAPI()")
        InformationRetrofitManager.instance.newdaynote(imageList, wishtext = WISHTEXT, note = NOTE, date = DAYNOTEDATE){ responseStatus, i ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(MyApplication.instance, "데이노트 작성하기 성공", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
    private fun wellcomAPICall(){
        HomeRetrofitManager.instance.doCompleteBreath {responseStatus ->
            when(responseStatus){
                RESPONSE_STATUS.OKAY ->{
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(MyApplication.instance, "마이타민 숨 쉬기 완료", Toast.LENGTH_SHORT).show()
                    }

                }
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