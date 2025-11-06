package com.syber.ssspltd.data.model

data class ApiErrorResponse(
    val title: String? = null,
    val status: Int? = null,
    val errors: Map<String, List<String>>? = null,
    val traceId: String? = null
)