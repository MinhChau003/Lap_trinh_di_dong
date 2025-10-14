package com.example.bt_trenlop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MyFirstApp()
                }
            }
        }
    }
}

@Composable
fun MyFirstApp() {
    // Trạng thái hiển thị
    var showGreeting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "My First App",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Dòng chữ ở giữa
        Text(
            text = if (showGreeting) "Hello, everyone!" else "I'm Nguyen Minh Chau",
            color = if (showGreeting) Color.Blue else Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Nút Say Hi
        Button(
            onClick = { showGreeting = !showGreeting },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
        ) {
            Text(text = "Say Hi!", color = Color.White, fontSize = 18.sp)
        }
    }
}
