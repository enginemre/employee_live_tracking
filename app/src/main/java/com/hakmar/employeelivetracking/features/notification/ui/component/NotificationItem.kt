package com.hakmar.employeelivetracking.features.notification.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun NotificationItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.man),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .padding(start = MaterialTheme.spacing.medium)
                .fillMaxWidth()
        ) {
            Text(
                text = "Yeni Sistemimize hoş geldiniz",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall))
            Text(
                text = "Bu bir deneme açıklaması ",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W400
                ),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.small))
            Text(
                text = "15 min ago", style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W400
                )
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 12.dp),
                thickness = 1.dp,
                Color(0xffA0A2B3)
            )

        }
    }
}

@Preview
@Composable
fun NotificationItemPrev() {
    EmployeeLiveTrackingTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.man),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Yeni Sistemimize hoş geldiniz",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall))
                Text(
                    text = "Bu bir deneme açıklaması ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Natural80,
                        fontWeight = FontWeight.W400
                    ),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.small))
                Text(
                    text = "15 min ago", style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Natural80,
                        fontWeight = FontWeight.W400
                    )
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 12.dp),
                    thickness = 1.dp,
                    Color(0xffA0A2B3)
                )

            }
        }
    }
}