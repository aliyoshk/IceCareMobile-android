package com.example.icecaremobile.presentation.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.icecaremobile.R
import com.example.icecaremobile.presentation.navigator.Screen
import com.example.icecaremobile.ui.theme.DarkGolden


@Composable
fun AppBottomNavigationBar(
    navController: NavHostController
) {
    val screens = listOf(
        Triple(Screen.DashboardScreen, R.drawable.ic_home, "Dashboard"),
        Triple(Screen.SendMoneyScreen, R.drawable.ic_add, "Send Money"),
        Triple(Screen.ChatScreen, R.drawable.ic_chat, "Chat")
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val shouldShowNavBar = screens.any { it.first::class.qualifiedName == currentRoute?.route }

    if (shouldShowNavBar) {
        NavigationBar(
            containerColor = Color.White,
        ) {
            screens.forEach { screen ->
                NavigationBarItem(
                    label = { Text(text = screen.third) },
                    icon = {
                        Icon(
                            painter = painterResource(screen.second),
                            contentDescription = ""
                        )
                    },
                    selected = currentRoute?.route == screen.first::class.qualifiedName!!,
                    onClick = {
                        navController.navigate(screen.first::class.qualifiedName!!) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.Gray,
                        selectedTextColor = DarkGolden,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.Gray.copy(0.5f),
                    ),
                )
            }
        }
    }
}
