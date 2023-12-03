package me.hgj.jetpackmvvm.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.CollectUiState
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.demo.data.model.bean.BankCardResponse
import me.hgj.jetpackmvvm.demo.data.repository.request.HttpRequestCoroutine
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

open class BankCardViewModel : BaseViewModel() {

    //收藏文章
    val collectUiState: MutableLiveData<CollectUiState> = MutableLiveData()

    //方式1  自动脱壳过滤处理请求结果，判断结果是否成功
    var addResult = MutableLiveData<ResultState<Any>>()

    var smsCodeResult = MutableLiveData<ResultState<String>>()

    var bankCardListDataState: MutableLiveData<ListDataUiState<BankCardResponse>> = MutableLiveData()

    fun getBankCardList(isRefresh: Boolean) {
        request({ apiService.getBankCardList() }, {
            //请求成功
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = false,
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it
                )
            bankCardListDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<BankCardResponse>()
                )
            bankCardListDataState.value = listDataUiState
        })
    }

    fun addBankCard(
        realName:String? = "",
        username:String? = "",
        smsCode:String? = "",
        cardNum:String? = "",
        bankName:String? = "",
    ) {
        request(
            { HttpRequestCoroutine.addBankCard(realName, username, smsCode, cardNum, bankName) }
            ,addResult,
            true,
            "正在添加中..."
        )
    }

    fun getSmsCode(
        username: String? = "",
    ) {
        request(
            { HttpRequestCoroutine.sendSmsCode(username) }
            , smsCodeResult,
            true,
            "正在获取中..."
        )
    }
}