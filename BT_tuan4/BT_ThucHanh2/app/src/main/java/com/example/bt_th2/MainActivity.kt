package com.example.bt_th2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.example.bt_th2.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding1") { OnboardingScreen1(navController) }
        composable("onboarding2") { OnboardingScreen2(navController) }
        composable("onboarding3") { OnboardingScreen3(navController) }
        composable("home") { HomeScreen() }
    }
}

// ---------------- SPLASH SCREEN ----------------
@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000L)
        navController.navigate("onboarding1") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("UTH SmartTasks", fontSize = 20.sp)
        }
    }
}

// ---------------- ONBOARDING SCREENS ----------------
@Composable
fun OnboardingScreen1(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        // Nội dung chính
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(130.dp))
            Text("Easy Time Management", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.weight(1f)) // đẩy nút xuống dưới

        // Nút Next
        Button(
            onClick = { navController.navigate("onboarding2") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

@Composable
fun OnboardingScreen2(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Nút Skip góc phải trên
        TextButton(
            onClick = { navController.navigate("home") { popUpTo("splash") { inclusive = true } } },
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Text("Skip")
        }

        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Text("Increase Work Effectiveness", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.weight(1f)) // đẩy nút xuống dưới

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }
                Button(onClick = { navController.navigate("onboarding3") }) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
fun OnboardingScreen3(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Nút Skip góc phải trên
        TextButton(
            onClick = { navController.navigate("home") { popUpTo("splash") { inclusive = true } } },
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Text("Skip")
        }

        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Text("Reminder Notification", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.weight(1f)) // đẩy nút xuống dưới

            Button(
                onClick = { navController.navigate("home") { popUpTo("splash") { inclusive = true } } },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Get Started")
            }
        }
    }
}

// ---------------- HOME SCREEN ----------------
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to Home Screen", fontSize = 24.sp)
    }
}
