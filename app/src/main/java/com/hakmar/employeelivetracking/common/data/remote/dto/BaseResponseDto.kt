package com.hakmar.employeelivetracking.common.data.remote.dto

class BaseResponseDto<T>(
    val data: T,
    val response: ResponseDto
)
