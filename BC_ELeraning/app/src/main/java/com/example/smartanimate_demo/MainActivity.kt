package com.example.smartanimate_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManHinhChinh()
        }
    }
}

/* -------------------------------------------------------------
   MÀN HÌNH CHÍNH
------------------------------------------------------------- */

@Composable
fun ManHinhChinh() {
    var trang by remember { mutableStateOf("home") }

    when (trang) {
        "home" -> TrangLuaChon(onSelect = { trang = it })
        "no_anim" -> ManHinhKhongHieuUng(onBack = { trang = "home" })
        "with_anim" -> ManHinhCoHieuUng(onBack = { trang = "home" })
        "no_transition" -> ManHinhChuyenKhongHieuUng(onBack = { trang = "home" })
        "with_transition" -> ManHinhChuyenCoHieuUng(onBack = { trang = "home" })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrangLuaChon(onSelect: (String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demo Chương 9 – Transitions & Animations", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onSelect("no_anim") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ảnh: KHÔNG hiệu ứng") }

            Button(
                onClick = { onSelect("with_anim") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) { Text("Ảnh: CÓ hiệu ứng (Smart-Animate)") }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onSelect("no_transition") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Chuyển cảnh: KHÔNG hiệu ứng") }

            Button(
                onClick = { onSelect("with_transition") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) { Text("Chuyển cảnh: CÓ hiệu ứng (Smart-Animate)") }
        }
    }
}

/* -------------------------------------------------------------
   PHẦN 1 — DEMO ẢNH
------------------------------------------------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhKhongHieuUng(onBack: () -> Unit) {

    var moRong by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Không có hiệu ứng (Ảnh)", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painterResource(android.R.drawable.ic_menu_revert),
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            Text("Nhấn ảnh để mở rộng – KHÔNG hiệu ứng",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val height = if (moRong) 280.dp else 120.dp

            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_camera),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
                    .clickable { moRong = !moRong },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhCoHieuUng(onBack: () -> Unit) {

    var moRong by remember { mutableStateOf(false) }

    val chieuCaoAnh by animateDpAsState(
        targetValue = if (moRong) 300.dp else 120.dp,
        animationSpec = TweenSpec(durationMillis = 600)
    )

    val tyLeScale by animateFloatAsState(
        targetValue = if (moRong) 1.05f else 1f,
        animationSpec = TweenSpec(durationMillis = 600)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Có hiệu ứng (Ảnh)", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painterResource(android.R.drawable.ic_menu_revert),
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            Text("Nhấn ảnh mở rộng – CÓ animation (Smart-Animate style)",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_camera),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chieuCaoAnh)
                    .scale(tyLeScale)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray)
                    .clickable { moRong = !moRong },
                contentScale = ContentScale.Crop
            )
        }
    }
}

/* -------------------------------------------------------------
   PHẦN 2 — DEMO CHUYỂN CẢNH
------------------------------------------------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhChuyenKhongHieuUng(onBack: () -> Unit) {

    var laDo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chuyển cảnh KHÔNG hiệu ứng", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painterResource(android.R.drawable.ic_menu_revert),
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Button(
                onClick = { laDo = !laDo },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (laDo) Color.Red else Color(0xFF1E88E5)
                )
            ) {
                Text("Bấm đổi màu (Không hiệu ứng)")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManHinhChuyenCoHieuUng(onBack: () -> Unit) {

    var laDo by remember { mutableStateOf(false) }

    val mauNut by animateColorAsState(
        targetValue = if (laDo) Color.Red else Color(0xFF1E88E5),
        animationSpec = tween(600)
    )

    val khoangCachY by animateDpAsState(
        targetValue = if (laDo) 120.dp else 0.dp,
        animationSpec = tween(600)
    )

    val tyLeScale by animateFloatAsState(
        targetValue = if (laDo) 1.1f else 1f,
        animationSpec = tween(600)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chuyển cảnh CÓ hiệu ứng (Smart-Animate)", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painterResource(android.R.drawable.ic_menu_revert),
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2)
                )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(khoangCachY))

                Button(
                    onClick = { laDo = !laDo },
                    modifier = Modifier.scale(tyLeScale),
                    colors = ButtonDefaults.buttonColors(containerColor = mauNut)
                ) {
                    Text("Bấm đổi màu + dịch xuống (Có hiệu ứng)")
                }
            }
        }
    }
}
