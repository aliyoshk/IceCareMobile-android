package com.example.icecaremobile.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.icecaremobile.presentation.screen.LandingScreen
import com.example.icecaremobile.presentation.screen.LoginScreen
import com.example.icecaremobile.presentation.screen.RegistrationScreen

@Composable
fun Route()
{
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LandingScreen.route
    )
    {
        composable(route = Screen.LandingScreen.route)
        {
            LandingScreen(navController)
        }
        composable(route = Screen.LoginScreen.route)
        {
            LoginScreen(navController)
        }
        composable(Screen.RegistrationScreen.route)
        {
            RegistrationScreen(navController)
        }
        composable(Screen.DashboardScreen.route)
        {
            //DashboardScreen(navController)
        }
//        composable(
//            Screen.DetailsScreen.route + "/{bundle}",
//            arguments = listOf(
//                navArgument("data")
//                {
//                    type = NavType.StringType
//                    defaultValue = "Empty value return"
//                    nullable = true
//                }
//            )
//        )
//        {
//            val data = it.arguments?.getString("bundle")!!
//            Detail(data = data)
//        }
    }
}