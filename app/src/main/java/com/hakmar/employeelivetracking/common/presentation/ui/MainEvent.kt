package com.hakmar.employeelivetracking.common.presentation.ui

sealed class MainEvent {
    object OnQrDataNotRead : MainEvent()
    data class OnQRScan(val data: String?, val storeCode: String) : MainEvent()
    object OnNfcNotOpened : MainEvent()
    object OnNfcDataNotRead : MainEvent()
    data class OnNfcRead(val data: String?, val storeCode: String) : MainEvent()
}
