package com.hakmar.employeelivetracking.features.store_detail_tasks.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.store_detail_tasks.data.remote.StoreTaskApi
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.SteelCaseRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SteelCaseRepositoryImpl @Inject constructor(
    private val storeTaskApi: StoreTaskApi,
    private val dataStoreRepository: DataStoreRepository
) : SteelCaseRepository {
    override fun sendSteelCaseRepository(map: HashMap<String, Any>): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let {
                    map["user_uuid"] = it
                    val result = storeTaskApi.sendSteelCaseAmount(map.toMap())
                    if (result.response.success) {
                        emit(Resource.Success(result.data))
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
}