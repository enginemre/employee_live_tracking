@file:OptIn(ExperimentalMaterial3Api::class)

package com.hakmar.employeelivetracking.common.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.*

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    hint: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    isVisible: Boolean = false,
    trailIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Next,
    onFocused: ((FocusState) -> Unit)? = null,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    onNext: (KeyboardActionScope.() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = Natural100
            )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        OutlinedTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .padding(horizontal = MaterialTheme.spacing.medium)
                .onFocusEvent { onFocused?.invoke(it) },
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Natural80,
                        fontWeight = FontWeight.Light
                    )
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = onDone,
                onNext = onNext
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            leadingIcon = leadingIcon,
            trailingIcon = trailIcon,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Green40,
                unfocusedIndicatorColor = Natural40,
                focusedTextColor = Natural90,
                unfocusedTextColor = Natural90
            )
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun CustomTextFieldPrev(

) {
    EmployeeLiveTrackingTheme {

        Column(
            modifier = Modifier.widthIn(
                min = 100.dp,
                max = 300.dp
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
                text = "Email",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Natural100
                )
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            OutlinedTextField(
                value = TextFieldValue(""),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .padding(horizontal = MaterialTheme.spacing.medium),
                onValueChange = {},
                placeholder = {
                    Text(
                        text = "emre@engin",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Natural80,
                            fontWeight = FontWeight.Light
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),

                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp

                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Green40,
                    unfocusedIndicatorColor = Natural40,
                    focusedTextColor = Natural90,
                    unfocusedTextColor = Natural90
                )
            )
        }


    }
}