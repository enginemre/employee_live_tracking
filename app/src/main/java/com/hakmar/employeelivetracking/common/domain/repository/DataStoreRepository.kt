package com.hakmar.employeelivetracking.common.domain.repository

interface DataStoreRepository {

    suspend fun stringReadKey(key: String): String?

    suspend fun stringPutKey(key: String, value: String)

    suspend fun intReadKey(key: String): Int?

    suspend fun intPutKey(key: String, value: Int)

    suspend fun clearDataStore(key: String)
}