package me.hgj.jetpackmvvm.demo.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　:登录注册的ViewModel
 */
class BankCardAddViewModel : BaseViewModel() {

    //姓名
    var realName = StringObservableField()

    //用户名
    var username = StringObservableField()

    //用户名
    var cardNum = StringObservableField()

    //验证码
    var smsCode = StringObservableField()

    //密码(登录注册界面)
    var bankName = StringObservableField()

    var password2 = StringObservableField()

}