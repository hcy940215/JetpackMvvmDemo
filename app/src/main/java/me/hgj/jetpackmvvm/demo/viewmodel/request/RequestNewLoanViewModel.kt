package me.hgj.jetpackmvvm.demo.viewmodel.request

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.util.toRequestBody
import me.hgj.jetpackmvvm.ext.request
import org.json.JSONObject

class RequestNewLoanViewModel : BaseViewModel() {

    fun newLoan(
//        loanAmount: String? = "",// 2000,
//        loanTerm: String? = "",// 9,
//        repayMethod: String? = "",// 0,
//        name: String? = "",// "张山",
//        age: String? = "",// "30",
//        cardNo: String? = "",// "111111111111111",
//        phone: String? = "",// "18888888",
//        income: String? = "",// 200,
//        alipayScore: String? = "",// 200,
//        loanUse: String? = "",// "个人消费",
//        faceImage: String? = "",// "/人脸照片",
//        cardId: String? = "",// "收款账户"

        loanAmount: String? = "2000",// 2000,
        loanTerm: String? = "9",// 9,
        repayMethod: String? = "0",// 0,
        name: String? = "张山",// "张山",
        age: String? = "30",// "30",
        cardNo: String? = "111111111111111",// "111111111111111",
        phone: String? = "18888888",// "18888888",
        income: String? = "20000",// 200,
        alipayScore: String? = "200",// 200,
        loanUse: String? = "个人消费",// "个人消费",
        faceImage: String? = "/人脸照片",// "/人脸照片",
        cardId: String? = "039f092b-6038-4b1e-98c5-4d67fdf22168",// "收款账户"
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

            },
            error = {

            },
            isShowDialog = true,
            loadingMessage = "正在登录中..."
        )
    }

}