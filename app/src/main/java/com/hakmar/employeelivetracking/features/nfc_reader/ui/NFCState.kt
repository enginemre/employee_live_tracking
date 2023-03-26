package com.hakmar.employeelivetracking.features.nfc_reader.ui

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.hakmar.employeelivetracking.R

data class NFCState(
    var data : String = "",
    var isLoading : Boolean = false,
    @RawRes var rawRes : Int = R.raw.nfc_card_read,
    @StringRes var descriptionText : Int = R.string.nfc_description
)
