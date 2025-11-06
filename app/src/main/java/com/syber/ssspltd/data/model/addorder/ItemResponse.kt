package com.syber.ssspltd.data.model.addorder

data class ItemResponse(
    val data: ItemData? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val error: Boolean? = null,
    val responsecode: String? = null
)

data class ItemData(
    val responseMessage: String? = null,
    val itemName: List<Item>? = null,
    val allowedAllType: Boolean? = null
)

data class Item(
    val ID: String? = null,
    val ItemName: String? = null
)
