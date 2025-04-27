@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.amshc.uii

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Settings",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val settingsOptions = listOf(
                "Notification Settings",
                "Password Manager",
                "Theme Settings (Coming Soon)"
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(settingsOptions) { setting ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        onClick = {
                            when (setting) {
                                "Notification Settings" -> navController.navigate("notificationSettings")
                                "Password Manager" -> navController.navigate("passwordManager")
                                else -> { /* No action yet */ }
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.CenterStart
                        ) {
                            Text(
                                text = setting,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
