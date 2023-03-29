package com.hakmar.employeelivetracking.features.auth.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.auth.data.remote.LoginApi
import com.hakmar.employeelivetracking.features.auth.domain.repository.LoginRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    val dataStore: DataStoreRepository,
    val loginApi: LoginApi
) : LoginRepository {
    override fun login(userCode: String, password: String): Flow<Resource<Unit>> {
        return flow {
            try {
                /*emit(Resource.Loading())
                val apiResult = loginApi.login(
                    loginBodyDto = LoginBodyDto(
                        username = "testadmin",
                        password = "testuser",
                    )
                )
                if(apiResult.response.success){*/
                emit(Resource.Loading())
                delay(1500)
                dataStore.stringPutKey(AppConstants.USER_ID, "")
                dataStore.intPutKey(AppConstants.IS_LOGIN, 1)
                emit(Resource.Success(Unit))
                /*}else{
                    emit(Resource.Error(
                        message = UiText.DynamicString(apiResult.response.message)
                    ))
                }*/
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