package com.mubarak.tweetyapp.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mubarak.tweetyapp.features.home.viewmodel.TweetsFlowViewModel

@Composable
fun TweetDetailsListingScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    //val viewModel: TweetsViewModel = hiltViewModel()
    val viewModel: TweetsFlowViewModel = hiltViewModel()
    val result = viewModel.tweets.collectAsState()

    Box {
        Column(modifier = modifier) {
            TopBarWithBackButton(title = viewModel.getTweetsTitle(), onBackClick = onBackClick)

            if (result.value.isEmpty()) {
                LoaderShow(modifier = Modifier.fillMaxSize())
            } else {
                LazyColumn(modifier = Modifier, content = {
                    items(result.value) {
                        TweetDetailsScreenItem(itemTitle = it.text)
                    }
                })
            }
        }
    }
}

@Composable
fun TweetDetailsScreenItem(itemTitle: String, modifier: Modifier = Modifier) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = itemTitle, Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview_TweetDetailsScreenItem() {
    TweetDetailsScreenItem("Disaster recovery with cloud backups ensures business continuity \uD83C\uDF29\uFE0F\uD83D\uDD12")
}

@Composable
fun TopBarWithBackButton(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Title text
        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(end = 30.dp) // Ensures title takes available space
        )
    }
}

@Composable
fun LoaderShow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}