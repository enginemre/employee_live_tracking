package com.hakmar.employeelivetracking.features.store_detail.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.data.mapper.toStore
import com.hakmar.employeelivetracking.common.data.mapper.toTimerStatus
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.model.TimerStatus
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.bs_store.data.mapper.toTimer
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.features.store_detail.data.remote.StoreDetailApi
import com.hakmar.employeelivetracking.features.store_detail.data.remote.dto.StoreTimerRequestBodyDto
import com.hakmar.employeelivetracking.features.store_detail.domain.repository.StoreDetailRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class StoreDetailRepositoryImpl @Inject constructor(
    private val storeDetailApi: StoreDetailApi,
    private val dataStoreRepository: DataStoreRepository
) : StoreDetailRepository {
    override fun getStoreDetail(storeCode: String): Flow<Resource<Store>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    val result = storeDetailApi.getStoreDetail(storeCode, it)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.toStore()))
                    } else
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                } ?: kotlin.run {
                    emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun startStoreShift(
        storeCode: String,
        lat: Double,
        lon: Double
    ): Flow<Resource<Timer>> {
        return flow {
            try {
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    emit(Resource.Loading())
                    val result = storeDetailApi.startStoreShift(
                        StoreTimerRequestBodyDto(
                            storeCode,
                            it,
                            lat.toString(),
                            lon.toString()
                        )
                    )
                    if (result.response.success) {
                        emit(Resource.Success(result.data.toTimer()))
                    } else
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                } ?: kotlin.run {
                    emit(Resource.Error(UiText.StringResorce(R.string.error_relogin)))
                }

            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun pauseStoreShift(storeCode: String): Flow<Resource<Unit>> {
        return flow {
            try {
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    emit(Resource.Loading())
                    val result = storeDetailApi.pauseStoreShift(storeCode, id)
                    if (result.response.success) {
                        emit(Resource.Success(Unit))
                    } else
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                } ?: kotlin.run {
                    emit(Resource.Error(UiText.StringResorce(R.string.error_relogin)))
                }

            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun resumeStoreShift(storeCode: String): Flow<Resource<Timer>> {
        return flow {
            try {
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    emit(Resource.Loading())
                    val result = storeDetailApi.resumeStoreShift(storeCode, id)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.toTimer()))
                    } else
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                } ?: kotlin.run {
                    emit(Resource.Error(UiText.StringResorce(R.string.error_relogin)))
                }

            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun cancelStoreShift(
        storeCode: String,
        lat: Double,
        lon: Double
    ): Flow<Resource<Unit>> {
        return flow {
            try {
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    emit(Resource.Loading())
                    val result = storeDetailApi.cancelStoreShift(
                        StoreTimerRequestBodyDto(
                            storeCode,
                            id,
                            lat.toString(),
                            lon.toString()
                        )
                    )
                    if (result.response.success) {
                        emit(Resource.Success(Unit))
                    } else
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                } ?: kotlin.run {
                    emit(Resource.Error(UiText.StringResorce(R.string.error_relogin)))
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }

    override fun initStoreShift(storeCode: String): Flow<Resource<TimerStatus>> {
        return flow {
            try {
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    emit(Resource.Loading())
                    val result = storeDetailApi.initStoreShift(storeCode = storeCode, userId = it)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.toTimerStatus()))
                    } else
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                } ?: kotlin.run {
                    emit(
                        Resource.Error(
                            message = UiText.StringResorce(R.string.error_store_list_not_fetched)
                        )
                    )
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_timeout)))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(UiText.StringResorce(R.string.error_internet)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        UiText.DynamicString(
                            e.localizedMessage ?: "Unexceptred error"
                        )
                    )
                )
            }
        }
    }
}