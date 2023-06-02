package com.hakmar.employeelivetracking.features.pm_store.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.data.mapper.toStore
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.pm_store.data.remote.PmStoreApi
import com.hakmar.employeelivetracking.features.pm_store.domain.repository.PmStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PmStoreRepositoryImpl @Inject constructor(
    private val pmStoreApi: PmStoreApi,
    private val dataStoreRepository: DataStoreRepository
) : PmStoreRepository {
    override fun getPmStores(): Flow<Resource<List<Store>>> {
        return flow {
            try {
                val userId = runBlocking { dataStoreRepository.stringReadKey(AppConstants.USER_ID) }
                userId?.let { id ->
                    emit(Resource.Loading())
                    val result = pmStoreApi.getPmStores(id)
                    if (result.response.success) {
                        emit(Resource.Success(result.data.map { it.toStore() }))
                    } else {
                        emit(
                            Resource.Error(
                                message = UiText.DynamicString(result.response.message),
                            )
                        )
                    }
                } ?: kotlin.run {
                    emit(
                        Resource.Error(
                            message = UiText.StringResorce(R.string.error_relogin)
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