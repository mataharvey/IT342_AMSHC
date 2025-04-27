@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.amshc.uii

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") }
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
                text = "Privacy Policy",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = """
                    Welcome to AMSHC!
                    
                    We value your privacy. 
                    Your data is securely stored and will never be shared with third parties without your consent.
                    
                    By using our services, you agree to our collection and use of information in accordance with this policy.
                    
                    If you have any questions, contact our support team.
                """.trimIndent(),
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
        }
    }
}
