package com.hakmar.employeelivetracking.features.bs_store.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.data.mapper.toStore
import com.hakmar.employeelivetracking.common.data.mapper.toTimerStatus
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.model.TimerStatus
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.bs_store.data.mapper.toTimer
import com.hakmar.employeelivetracking.features.bs_store.data.remote.BsStoreApi
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerRequestBody
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class BsStoreRepositoryImpl @Inject constructor(
    private val bsStoreApi: BsStoreApi,
    private val dataStoreRepository: DataStoreRepository
) : BsStoreRepository {
    override fun startGeneralShift(lat: Double, lon: Double): Flow<Resource<Timer>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    val apiResult = bsStoreApi.startGeneralShift(
                        TimerRequestBody(
                            it,
                            lat.toString(),
                            lon.toString()
                        )
                    )
                    if (apiResult.response.success) {
                        emit(Resource.Success(apiResult.data.toTimer()))
                    } else {
                        emit(
                            Resource.Error(
                                message = UiText.DynamicString(apiResult.response.message)
                            )
                        )
                    }
                } ?: kotlin.run {
                    emit(
                        Resource.Error(
                            message = UiText.StringResorce(R.string.error_shift_not_started)
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

    override fun pauseGeneralShift(): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    val result = bsStoreApi.pauseGeneralShift(id)
                    if (result.response.success) {
                        emit(Resource.Success(Unit))
                    } else {
                        emit(
                            Resource.Error(
                                message = UiText.DynamicString(result.response.message)
                            )
                        )
                    }
                } ?: kotlin.run {
                    emit(
                        Resource.Error(
                            message = UiText.StringResorce(R.string.error_shift_not_started)
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

    override fun resumeGeneralShift(): Flow<Resource<Timer>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    val result = bsStoreApi.resumeGeneralShift(id)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.toTimer()))
                    } else {
                        emit(
                            Resource.Error(
                                message = UiText.DynamicString(result.response.message)
                            )
                        )
                    }
                } ?: kotlin.run {
                    emit(
                        Resource.Error(
                            message = UiText.StringResorce(R.string.error_shift_not_started)
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

    override fun getStores(): Flow<Resource<List<Store>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    val result = bsStoreApi.getDmStores(userId = id)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.map { it.toStore() }))
                    } else {
                        emit(
                            Resource.Error(
                                message = UiText.StringResorce(R.string.error_shift_not_started)
                            )
                        )
                    }
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

    override fun initGeneralShift(): Flow<Resource<TimerStatus>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    val result = bsStoreApi.initGeneralShift(it)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.toTimerStatus()))
                    } else {
                        emit(Resource.Error(UiText.DynamicString(result.response.message)))
                    }
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