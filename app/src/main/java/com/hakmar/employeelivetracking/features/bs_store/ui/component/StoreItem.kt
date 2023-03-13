package com.hakmar.employeelivetracking.features.bs_store.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreCardItem(
    storeName: String,
    storeCode: String,
    taskCount: Int,
    completedTaskCount: Int,
    passingTime: String,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .heightIn(min = 100.dp)
            .width(365.dp),
        onClick = { onClick(storeCode) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.shop),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .padding(
                        top = MaterialTheme.spacing.small,
                        bottom = MaterialTheme.spacing.small,
                        start = MaterialTheme.spacing.small
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                StoreTitle(storeName = storeName, storeCode = storeCode)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.extraSmall),
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    StoreStatus(icon = R.drawable.ic_task, descriptiton = "$taskCount Görev")
                    StoreStatus(icon = R.drawable.ic_time, descriptiton = passingTime)
                }
                Spacer(modifier = Modifier.height(8.dp))
                TaskProgress(
                    progress = (completedTaskCount.toFloat() / taskCount.toFloat()),
                    ratioString = "$completedTaskCount/$taskCount"
                )
            }
        }


    }
}

@Composable
fun StoreTitle(storeName: String, storeCode: String) {
    Column {
        Text(
            text = storeName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            )
        )
        Text(
            text = storeCode,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W300,
                fontSize = 11.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
        )
    }

}

@Composable
fun TaskProgress(
    progress: Float,
    ratioString: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            color = MaterialTheme.colors.primary,
            progress = progress,
            modifier = Modifier
                .height(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .weight(7f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = ratioString,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W300,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = Natural80
            )
        )

    }
}

@Composable
fun StoreStatus(
    @DrawableRes icon: Int,
    descriptiton: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(14.dp),
            painter = painterResource(id = icon),
            contentDescription = descriptiton
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = descriptiton,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W300,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = Natural80
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun StoreCardItemPrev() {
    EmployeeLiveTrackingTheme {
        LazyColumn {
            items(5) {
                Card(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium)
                        .heightIn(min = 100.dp)
                        .width(365.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.man),
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .padding(
                                    top = MaterialTheme.spacing.small,
                                    bottom = MaterialTheme.spacing.small,
                                    start = MaterialTheme.spacing.small
                                )
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Fatih Esenyalı Mağazası",
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.W500,
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                    )
                                )
                                Text(
                                    text = "5004",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.W300,
                                        fontSize = 11.sp,
                                        lineHeight = 20.sp,
                                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                                    )
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = MaterialTheme.spacing.extraSmall),
                                horizontalArrangement = Arrangement.spacedBy(30.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        modifier = Modifier.size(14.dp),
                                        painter = painterResource(id = R.drawable.ic_task),
                                        contentDescription = "Task"
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "22 Görev",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.W300,
                                            fontSize = 11.sp,
                                            lineHeight = 16.sp,
                                            color = Natural80
                                        )
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        modifier = Modifier.size(14.dp),
                                        painter = painterResource(id = R.drawable.ic_time),
                                        contentDescription = "Task"
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "2 saat 12 dk",
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.W300,
                                            fontSize = 11.sp,
                                            lineHeight = 16.sp,
                                            color = Natural80
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LinearProgressIndicator(
                                    color = MaterialTheme.colors.primary,
                                    progress = 0.5f,
                                    modifier = Modifier
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .weight(7f)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "22/25",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.W300,
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp,
                                        color = Natural80
                                    )
                                )

                            }

                        }
                    }
                }
            }
        }
    }


}