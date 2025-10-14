package com.example.bt_tl2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.bt_tl2.ui.theme.ComposeIntroTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeIntroTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IntroScreen()
                }
            }
        }
    }
}

@Composable
fun IntroScreen() {
    var clicked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Nội dung chính: logo + text
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 80.dp), // để chừa chỗ cho nút ở dưới
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_compose_logo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Jetpack Compose",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        Button(
            onClick = { clicked = !clicked },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (clicked) Color(0xFF4CAF50) else Color(0xFF2196F3)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter) // nằm ở dưới
                .fillMaxWidth()
                .padding(bottom = 24.dp) // cách mép dưới 24dp
        ) {
            Text(
                text = if (clicked) "Let's go!" else "I'm ready",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}