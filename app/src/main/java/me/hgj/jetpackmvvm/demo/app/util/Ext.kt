package me.hgj.jetpackmvvm.demo.app.util

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * 业务线: GoodsList
 * 类作用: java类作用描述
 * 作  者: 10063082
 * 日  期: 2023/12/3
 */

fun JSONObject.toRequestBody(): RequestBody {
    return RequestBody.create(
        MediaType.parse("application/json;charset=utf-8"),
        this.toString()
    )
}
