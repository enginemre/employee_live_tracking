package com.hakmar.employeelivetracking.features.bs_store.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.bs_store.data.mapper.toTimer
import com.hakmar.employeelivetracking.features.bs_store.data.remote.BsStoreApi
import com.hakmar.employeelivetracking.features.bs_store.data.remote.dto.TimerBodyDto
import com.hakmar.employeelivetracking.features.bs_store.domain.model.BsStore
import com.hakmar.employeelivetracking.features.bs_store.domain.model.Timer
import com.hakmar.employeelivetracking.features.bs_store.domain.repository.BsStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class BsStoreRepositoryImpl @Inject constructor(
    private val bsStoreApi: BsStoreApi,
    private val dataStoreRepository: DataStoreRepository
) : BsStoreRepository {
    override fun startGeneralShift(): Flow<Resource<Timer>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    val apiResult = bsStoreApi.startGeneralShift(userId)
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
                userId?.let {
                    val result = bsStoreApi.stopGeneralShift(
                        TimerBodyDto(
                            commmand = "pause",
                            id = userId
                        )
                    )
                    /*if(result.success){
                        emit(Resource.Success(apiresultResult.data.toTimer()))
                    }else{
                        emit(Resource.Error(
                            message = UiText.StringResorce(R.string.error_login)
                        ))
                    }*/
                    emit(Resource.Success(Unit))
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

    override fun exitGeneralShift(): Flow<Resource<Unit>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    val result = bsStoreApi.stopGeneralShift(
                        TimerBodyDto(
                            commmand = "exit",
                            id = userId
                        )
                    )
                    emit(Resource.Success(Unit))
                    /*if(apiResult.success){
                        emit(Resource.Success(apiResult.data.toTimer()))
                    }else{
                        emit(Resource.Error(
                            message = UiText.StringResorce(R.string.error_shift_not_started)
                        ))
                    }*/
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

    override fun getStores(): Flow<Resource<List<BsStore>>> {
        return flow {
            emit(Resource.Loading())
            val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
            userId?.let {
                val list = listOf(
                    BsStore(
                        name = "Fatih Esenyalı",
                        code = "5004",
                        passedTime = "2 saat 12 dk",
                        taskCount = 25,
                        completedTask = 25,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                    BsStore(
                        name = "Güzelyalı",
                        code = "5024",
                        passedTime = "1 saat 12 dk",
                        taskCount = 25,
                        completedTask = 12,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                    BsStore(
                        name = "Gözdağı Pendik",
                        code = "5054",
                        passedTime = "12 dk",
                        taskCount = 25,
                        completedTask = 3,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                    BsStore(
                        name = "Gülsuyu Maltepe",
                        code = "5014",
                        passedTime = "2 saat 12 dk",
                        taskCount = 25,
                        completedTask = 17,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                    BsStore(
                        name = "Güzelyalı",
                        code = "5024",
                        passedTime = "1 saat 12 dk",
                        taskCount = 25,
                        completedTask = 12,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                    BsStore(
                        name = "Gözdağı Pendik",
                        code = "5054",
                        passedTime = "12 dk",
                        taskCount = 25,
                        completedTask = 3,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                    BsStore(
                        name = "Gülsuyu Maltepe",
                        code = "5014",
                        passedTime = "2 saat 12 dk",
                        taskCount = 25,
                        completedTask = 17,
                        id = "",
                        lattitude = 23.3,
                        longtitude = 43.5,
                        areaCode = "001"
                    ),
                )
                delay(2000)
                emit(Resource.Success(list))
                /*if(apiResult.success){
                    emit(Resource.Success(apiResult.data.toTimer()))
                }else{
                    emit(Resource.Error(
                        message = UiText.StringResorce(R.string.error_shift_not_started)
                    ))
                }*/
            } ?: kotlin.run {
                emit(
                    Resource.Error(
                        message = UiText.StringResorce(R.string.error_store_list_not_fetched)
                    )
                )
            }
        }
    }

}