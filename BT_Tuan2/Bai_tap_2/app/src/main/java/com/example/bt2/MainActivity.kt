package com.example.bt2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    Practice03Screen()
                }
            }
        }
    }
}

@Composable
fun Practice03Screen() {
    var name by remember { mutableStateOf("") }
    var ageInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Khung nhập liệu
        Column(
            modifier = Modifier
                .background(Color(0xFFDADADA), shape = RoundedCornerShape(10.dp))
                .padding(20.dp)
                .fillMaxWidth(0.9f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Họ và tên", modifier = Modifier.width(100.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Tuổi", modifier = Modifier.width(100.dp))
                OutlinedTextField(
                    value = ageInput,
                    onValueChange = { ageInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                if (name.isBlank()) {
                    result = "Vui lòng nhập họ và tên"
                } else {
                    try {
                        val age = ageInput.toInt()
                        result = when {
                            age > 65 -> "$name là Người già"
                            age in 6..65 -> "$name là Người lớn"
                            age in 2..5 -> "$name là Trẻ em"
                            age < 2 -> "$name là Em bé"
                            else -> "Tuổi không hợp lệ"
                        }
                    } catch (e: Exception) {
                        result = "Tuổi phải là số hợp lệ!"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(text = "Kiểm tra", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Kết quả
        if (result.isNotEmpty()) {
            Text(
                text = result,
                fontSize = 18.sp,
                color = if (result.startsWith("Vui lòng") || result.contains("không")) Color.Red else Color.Black
            )
        }
    }
}
