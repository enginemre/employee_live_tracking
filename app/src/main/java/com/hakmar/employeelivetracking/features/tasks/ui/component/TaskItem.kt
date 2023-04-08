package com.hakmar.employeelivetracking.features.tasks.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    color: Color,
    storeCode: String,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.wrapContentHeight(),
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = onClick
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