package com.example.bt_th1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bt_th1.ui.theme.ComposeIntroTheme

// ---------------- MAIN ----------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeIntroTheme {
                AppNavigator()
            }
        }
    }
}

// ---------------- NAVIGATION ----------------
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "intro") {
        composable("intro") { IntroScreen(navController) }
        composable("list") { ListScreen(navController) }
        composable("columnDemo") { ColumnDemoScreen() }
        composable("lazyColumnDemo") { LazyColumnDemoScreen(navController) }
    }
}

// ---------------- INTRO SCREEN ----------------
@Composable
fun IntroScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_compose_logo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Jetpack Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Jetpack Compose is a modern UI toolkit for building native Android apps using a declarative approach.",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        Button(
            onClick = { navController.navigate("list") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text("Push", color = Color.White, fontSize = 18.sp)
        }
    }
}

// ---------------- LIST SCREEN ----------------
@Composable
fun ListScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Choose Layout Type", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable { navController.navigate("columnDemo") },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Column", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable { navController.navigate("lazyColumnDemo") },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("LazyColumn", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ---------------- COLUMN DEMO ----------------
@Composable
fun ColumnDemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Column Demo", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        // Tạo 100 item — KHÔNG cuộn được, sẽ bị tràn
        for (i in 1..1_000_000) {
            Text("Item $i", fontSize = 16.sp)
        }
    }
}

// ---------------- LAZY COLUMN DEMO ----------------
@Composable
fun LazyColumnDemoScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            "LazyColumn Demo",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items((1..1_000_000).toList()) { i ->
                Text(
                    text = "Item $i",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    fontSize = 16.sp
                )
                Divider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("intro") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text("Back to Root", color = Color.White, fontSize = 18.sp)
        }
    }
}
