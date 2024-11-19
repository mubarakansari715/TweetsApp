package com.mubarak.tweetyapp.features.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mubarak.tweetyapp.R
import com.mubarak.tweetyapp.features.home.viewmodel.TweetsFlowViewModel

@Composable
fun TweetListingScreen(modifier: Modifier = Modifier, onItemClick: (String) -> Unit) {
//    val viewModel: TweetsViewModel = hiltViewModel()
    val viewModel: TweetsFlowViewModel = hiltViewModel()
    val result = viewModel.categories.collectAsState()

    Box {
        Column(modifier = modifier.padding(8.dp)) {
            if (result.value?.isEmpty() == true) {
                LoaderShow(modifier = Modifier.fillMaxSize())
            } else {
                TopBarWithTitleOnly("Tweets Category")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(result.value!!.distinct()) {

                        TweetItem(categoryName = it, Modifier, onItemClick)
                    }
                }
            }
        }
    }
}

@Composable
fun TweetItem(categoryName: String, modifier: Modifier = Modifier, onItemClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .clickable { onItemClick(categoryName) }
            .padding(10.dp)
            .size(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .paint(painterResource(id = R.drawable.ic_bgs), contentScale = ContentScale.Crop),
        contentAlignment = Alignment.BottomCenter

    ) {
        Text(
            text = categoryName,
            modifier = Modifier
                .padding(vertical = 20.dp),
            fontSize = 18.sp,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
fun preview_TweetItem() {
    TweetItem("Android", onItemClick = {})
}

@Composable
fun TopBarWithTitleOnly(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title text
        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(end = 30.dp)
        )
    }
}