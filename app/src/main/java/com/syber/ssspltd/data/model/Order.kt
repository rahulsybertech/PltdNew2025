package com.syber.ssspltd.data.model

data class Order(
    val saleParty: String,
    val item: String,
    val type: String,
    val subParty: String,
    val orderNo: String,
    val orderDate: String,
    val amount: Double,
    val qty: Int
)
