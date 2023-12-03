package me.hgj.jetpackmvvm.demo.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoanRecordResponse(
    var loanAmount: String?,
    var createTime: String?,
    var loanStatus: String?,
) : Parcelable