package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Green40
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural100

@Composable
fun LargeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Natural100,
    containerColor: Color = Green40
) {

    Button(
        modifier = modifier
            .height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor
        )
    ) {
        Text(
            text = text, style = MaterialTheme.typography.bodyMedium
        )
    }


}


@Preview
@Composable
fun LargeButtonPrev() {
    EmployeeLiveTrackingTheme {
        Box(
            modifier = Modifier
                .height(50.dp)
        ) {
            Button(
                modifier = Modifier.widthIn(min = 300.dp, max = 350.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green40,
                    contentColor = Natural100
                )


            ) {

                Text(
                    text = "Giriş", style = MaterialTheme.typography.bodyMedium
                )

            }
        }

    }
}