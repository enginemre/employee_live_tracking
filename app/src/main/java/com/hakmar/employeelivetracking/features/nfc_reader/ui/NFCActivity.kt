package com.hakmar.employeelivetracking.features.nfc_reader.ui

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural110
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.util.AppConstants
import com.hakmar.employeelivetracking.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NFCActivity : ComponentActivity() {

    private var tag: Tag? = null

    private val viewModel: NFCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeLiveTrackingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.state.collectAsStateWithLifecycle()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val composition by rememberLottieComposition(
                            spec = LottieCompositionSpec.RawRes(
                                state.value.rawRes
                            )
                        )
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                        Text(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.large),
                            text = stringResource(id = state.value.descriptionText),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Natural110,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                }
            }
        }
        collectData()
        handleIntent(intent)
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.uiEvent.flowWithLifecycle(lifecycle).collect { event ->
                when (event) {
                    is UiEvent.NfcReaded -> {
                        val resultIntent = Intent().apply {
                            putExtra(AppConstants.NFC_DATA, event.data)
                        }
                        setResult(RESULT_OK,resultIntent)
                        finish()
                    }
                    else -> {}
                }
            }
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }


    private fun handleIntent(intent: Intent?) {
        val action = intent?.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
            || NfcAdapter.ACTION_TECH_DISCOVERED == action
            || NfcAdapter.ACTION_NDEF_DISCOVERED == action
        ) {
            tag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            val msgs = mutableListOf<NdefMessage>()
            if (rawMsgs != null) {
                for (i in rawMsgs.indices) {
                    msgs.add(i, rawMsgs[i] as NdefMessage)
                }
                viewModel.readingIntent(msgs.toTypedArray())
            }
        }
    }
}