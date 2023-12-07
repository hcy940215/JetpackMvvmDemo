package me.hgj.jetpackmvvm.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.util.toRequestBody
import me.hgj.jetpackmvvm.demo.data.model.bean.UserInfo
import me.hgj.jetpackmvvm.demo.data.model.bean.UserInfoToken
import me.hgj.jetpackmvvm.demo.data.repository.request.HttpRequestCoroutine
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState
import org.json.JSONObject

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　:登录注册的请求ViewModel
 */
class RequestLoginRegisterViewModel : BaseViewModel() {

    //方式1  自动脱壳过滤处理请求结果，判断结果是否成功
    var loginResult = MutableLiveData<ResultState<UserInfoToken>>()

    var smsCodeResult = MutableLiveData<ResultState<String>>()

    //方式2  不用框架帮脱壳，判断结果是否成功
//    var loginResult2 = MutableLiveData<ResultState<ApiResponse<UserInfo>>>()

    fun loginReq(username: String, password: String) {
        //1.这种是在 Activity/Fragment的监听回调中拿到已脱壳的数据（项目有基类的可以用）
       request({
           val jsonLogin = JSONObject()
           jsonLogin.put("username", username)
           jsonLogin.put("password", password)
           apiService.login(jsonLogin.toRequestBody()) }//请求体
            , loginResult,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "正在登录中..."//等待框内容，可以默认不填请求网络中...
        )
        //2.这种是在Activity/Fragment中的监听拿到未脱壳的数据，你可以自己根据code做业务需求操作（项目没有基类的可以用）
        /*requestNoCheck({HttpRequestCoroutine.login(username,password)},loginResult2,true)*/

        //3. 这种是直接在当前ViewModel中就拿到了脱壳数据数据，做一层封装再给Activity/Fragment，如果 （项目有基类的可以用）
         /*request({apiService.login(username, password)},{
             //请求成功 已自动处理了 请求结果是否正常
         },{
             //请求失败 网络异常，或者请求结果码错误都会回调在这里
         })*/

        //4.这种是直接在当前ViewModel中就拿到了未脱壳数据数据，（项目没有基类的可以用）
        /*requestNoCheck({HttpRequestCoroutine.login(username,password)},{
            //请求成功 自己拿到数据做业务需求操作
            if(it.errorCode==0){
                //结果正确
            }else{
                //结果错误
            }
        },{
            //请求失败 网络异常回调在这里
        })*/
    }

    fun registerAndLogin(
        realName: String? = "",
        username: String? = "",
        smsCode: String? = "",
        password: String? = "",
        idCardImgFront: String? = "idCardImgFront",
        idCardImgBack: String? = "idCardImgFront",
    ) {
        request(
            { HttpRequestCoroutine.register(realName, username, smsCode, password,
                idCardImgFront, idCardImgBack) }
            , loginResult,
            true,
            "正在注册中..."
        )
    }

    fun getSmsCode(
        username: String? = "",
    ) {
        request(
            { HttpRequestCoroutine.sendSmsCode(username) }
            , smsCodeResult,
            true,
            "正在注册中..."
        )
    }



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