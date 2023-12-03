package me.hgj.jetpackmvvm.demo.data.repository.request

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.util.CacheUtil
import me.hgj.jetpackmvvm.demo.app.util.toRequestBody
import me.hgj.jetpackmvvm.demo.data.model.bean.*
import me.hgj.jetpackmvvm.network.AppException
import org.json.JSONObject

/**
 * 作者　: hegaojian
 * 时间　: 2020/5/4
 * 描述　: 处理协程的请求类
 */

val HttpRequestCoroutine: HttpRequestManger by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpRequestManger()
}

class HttpRequestManger {
    /**
     * 获取首页文章数据
     */
    suspend fun getHomeData(pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>> {
        //同时异步请求2个接口，请求完成后合并数据
        return withContext(Dispatchers.IO) {
            val listData = async { apiService.getAritrilList(pageNo) }
            //如果App配置打开了首页请求置顶文章，且是第一页
            if (CacheUtil.isNeedTop() && pageNo == 0) {
                val topData = async { apiService.getTopAritrilList() }
                listData.await().data.datas.addAll(0, topData.await().data)
                listData.await()
            } else {
                listData.await()
            }
        }
    }

    /**
     * 注册并登陆
     */
    suspend fun register(
        realName: String? = "",
        username: String? = "",
        smsCode: String? = "",
        password: String? = "",
        idCardImgFront: String? = "idCardImgFront",
        idCardImgBack: String? = "idCardImgFront",
    ): TokenApiResponse<String> {
        val jsonObject = JSONObject()
        jsonObject.put("realName", realName)
        jsonObject.put("username", username)
        jsonObject.put("smsCode", smsCode)
        jsonObject.put("password", password)
        jsonObject.put("confirmPassword", password)
        jsonObject.put("idCardImgFront", idCardImgFront)
        jsonObject.put("idCardImgBack", idCardImgBack)
        val requestBody = jsonObject.toRequestBody()
        val registerData = apiService.register(requestBody)
        //判断注册结果 注册成功，调用登录接口
        if (registerData.isSucces()) {
            val jsonLogin = JSONObject()
            jsonLogin.put("username", username)
            jsonLogin.put("password", password)
            return apiService.login(jsonLogin.toRequestBody())
        } else {
            //抛出错误异常
            throw AppException(registerData.code, registerData.msg)
        }
    }

    /**
     * 注册并登陆
     */
    suspend fun sendSmsCode(phone: String? = ""): ApiResponse<String> {
        val jsonObject = JSONObject()
        jsonObject.put("phone", phone)
        val requestBody = jsonObject.toRequestBody()
        val registerData = apiService.sendSmsCode(requestBody)
        if (registerData.isSucces()) {
            return registerData
        } else {
            //抛出错误异常
            throw AppException(registerData.code, registerData.msg)
        }
    }

    /**
     * 添加银行卡
     */
    suspend fun addBankCard(
        realName: String? = "",
        username: String? = "",
        smsCode: String? = "",
        cardNum: String? = "",
        bankName: String? = "",
    ): ApiResponse<Any> {
        val jsonObject = JSONObject()
        jsonObject.put("holderName",realName)
        jsonObject.put("phone",username)
        jsonObject.put("smsCode",smsCode)
        jsonObject.put("cardNo",cardNum)
        jsonObject.put("bank",bankName)
        val requestBody = jsonObject.toRequestBody()
        val registerData = apiService.addBankCard(requestBody)
        if (registerData.isSucces()) {
            return registerData
        } else {
            //抛出错误异常
            throw AppException(registerData.code, registerData.msg)
        }
    }

    /**
     * 获取项目标题数据
     */
    suspend fun getProjectData(
        pageNo: Int,
        cid: Int = 0,
        isNew: Boolean = false
    ): ApiResponse<ApiPagerResponse<ArrayList<AriticleResponse>>> {
        return if (isNew) {
            apiService.getProjecNewData(pageNo)
        } else {
            apiService.getProjecDataByType(pageNo, cid)
        }
    }
}