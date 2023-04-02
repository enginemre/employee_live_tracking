package com.hakmar.employeelivetracking.features.profile.data.repository

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.domain.repository.DataStoreRepository
import com.hakmar.employeelivetracking.features.profile.data.mapper.toUser
import com.hakmar.employeelivetracking.features.profile.data.remote.ProfileApi
import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.features.profile.domain.repository.ProfileRepository
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileApi: ProfileApi,
    private val dataStoreRepository: DataStoreRepository
) : ProfileRepository {
    override fun getUserInfo(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
            userId?.let {
                val result = profileApi.getUserDetail(it)
                if (result.response.success) {
                    emit(Resource.Success(result.data.toUser()))
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
                        message = UiText.StringResorce(R.string.error_user_info)
                    )
                )
            }

        }
    }

    override fun updateUserInfo(
        oldPassword: String,
        newPassword: String,
        email: String
    ): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            val userId = dataStoreRepository.stringReadKey(AppConstants.USER_ID)
            userId?.let {
                val result = profileApi.getUserDetail(it)
                if (result.response.success) {
                    emit(Resource.Success(result.data.toUser()))
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
                        message = UiText.StringResorce(R.string.error_user_info)
                    )
                )
            }
        }
    }
}