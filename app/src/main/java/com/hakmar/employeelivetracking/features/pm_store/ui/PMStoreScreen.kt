package com.hakmar.employeelivetracking.features.pm_store.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.pm_store.ui.component.PmStoreCard
import com.hakmar.employeelivetracking.features.pm_store.ui.model.PmStoreModel

@Composable
fun PmStoreScreen() {
    val list = listOf<PmStoreModel>(
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(

            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.large),
            columns = GridCells.Fixed(2),
            content = {
                items(list) { item ->
                    Box(
                        modifier = Modifier
                            .padding(MaterialTheme.spacing.small)
                    ) {
                        PmStoreCard(item.storeCode, item.storeName)
                    }

                }
            }
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PmScreenPrev() {
    val list = listOf<PmStoreModel>(
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
        PmStoreModel(
            storeCode = "5004",
            storeName = "Fatih Esenyalı"
        ),
        PmStoreModel(
            storeCode = "5024",
            storeName = "GüzelYalı Pendil"
        ),
    )
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.large),
                content = {
                    items(list) { item ->
                        Box(
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.small)
                        ) {
                            PmStoreCard(item.storeCode, item.storeName)
                        }

                    }
                }
            )
        }
    }
}