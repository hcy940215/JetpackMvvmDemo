package me.hgj.jetpackmvvm.demo.data.model.bean

/**
 * 业务线: GoodsList
 * 类作用: java类作用描述
 * 作  者: 10063082
 * 日  期: 2023/12/5
 */
data class LoanPayData(
    val principal: Double? = null,//" : 220.37, //本金
    val interest: Double? = null,//": 4.17, //利息
    val repayAmount: Double? = null,//": 224.54, //应还金额
    val repayDate: String? = null,//": "2024-01-01" //还款日期
)