package com.ssspvtltd.quick.model.order.add.additem
data class PackType(
    var id: String? = null,
    var pcsId: String,
    var packName: String? = null,
    var qty: String? = null,
    var amount: String? = null,
    var itemDetail: List<PackTypeItem> = emptyList()
)

