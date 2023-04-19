package com.hakmar.employeelivetracking.features.store_detail_tasks.domain.usecase

import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.features.store_detail_tasks.domain.repository.SteelCaseRepository
import com.hakmar.employeelivetracking.util.Resource
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SendSteelCaseUseCase @Inject constructor(
    private val repository: SteelCaseRepository
) {
    operator fun invoke(params: Params): Flow<Resource<String>> {
        if (params.storeCode.isEmpty())
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_storeCode)))
        if (params.cashTotal.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_cashTotal)))
        if (params.coinTotal.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_coinTotal)))
        if (params.posAmountTotal.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_posAmountTotal)))
        if (params.safeCurrentTotal.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_safeCurrentTotal)))
        if (params.difference.toFloatOrNull() == null)
            return flowOf(Resource.Error(message = UiText.StringResorce(R.string.error_difference)))
        return repository.sendSteelCaseRepository(
            hashMapOf(
                STORE_CODE_KEY to params.storeCode,
                CASH_TOTAL_KEY to params.cashTotal.toFloat(),
                POS_AMOUNT_KEY to params.posAmountTotal.toFloat(),
                COIN_TOTAL_KEY to params.coinTotal.toFloat(),
                STEEL_CASE_AMOUNT_KEY to params.safeCurrentTotal.toFloat(),
                DIFFERENCE_KEY to params.difference.toFloat()
            )
        )
    }

    companion object {
        private const val STORE_CODE_KEY = "store_code"
        private const val CASH_TOTAL_KEY = "cash_total"
        private const val COIN_TOTAL_KEY = "coin_total"
        private const val POS_AMOUNT_KEY = "pos_amount_total"
        private const val EXPENSES_TOTAL_KEY = "expenses_total"
        private const val STEEL_CASE_AMOUNT_KEY = "safe_current_balance"
        private const val CASHBOOK_BALANCE_KEY = "cashbook_balance"
        private const val DIFFERENCE_KEY = "difference"
    }

    data class Params(
        val storeCode: String,
        val cashTotal: String,
        val coinTotal: String,
        val posAmountTotal: String,
        val safeCurrentTotal: String,
        val difference: String
    )
}