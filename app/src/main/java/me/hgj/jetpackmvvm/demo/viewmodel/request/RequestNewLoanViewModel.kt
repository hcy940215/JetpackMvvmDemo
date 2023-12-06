package me.hgj.jetpackmvvm.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.demo.app.util.toRequestBody
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanPayData
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.network.AppException
import me.hgj.jetpackmvvm.state.ResultState
import org.json.JSONObject

class RequestNewLoanViewModel : BaseViewModel() {
    var loanListDataState: MutableLiveData<ListDataUiState<LoanPayData>> = MutableLiveData()
    var loanResultDataState: MutableLiveData<ResultState<Any?>> = MutableLiveData()
    var repayResultDataState: MutableLiveData<ResultState<Any?>> = MutableLiveData()

    fun newLoan(
        loanAmount: String? = "",// 2000,
        loanTerm: String? = "",// 9,
        repayMethod: String? = "",// 0,
        name: String? = "",// "张山",
        age: String? = "",// "30",
        cardNo: String? = "",// "111111111111111",
        phone: String? = "",// "18888888",
        income: String? = "",// 200,
        alipayScore: String? = "",// 200,
        loanUse: String? = "",// "个人消费",
        faceImage: String? = "",// "/人脸照片",
        cardId: String? = "",// "收款账户"

//        loanAmount: String? = "2000",// 2000,
//        loanTerm: String? = "9",// 9,
//        repayMethod: String? = "0",// 0,
//        name: String? = "张山",// "张山",
//        age: String? = "30",// "30",
//        cardNo: String? = "111111111111111",// "111111111111111",
//        phone: String? = "18888888",// "18888888",
//        income: String? = "20000",// 200,
//        alipayScore: String? = "200",// 200,
//        loanUse: String? = "个人消费",// "个人消费",
//        faceImage: String? = "/人脸照片",// "/人脸照片",
//        cardId: String? = "039f092b-6038-4b1e-98c5-4d67fdf22168",// "收款账户"
    ) {
        val jsonLogin = JSONObject()
        jsonLogin.put("loanAmount", loanAmount)
        jsonLogin.put("loanTerm", loanTerm)
        jsonLogin.put("repayMethod", repayMethod)
        jsonLogin.put("name", name)
        jsonLogin.put("age", age)
        jsonLogin.put("cardNo", cardNo)
        jsonLogin.put("phone", phone)
        jsonLogin.put("income", income)
        jsonLogin.put("alipayScore", alipayScore)
        jsonLogin.put("loanUse", loanUse)
        jsonLogin.put("faceImage", faceImage)
        jsonLogin.put("cardId", cardId)
        request(
            block = {
                apiService.newLoan(jsonLogin.toRequestBody())
            },

            success = {
                loanResultDataState.value = ResultState.Success(it)
            },
            error = {
                loanResultDataState.value = ResultState.onAppError(it)
            },
            isShowDialog = true,
            loadingMessage = "正在登录中..."
        )
    }


    fun repaymentPlan(
        loanAmount: String? = "",// 2000,
        loanTerm: String? = "",// 9,
        repayMethod: String? = "",// 0,
    ) {
        val jsonLogin = JSONObject()
        jsonLogin.put("loanAmount", loanAmount)
        jsonLogin.put("loanTerm", loanTerm)
        jsonLogin.put("repayMethod", repayMethod)
        request(
            block = {
                apiService.repaymentPlan(jsonLogin.toRequestBody())
            },

            success = {
                //请求成功
                val listDataUiState =
                    ListDataUiState(
                        isSuccess = true,
                        isRefresh = true,
                        isEmpty = it.isEmpty(),
                        hasMore = repayMethod == "0",
                        isFirstEmpty = it.isEmpty(),
                        listData = it
                    )
                loanListDataState.value = listDataUiState
            },
            error = {
                //请求失败
                val listDataUiState =
                    ListDataUiState(
                        isSuccess = false,
                        errMessage = it.errorMsg,
                        isRefresh = true,
                        hasMore = repayMethod == "0",
                        listData = arrayListOf<LoanPayData>()
                    )
                loanListDataState.value = listDataUiState

            },
            isShowDialog = true,
            loadingMessage = "正在生成还款计划..."
        )
    }


    fun repayment(
        repayId: String? = "",// 2000,
        loanTerm: String? = "",// 9,
        cardId: String? = "",// 0,
    ) {
        val jsonLogin = JSONObject()
        jsonLogin.put("repayId", repayId)
        jsonLogin.put("loanTerm", loanTerm)
        jsonLogin.put("cardId", cardId)
        requestNoCheck(
            block = {
                apiService.repayment(jsonLogin.toRequestBody())
            },

            success = {
                if (it.isSucces()){
                    repayResultDataState.value = ResultState.Success(it)
                }else{
                    repayResultDataState.value = ResultState.onAppError(AppException(it.getResponseCode(), it.getResponseMsg()))
                }
            },
            error = {
                repayResultDataState.value = ResultState.onAppError(it)
            },
            isShowDialog = true,
            loadingMessage = "正在还款中..."
        )
    }

}