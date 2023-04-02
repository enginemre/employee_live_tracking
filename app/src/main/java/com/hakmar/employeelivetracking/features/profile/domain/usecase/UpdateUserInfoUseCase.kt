package com.hakmar.employeelivetracking.features.profile.domain.usecase

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.features.profile.domain.model.User
import com.hakmar.employeelivetracking.features.profile.domain.repository.ProfileRepository
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(
        oldPassword: String,
        newPassword: String,
        email: String
    ): Flow<Resource<User>> {
        if (oldPassword.isEmpty())
            return flow {
                emit(
                    Resource.Error(
                        message = UiText.StringResorce(R.string.error_old_password_empty),
                    )
                )
            }
        if (newPassword.isEmpty()) {
            return flow {
                emit(
                    Resource.Error(
                        message = UiText.StringResorce(R.string.error_new_password_empty),
                    )
                )
            }
        }
        if (email.isEmpty()) {
            return flow {
                emit(
                    Resource.Error(
                        message = UiText.StringResorce(R.string.error_email_empty),
                    )
                )
            }
        }
        return repository.updateUserInfo(
            oldPassword, newPassword, email
        )
    }
}