package me.hgj.jetpackmvvm.demo.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BankCardResponse(
    val cardId: String?,//a4bd9be7-3d18-4c5d-8189-afff9666d9b9",
    val cardNo: String?,//123442265412",  //卡号
    val holderName: String?,//张三",   //开户人
    val bank: String?,//中国农业银行",  //开户行
    val phone: String?,//18817394118", //手机号
) : Parcelable

