package me.hgj.jetpackmvvm.demo.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class LoanViewModel : BaseViewModel() {

    var price = StringObservableField()
    var month = StringObservableField()
    var returnPay = StringObservableField()
    var bank = StringObservableField()

    var age = StringObservableField()

    //用户名
    var username = StringObservableField()

    //用户名
    var cardNum = StringObservableField()

    var work = StringObservableField()

    var score = StringObservableField()


    var payUse = StringObservableField()

}