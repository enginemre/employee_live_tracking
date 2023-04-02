package com.hakmar.employeelivetracking.common.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.data.remote.GeneralApi
import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.domain.repository.CommonRepository
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val api: GeneralApi
) : CommonRepository {

    override fun getAllStores(): Flow<Resource<List<Store>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = api.getAllStores()
                if (result.response.success)
                    emit(
                        Resource.Success(
                            listOf<Store>(
                                Store(
                                    id = "1",
                                    taskCount = 2,
                                    completedTask = 1,
                                    areaCode = "001",
                                    passedTime = "2 saat",
                                    lattitude = 40.98520840398029,
                                    longtitude = 29.22647609101577,
                                    name = "Fatih Esenyalı Pendik",
                                    code = "5004",
                                ),
                                Store(
                                    lattitude = 40.96715318588728,
                                    longtitude = 29.24017077690967,
                                    name = "Selahattinler Gebze",
                                    code = "5504",
                                    id = "1",
                                    taskCount = 2,
                                    completedTask = 1,
                                    areaCode = "001",
                                    passedTime = "2 saat",
                                ),
                                Store(
                                    lattitude = 40.96838591104279,
                                    longtitude = 29.260204219902207,
                                    name = "Gülsüyü Maltepe",
                                    code = "5054",
                                    id = "1",
                                    taskCount = 2,
                                    completedTask = 1,
                                    areaCode = "001",
                                    passedTime = "2 saat",
                                ),
                                Store(
                                    lattitude = 40.00391682815467,
                                    longtitude = 29.20109507135916,
                                    name = "Güzelyalı  Pendik",
                                    code = "5024",
                                    id = "1",
                                    taskCount = 2,
                                    completedTask = 1,
                                    areaCode = "001",
                                    passedTime = "2 saat",
                                ),
                            )
                        )
                    )
                else
                    emit(Resource.Error(message = UiText.DynamicString(result.response.message)))
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