package com.hakmar.employeelivetracking.features.navigation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationStoreCard(
    modifier: Modifier = Modifier,
    storeCode: String,
    storeName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(60.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        onClick = onClick

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(modifier = Modifier.weight(1f)) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.shop),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.medium)
                ) {
                    Text(
                        text = storeName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = storeCode, style = MaterialTheme.typography.bodySmall.copy(
                            color = Natural80,
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }

            Image(
                modifier = Modifier
                    .size(25.dp)
                    .weight(0.2f),
                painter = painterResource(id = R.drawable.direction),
                contentDescription = null
            )
        }
    }
}


@Preview
@Composable
fun NavigationStoreCardPrev() {
    EmployeeLiveTrackingTheme {
        Card(
            modifier = Modifier
                .height(60.dp), colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.shop),
                        contentDescription = ""
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.medium)
                    ) {
                        Text(
                            text = "Fatih Esenyalı Pendik ",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = "5004", style = MaterialTheme.typography.bodySmall.copy(
                                color = Natural80,
                                fontWeight = FontWeight.W400,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }

                Image(
                    modifier = Modifier
                        .size(25.dp)
                        .weight(0.2f),
                    painter = painterResource(id = R.drawable.direction),
                    contentDescription = null
                )
            }
        }
    }
}