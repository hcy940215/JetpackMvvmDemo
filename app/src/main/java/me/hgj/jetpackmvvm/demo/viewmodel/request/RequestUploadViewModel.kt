package me.hgj.jetpackmvvm.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.util.toRequestBody
import me.hgj.jetpackmvvm.demo.data.model.bean.ImageUrlResponse
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.network.AppException
import me.hgj.jetpackmvvm.state.ResultState
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RequestUploadViewModel : BaseViewModel() {
    var repayResultDataState: MutableLiveData<ResultState<ImageUrlResponse>> = MutableLiveData()


    fun upload(
        path: String? = "",// 2000,
        isFront: Boolean = false
    ) {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), File(path))

        val part = MultipartBody.Part.createFormData("file", "face.jpg", body)

        requestNoCheck(
            block = {
                apiService.uploadOneFile(part)
            },

            success = {
                if (it.isSucces()) {
                    val data = it.getResponseData()
                    data.isFront = isFront
                    repayResultDataState.value = ResultState.Success(data)
                } else {
                    repayResultDataState.value = ResultState.onAppError(
                        AppException(
                            it.getResponseCode(),
                            it.getResponseMsg()
                        )
                    )
                }
            },
            error = {
                repayResultDataState.value = ResultState.onAppError(it)
            },
            isShowDialog = true,
            loadingMessage = "正在上传照片中..."
        )
    }

}