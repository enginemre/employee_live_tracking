package com.hakmar.employeelivetracking.features.store_detail.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun TaskProgressCard(
    modifier: Modifier,
    taskCount: Int,
    completedTask: Int
) {
    Card(
        modifier
            .widthIn(max = 400.dp)
            .padding(
                top = MaterialTheme.spacing.large,
                start = MaterialTheme.spacing.large,
                end = MaterialTheme.spacing.large
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.Center
        ) {
            CompletedStatusProgressBar(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                percentage = completedTask / taskCount.toFloat(),
                taskCount = taskCount,
                size = 100.dp
            )
        }
        Box(
            modifier = Modifier
                .padding(12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = stringResource(id = R.string.motivation_for_task),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Natural80,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}