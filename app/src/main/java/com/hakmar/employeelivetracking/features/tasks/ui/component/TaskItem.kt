package com.hakmar.employeelivetracking.features.tasks.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@ExperimentalFoundationApi
@Composable
fun TaskItem(
    color: Color,
    storeCode: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            ),
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.spacing.extraSmall,
                        horizontal = MaterialTheme.spacing.small
                    ),
                    text = title, style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 24.sp
                    )
                )
                Text(
                    modifier = Modifier.padding(
                        vertical = MaterialTheme.spacing.extraSmall,
                        horizontal = MaterialTheme.spacing.small
                    ),
                    text = storeCode, style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 24.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W200,
                    lineHeight = 16.sp
                ),
                text = description

            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun TaskItemPrev() {
    EmployeeLiveTrackingTheme {
        Card(
            modifier = Modifier.wrapContentHeight(),
            shape = CutCornerShape(topEnd = MaterialTheme.spacing.medium),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colors.primary.copy(alpha = 0.5f)
            )
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                    text = "Title Of Task", style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp
                    )
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W200,
                        lineHeight = 16.sp
                    ),
                    text = "Lorem ipsum dolor sit amet, " +
                            "consectetuer adipiscing elit. Aenean commodo " +
                            "ligula eget dolor. Aenean massa. Cum sociis natoque penatibus"

                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
    }
}