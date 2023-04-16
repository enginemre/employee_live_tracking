package com.hakmar.employeelivetracking.common.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.data.mapper.toStore
import com.hakmar.employeelivetracking.common.data.remote.GeneralApi
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.repository.CommonRepository
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val api: GeneralApi,
    private val dataStoreRepository: DataStoreRepository
) : CommonRepository {

    override fun getAllStores(): Flow<Resource<List<Store>>> {
        return flow {
            try {
                val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
                userId?.let { id ->
                    emit(Resource.Loading())
                    val result = api.getAllStores(id)
                    if (result.response.success)
                        emit(
                            Resource.Success(
                                result.data.map { it.toStore() }
                            )
                        )
                    else
                        emit(Resource.Error(message = UiText.DynamicString(result.response.message)))
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
}