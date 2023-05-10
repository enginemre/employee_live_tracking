package com.hakmar.employeelivetracking.features.store_detail.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun StoreInfoCard(
    storeName: String,
    storeCode: String,
    bsName: String,
    pmName: String,
    address: String
) {
    Card(
        Modifier
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
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = storeName,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Kod : $storeCode",
                color = Natural80,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bölge Sorumlusu : $bsName",

                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pazarlama Müdürü : $pmName",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Adres : $address",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

        }


    }
}


@Preview
@Composable
fun StoreInfoCardPrev() {
    Card(
        Modifier
            .widthIn(max = 400.dp)
            .fillMaxHeight(0.3f)
            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = CutCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pendik Fatih Esenyalı",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Kod : 5004",
                color = Natural80,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bölge Sorumlusu : Ahmet Kaya",

                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pazarlama Müdürü : İbrahim Şakar",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Adres : Çınardere Mah gençlik cad no 134 daire 16 kat 5 mer pendik istanbul ",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Natural80,
                    fontWeight = FontWeight.W500,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

        }


    }
}