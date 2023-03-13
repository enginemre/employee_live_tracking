package com.hakmar.employeelivetracking.features.store_detail.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hakmar.employeelivetracking.common.presentation.ui.components.AppBarState
import com.hakmar.employeelivetracking.common.presentation.ui.theme.EmployeeLiveTrackingTheme
import com.hakmar.employeelivetracking.common.presentation.ui.theme.Natural80
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel
import com.hakmar.employeelivetracking.features.store_detail.ui.component.CompletedStatusProgressBar
import com.hakmar.employeelivetracking.features.store_detail.ui.component.LayoutTitle
import com.hakmar.employeelivetracking.features.store_detail.ui.component.StoreInfoCard
import com.hakmar.employeelivetracking.features.store_detail.ui.component.TaskCard
import com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel.StoreDetailViewModel

@Composable
fun StoreDetailScreen(
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onAppBarConfig: (AppBarState) -> Unit
) {
    val list = listOf<TaskModel>(
        TaskModel(
            name = "Çelik Kasa Sayım",
            isCompleted = true,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/2676/2676632.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle kasanın yanına git"
        ),
        TaskModel(
            name = "Mağaza Önü Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/609/609752.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
        TaskModel(
            name = "Mağaza İçi Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/3306/3306049.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
        TaskModel(
            name = "Z Raporları Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/9342/9342023.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
    )
    LaunchedEffect(key1 = true) {
        onAppBarConfig(
            AppBarState(
                title = "Mağaza Detay",
                isNavigationButton = true,
                navigationClick = onBackPressed
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StoreInfoCard(
            storeName = "Pendik Fatih Esenyalı",
            storeCode = "5004",
            bsName = "Ahmet Kaya",
            pmName = "İbrahim Şakar",
            address = "Çınardere mah gençlik cad no 16 daire 15 pendik İsitanbul"
        )
        Card(
            Modifier
                .widthIn(max = 400.dp)
                .align(Alignment.CenterHorizontally)
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
                    percentage = 12 / 25f,
                    taskCount = 25,
                    size = 100.dp
                )
            }
            Box(
                modifier = Modifier
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "Bügünkü görevlerinizi tamamlamaya erkenden başlayın ve hedeflerinize ulaşın",
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
        LayoutTitle(title = "Görevler", link = "Tümünü Gör")
        TaskList(list)

    }
}

@Composable
fun TaskList(
    list: List<TaskModel>
) {
    LazyRow(
        contentPadding = PaddingValues(
            top = MaterialTheme.spacing.medium,
            start = MaterialTheme.spacing.large,
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(list) {
            TaskCard(name = it.name, imageUrl = it.imageUrl, it.isCompleted)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showSystemUi = true)
@Composable
fun StoreDetailScreenPrev(
) {
    val list = listOf<TaskModel>(
        TaskModel(
            name = "Çelik Kasa Sayım",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/2676/2676632.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle kasanın yanına git"
        ),
        TaskModel(
            name = "Mağaza Önü Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/609/609752.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
        TaskModel(
            name = "Mağaza İçi Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/3306/3306049.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
        TaskModel(
            name = "Z Raporları Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/9342/9342023.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git"
        ),
    )
    EmployeeLiveTrackingTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                Modifier
                    .widthIn(max = 400.dp)
                    .height(225.dp)
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
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
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Natural80,
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
            Card(
                Modifier
                    .widthIn(max = 400.dp)
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CompletedStatusProgressBar(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                        percentage = 12 / 25f,
                        taskCount = 25,
                        size = 100.dp
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        text = "Bügünkü görevlerinizi tamamlamaya erkenden başlayın ve hedeflerinize ulaşın",
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
            Row(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .padding(
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.large
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Görevler", style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp
                    )
                )
                Text(
                    text = "Tümünü Gör", style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Natural80,
                        fontWeight = FontWeight.W400
                    )
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .widthIn(max = 500.dp),
                contentPadding = PaddingValues(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.large,
                    end = MaterialTheme.spacing.large
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(list) {
                    TaskCard(name = it.name, imageUrl = it.imageUrl, isCompleted = false)
                }
            }
        }
    }

}
