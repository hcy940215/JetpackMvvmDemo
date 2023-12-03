package me.hgj.jetpackmvvm.demo.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　:登录注册的ViewModel
 */
class NewLoanUserInfoViewModel : BaseViewModel() {

    //姓名
    var realName = StringObservableField()

    var age = StringObservableField()

    //用户名
    var username = StringObservableField()

    //用户名
    var cardNum = StringObservableField()

    var work = StringObservableField()

    var score = StringObservableField()

}