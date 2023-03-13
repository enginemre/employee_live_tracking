package com.hakmar.employeelivetracking.features.store_detail.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hakmar.employeelivetracking.R
import com.hakmar.employeelivetracking.common.presentation.ui.theme.colors
import com.hakmar.employeelivetracking.common.presentation.ui.theme.spacing

@Composable
fun TaskCard(
    name: String,
    imageUrl: String,
    isCompleted: Boolean
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .heightIn(max = 170.dp)
            .width(150.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            if (isCompleted) MaterialTheme.colors.primary
            else Color.White
        )
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = MaterialTheme.spacing.small)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    top = MaterialTheme.spacing.small
                ),
            text = name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.sp
            )
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Icon(
                modifier = Modifier.padding(
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.small
                ), imageVector = Icons.Default.Info, contentDescription = "info"
            )
        }
    }
}


@Preview
@Composable
fun TaskCardPrev() {
    Card(
        modifier = Modifier
            .heightIn(max = 170.dp)
            .width(150.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 10.dp),
            painter = painterResource(id = R.drawable.man),
            contentDescription = ""
        )
        /*AsyncImage(
            model = ImageRequest.Builder(context)
                .data("https://cdn-icons-png.flaticon.com/512/2676/2676632.png")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape).fillMaxWidth()
                .height(100.dp)
                .padding(top = 10.dp)
        )*/
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 8.dp
                ),
            text = "Çelik Kasa Sayımı",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.sp
            )
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Icon(
                modifier = Modifier.padding(
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.small
                ), imageVector = Icons.Default.Info, contentDescription = "info"
            )
        }
    }
}