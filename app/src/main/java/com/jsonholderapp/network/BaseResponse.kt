package com.jsonholderapp.network

data class BaseResponse<T>(
    val code: Int,
    val errorMessage: String? = null,
    val data: T? = null
)