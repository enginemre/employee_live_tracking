package com.hakmar.employeelivetracking.features.store_detail_tasks.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreCheckCard(
    modifier: Modifier,
    checked: Boolean,
    description: String,
    onChecked: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .widthIn(max = 400.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(start = MaterialTheme.spacing.medium),
                text = description,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Natural80
                )
            )
            Checkbox(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .padding(end = MaterialTheme.spacing.medium),
                checked = checked,
                onCheckedChange = {
                    onChecked(it)
                })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StoreCheckCardPrev() {
    EmployeeLiveTrackingTheme {
        Card(
            modifier = Modifier
                .widthIn(max = 400.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(start = MaterialTheme.spacing.medium),
                    text = "Görev açıklaması buraya gelecek burda önemli bir şeyler yazacak yani",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Natural80
                    )
                )
                Checkbox(
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .padding(end = MaterialTheme.spacing.medium),
                    checked = true,
                    onCheckedChange = {})
            }
        }
    }
}