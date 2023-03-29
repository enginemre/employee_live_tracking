package com.hakmar.employeelivetracking.features.nfc_reader.ui

import android.nfc.NdefMessage
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.util.SnackBarType
import com.hakmar.employeelivetracking.util.UiEvent
import com.hakmar.employeelivetracking.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import kotlin.experimental.and

class NFCViewModel : ViewModel() {

    private var _state = MutableStateFlow(NFCState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun readingIntent(messages: Array<NdefMessage>) {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
        if (messages.isEmpty())
            viewModelScope.launch {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResorce(R.string.nfc_message_not_found),
                        SnackBarType.WARNING
                    )
                )
            }

        val payload = messages[0].records[0].payload
        val textEncoding: Charset =
            if ((payload[0] and 128.toByte()).toInt() == 0) Charsets.UTF_8 else Charsets.UTF_16 // Get the Text Encoding
        val languageCodeLength: Int = (payload[0] and 51).toInt()
        try {
            val text = String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                textEncoding
            )
            _state.update {
                it.copy(
                    isLoading = true,
                    data = text,
                    rawRes = R.raw.success,
                    descriptionText = R.string.nfc_success_message
                )
            }
            viewModelScope.launch {
                delay(1000)
                _uiEvent.send(
                    UiEvent.NfcReaded(
                        text
                    )
                )
            }
        } catch (e: UnsupportedEncodingException) {
            viewModelScope.launch {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(UiText.StringResorce(R.string.nfc_message_not_found),
                    SnackBarType.ERROR)
                )
            }
        }
    }
}