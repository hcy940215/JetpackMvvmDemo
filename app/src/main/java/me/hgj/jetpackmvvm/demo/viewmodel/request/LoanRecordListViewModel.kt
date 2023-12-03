package me.hgj.jetpackmvvm.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.network.apiService
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.CollectUiState
import me.hgj.jetpackmvvm.demo.app.network.stateCallback.ListDataUiState
import me.hgj.jetpackmvvm.demo.data.model.bean.CollectUrlResponse
import me.hgj.jetpackmvvm.demo.data.model.bean.LoanRecordResponse
import me.hgj.jetpackmvvm.ext.request

open class LoanRecordListViewModel : BaseViewModel() {

    //收藏文章
    val collectUiState: MutableLiveData<CollectUiState> = MutableLiveData()

    //收藏网址
    val collectUrlUiState: MutableLiveData<CollectUiState> = MutableLiveData()

    //收藏de网址数据
    var urlDataState: MutableLiveData<ListDataUiState<CollectUrlResponse>> = MutableLiveData()

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun collect(id: Int) {
        request({ apiService.collect(id) }, {
            val uiState = CollectUiState(isSuccess = true, collect = true, id = id)
            collectUiState.value = uiState
        }, {
            val uiState = CollectUiState(isSuccess = false, collect = false, errorMsg = it.errorMsg, id = id)
            collectUiState.value = uiState
        })
    }

    var loanListDataState: MutableLiveData<ListDataUiState<LoanRecordResponse>> = MutableLiveData()

    /**
     * 借还记录接口
     */
    fun getListLoanData(isRefresh: Boolean) {
        request({ apiService.listLoan() }, {
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
            loanListDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<LoanRecordResponse>()
                )
            loanListDataState.value = listDataUiState
        })
    }

    fun getCollectUrlData() {
        TODO("Not yet implemented")
    }

    fun uncollect(id: Int) {

    }

    fun uncollectUrl(id: Int) {

    }

    fun collectUrl(name: String, link: String) {

    }

}