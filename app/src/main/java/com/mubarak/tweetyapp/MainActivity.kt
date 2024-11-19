package com.mubarak.tweetyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mubarak.tweetyapp.features.home.ui.TweetDetailsListingScreen
import com.mubarak.tweetyapp.features.home.ui.TweetListingScreen
import com.mubarak.tweetyapp.ui.theme.TweetyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TweetyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    App(modifier)
                }
            }
        }
    }
}

//navigation impl
@Composable
fun App(modifier: Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "categoryList") {

        composable(route = "categoryList") {
            TweetListingScreen(modifier) {
                navController.navigate("categoryDetails/$it")
            }
        }

        composable(
            route = "categoryDetails/{categoryName}",
            arguments = listOf(navArgument(name = "categoryName") {
                type = NavType.StringType
            })
        ) {
            TweetDetailsListingScreen(modifier) {
                navController.navigateUp()
            }
        }
    }
}