package com.hakmar.employeelivetracking.features.pm_store.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PmStoreCard(
    storeCode: String,
    storeName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(180.dp)
            .width(150.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.spacing.medium
        ),
        shape = CutCornerShape(MaterialTheme.spacing.small),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { onClick() }
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = MaterialTheme.spacing.small),
            painter = painterResource(id = R.drawable.shop),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = MaterialTheme.spacing.small
                ),
            text = storeName,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.sp
            )
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = MaterialTheme.spacing.small
                ),
            text = storeCode,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W300,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
        )
    }
}


@Preview
@Composable
fun PmStoreCardPrev() {
    EmployeeLiveTrackingTheme {
        Card(
            modifier = Modifier
                .height(180.dp)
                .width(150.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            shape = CutCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 10.dp),
                painter = painterResource(id = R.drawable.shop),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 8.dp
                    ),
                text = "Fatih Esenyalı Güzelyalı Pendik",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 16.sp
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                text = "5004",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W300,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            )
        }
    }
}