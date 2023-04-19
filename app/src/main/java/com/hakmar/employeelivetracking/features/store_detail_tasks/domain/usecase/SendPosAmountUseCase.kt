package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.PosAmountRepository
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SendPosAmountUseCase @Inject constructor(
    private val repository: PosAmountRepository
) {
    operator fun invoke(
        storeCode: String,
        posAmount: String,
        cashierAmount: String,
        difference: String
    ): Flow<Resource<Unit>> {
        if (storeCode.isEmpty())
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_storeCode)))
        if (posAmount.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_posAmountTotal)))
        if (cashierAmount.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_cashier_amount)))
        if (difference.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_difference)))
        return repository.sendPosAmount(
            hashMapOf(
                DIFFERENCE_KEY to difference.toFloat(),
                POS_AMOUNT_KEY to posAmount.toFloat(),
                CASHIER_AMOUNT_KEY to cashierAmount.toFloat()
            )
        )
    }

    companion object {
        private const val DIFFERENCE_KEY = "difference"
        private const val POS_AMOUNT_KEY = "pos_amount"
        private const val CASHIER_AMOUNT_KEY = "cashier_amount"
    }
}