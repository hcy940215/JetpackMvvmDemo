package me.hgj.jetpackmvvm.demo.data.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageUrlResponse(
    var url: String,
    var isFront: Boolean = false,
) : Parcelable
