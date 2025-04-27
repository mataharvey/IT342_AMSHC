@file:OptIn(ExperimentalMaterial3Api::class) // ✅ Accept Experimental API at the top

package com.example.amshc.uii

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "John Doe",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold // ✅ Corrected here
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Options List
            ProfileOptionItem("Profile") { /* TODO: Navigate to Edit Profile Screen */ }
            ProfileOptionItem("Favorite") { navController.navigate("favorite") }
            ProfileOptionItem("Payment Method") { navController.navigate("paymentMethod") }
            ProfileOptionItem("Privacy Policy") { navController.navigate("privacyPolicy") }
            ProfileOptionItem("Settings") { navController.navigate("settings") }
            ProfileOptionItem("Help") { navController.navigate("helpCenter") }
            ProfileOptionItem("Logout") { navController.navigate("logoutConfirm") }
        }
    }
}

@Composable
fun ProfileOptionItem(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
