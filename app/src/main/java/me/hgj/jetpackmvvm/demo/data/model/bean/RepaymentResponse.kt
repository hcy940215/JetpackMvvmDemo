package me.hgj.jetpackmvvm.demo.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepaymentResponse(
    var totalRepayAmount: String?,
    var recentPlans: String?,
    var bLoanNum: String?,
    var bRepayments: ArrayList<RepaymentItemResponse>,
) : Parcelable

@Parcelize
data class RepaymentItemResponse(
    var repayId: String?,//": "d5ee1021-ca33-4e33-a8dd-a2b438a89538",//id
    var bLoanInfor: String?,//": "2023-12-01 借 2000.00 元 1/9期",
    var repaymentDate: String?,//": "2024-01-01",//还款时间
    var days: String?,//": 31,//剩余天数
    var bRepaymentInfor: String?,//": "31天后待还224.54元",
    var repayMethod: String?,//": "每月等额",
    var loanRate: String?,//": 0.03//利率
) : Parcelable

