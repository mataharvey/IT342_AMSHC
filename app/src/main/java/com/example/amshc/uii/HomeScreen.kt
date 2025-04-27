package com.example.amshc.uii

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Doctor(
    val name: String,
    val specialization: String,
    val rating: Double,
    val chatCount: Int
)

@Composable
fun HomeScreen(navController: NavController) {   // 🆕 now accepts navController
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // 15 Sample Doctors
    val doctors = listOf(
        Doctor("Dr. Olivia Turner", "Dermatology", 5.0, 60),
        Doctor("Dr. James Smith", "Pediatrics", 4.8, 45),
        Doctor("Dr. Sophia Johnson", "Cardiology", 4.9, 52),
        Doctor("Dr. Liam Brown", "Neurology", 5.0, 70),
        Doctor("Dr. Emma Wilson", "Orthopedics", 4.7, 40),
        Doctor("Dr. Benjamin Miller", "Radiology", 4.8, 33),
        Doctor("Dr. Mia Davis", "ENT Specialist", 5.0, 55),
        Doctor("Dr. Noah Garcia", "Endocrinology", 4.6, 28),
        Doctor("Dr. Ava Martinez", "Pulmonology", 4.9, 61),
        Doctor("Dr. Lucas Rodriguez", "Ophthalmology", 5.0, 38),
        Doctor("Dr. Charlotte Lee", "Gastroenterology", 4.8, 47),
        Doctor("Dr. Logan Perez", "Psychiatry", 4.7, 50),
        Doctor("Dr. Harper Harris", "Anesthesiology", 4.9, 43),
        Doctor("Dr. Elijah Clark", "Nephrology", 5.0, 35),
        Doctor("Dr. Amelia Lewis", "Oncology", 4.8, 58)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🧢 Top Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.navigate("profile") } // 🆕 Navigate to ProfileScreen
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Hi, WelcomeBack", fontSize = 14.sp, color = Color.Gray)
                Text(text = "John Doe", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Notification */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
            IconButton(onClick = { /* Settings */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔍 Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search doctors...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 📅 Calendar Section
        CalendarSection()

        Spacer(modifier = Modifier.height(24.dp))

        // 🧑‍⚕️ Doctor List Section (VERTICAL Scroll!)
        Text(
            text = "Available Doctors",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(doctors) { doctor ->
                DoctorItem(doctor)
            }
        }
    }
}

@Composable
fun CalendarSection() {
    val today = LocalDate.now()
    val dates = (0..14).map { today.plusDays(it.toLong()) }
    val formatter = DateTimeFormatter.ofPattern("d EEE")

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(dates) { date ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                modifier = Modifier
                    .size(width = 70.dp, height = 70.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.format(formatter),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E5BFF)
                    )
                }
            }
        }
    }
}

@Composable
fun DoctorItem(doctor: Doctor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDDE8FF)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Doctor",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = doctor.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = doctor.specialization, fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "${doctor.rating}", fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Chat",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "${doctor.chatCount}", fontSize = 13.sp)
                }
            }
        }
    }
}
