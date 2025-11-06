package com.syber.ssspltd.data.model.addorder

data class SupplierNickNameResponse(
    val data: NickNameWrapper? = null,
    val message: String? = null,
    val success: Boolean? = null,
    val error: Boolean? = null,
    val responsecode: String? = null
)

data class NickNameWrapper(
    val responseMessage: String? = null,
    val allowedAllType: Boolean? = null,
    val nickNameData: NickNameData? = null
)

data class NickNameData(
    val id: String? = null,
    val nickName: String? = null
)
