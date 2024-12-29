package com.example.icecaremobile

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.icecaremobile.presentation.navigator.Route
import com.example.icecaremobile.ui.theme.IceCareMobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var sessionTimeoutTimer: CountDownTimer? = null
    private val timeoutDuration = 10 * 1000L

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            IceCareMobileTheme {
                Route()
            }
        }
    }

//    override fun onUserInteraction() {
//        super.onUserInteraction()
//        resetSessionTimeout()
//    }
//
//    private fun resetSessionTimeout() {
//        sessionTimeoutTimer?.cancel()
//        sessionTimeoutTimer = object : CountDownTimer(timeoutDuration, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                // Optional: Log remaining time or update UI
//            }
//
//            @RequiresApi(Build.VERSION_CODES.O)
//            override fun onFinish() {
//                handleSessionTimeout()
//            }
//        }.start()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun handleSessionTimeout() {
//        // Clear session data
//        clearSessionData()
//
//        // Navigate back to the login screen
//        // Ensure that the navigation stack is cleared
//        setContent {
//            IceCareMobileTheme {
//                val navController = rememberNavController()
//                val excludedRoutes = listOf(Screen.LandingScreen, Screen.LoginScreen, Screen.RegistrationScreen)
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentRoute = navBackStackEntry?.destination?.route
//                val exclude = excludedRoutes.any { it::class.qualifiedName == currentRoute }
//
//                Log.d("Screen name", "boolean $exclude")
//
//                if (exclude) {
//                    navController.navigate(Screen.LandingScreen) {
//                        popUpTo(Screen.LandingScreen) { inclusive = true } // Clear the back stack
//                    }
//                }
//            }
//        }
//    }
//
//    private fun clearSessionData() {
//        // Clear user session, preferences, or token here
//        // Example: AuthManager.clearSession()
//        Toast.makeText(this, "Session timed out", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        resetSessionTimeout()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        sessionTimeoutTimer?.cancel()
//    }
}