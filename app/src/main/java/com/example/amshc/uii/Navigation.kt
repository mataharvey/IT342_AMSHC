package com.example.amshc.uii

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onLoginClick = { navController.navigate("login") },
                onSignUpClick = { navController.navigate("signup") }
            )
        }
        composable("login") {
            LoginScreen(
                navController = navController,
                onForgotPasswordClick = { /* TODO: Navigate to forgot password */ },
                onSignUpClick = { navController.navigate("signup") },
                onBackClick = { navController.navigate("welcome") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpClick = { /* TODO: Handle signup */ },
                onLoginClick = { navController.navigate("login") },
                onBackClick = { navController.navigate("welcome") }
            )
        }
        composable("home") {
            HomeScreen(navController = navController)  // ✅ CORRECT
        }
        composable("profile") {   // 🆕 Make sure THIS exists!
            ProfileScreen(navController = navController)
        }
        composable("favorite") {
            FavoriteScreen(navController = navController)
        }
        composable("paymentMethod") {
            PaymentMethodScreen(navController = navController)
        }
        composable("privacyPolicy") {
            PrivacyPolicyScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        composable("helpCenter") {
            HelpCenterScreen(navController = navController)
        }
        composable("logoutConfirm") {
            LogoutConfirmScreen(navController = navController)
        }






    }
}
