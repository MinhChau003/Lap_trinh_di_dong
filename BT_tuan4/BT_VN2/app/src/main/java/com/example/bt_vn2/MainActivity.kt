package com.example.bt_vn2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppNavigator() }
    }
}

// ---------------------- ViewModel ----------------------
data class SubmittedData(
    val email: String,
    val otp: String,
    val password: String
)

class ForgotViewModel : ViewModel() {
    var email by mutableStateOf("")
    var otpDigits = mutableStateListOf("", "", "", "", "")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var lastSubmittedData by mutableStateOf<SubmittedData?>(null)

    fun otpString(): String = otpDigits.joinToString("")

    fun resetAfterEmail() {
        otpDigits = mutableStateListOf("", "", "", "", "")
        password = ""
        confirmPassword = ""
    }

    fun resetAfterOtp() {
        password = ""
        confirmPassword = ""
    }

    fun resetAll() {
        email = ""
        resetAfterEmail()
    }

    fun setLastSubmittedData(email: String, otp: String, password: String) {
        lastSubmittedData = SubmittedData(email, otp, password)
    }
}

// ---------------------- Navigation ----------------------
@Composable
fun AppNavigator(vm: ForgotViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "forgot") {
        composable("forgot") { ForgotPasswordScreen(navController, vm) }
        composable("verify") { VerifyCodeScreen(navController, vm) }
        composable("create") { CreatePasswordScreen(navController, vm) }
        composable("confirm") { ConfirmScreen(navController, vm) }
    }
}

// ---------------------- Header ----------------------
@Composable
fun AppHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_uth_logo),
            contentDescription = "UTH Logo",
            modifier = Modifier.size(80.dp)
        )
        Text("SmartTasks", color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
    }
}

// ---------------------- 1. Forgot Password ----------------------
@Composable
fun ForgotPasswordScreen(navController: NavHostController, vm: ForgotViewModel) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()
            Spacer(Modifier.height(24.dp))
            Text("Forget Password?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Enter your Email, we will send you a verification code.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = vm.email,
                onValueChange = { vm.email = it },
                label = { Text("Your Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (vm.email.isNotBlank()) {
                        vm.resetAfterEmail()
                        navController.navigate("verify")
                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Next") }

            // ----------- Hiển thị thông tin recap sau khi Submit -----------
            vm.lastSubmittedData?.let { data ->
                Spacer(Modifier.height(32.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Last Reset Summary", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Spacer(Modifier.height(8.dp))
                        Text("Email: ${data.email}")
                        Text("OTP: ${data.otp}")
                        Text("Password: ${data.password}")
                    }
                }
            }
        }
    }
}

// ---------------------- 2. Verify Code ----------------------
@Composable
fun VerifyCodeScreen(navController: NavHostController, vm: ForgotViewModel) {
    val focusRequesters = remember { List(5) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart).padding(top = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()
            Spacer(Modifier.height(16.dp))
            Text("Verify Code", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Enter the code we just sent you on your registered Email",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                for (i in 0 until 5) {
                    OutlinedTextField(
                        value = vm.otpDigits[i],
                        onValueChange = { value ->
                            val filtered = value.filter { it.isDigit() }.take(1)
                            vm.otpDigits[i] = filtered
                            if (filtered.isNotEmpty() && i < 4) focusRequesters[i + 1].requestFocus()
                            if (filtered.isEmpty() && i > 0) focusRequesters[i - 1].requestFocus()
                        },
                        singleLine = true,
                        modifier = Modifier.size(56.dp).focusRequester(focusRequesters[i]),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (i == 4) ImeAction.Done else ImeAction.Next
                        ),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("We sent code to: ${vm.email}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (vm.otpString().length == 5) {
                        vm.resetAfterOtp()
                        navController.navigate("create")
                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                enabled = vm.otpString().length == 5
            ) { Text("Next") }
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters.firstOrNull()?.requestFocus()
    }
}

// ---------------------- 3. Create Password ----------------------
@Composable
fun CreatePasswordScreen(navController: NavHostController, vm: ForgotViewModel) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart).padding(top = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()
            Spacer(Modifier.height(16.dp))
            Text("Create new password", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                "Your new password must be different from previously used password",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = vm.password,
                onValueChange = { vm.password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = vm.confirmPassword,
                onValueChange = { vm.confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
            )

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (vm.password == vm.confirmPassword && vm.password.isNotBlank()) {
                        navController.navigate("confirm")
                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Next") }
        }
    }
}

// ---------------------- 4. Confirm Screen ----------------------
@Composable
fun ConfirmScreen(navController: NavHostController, vm: ForgotViewModel) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart).padding(top = 8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppHeader()
            Spacer(Modifier.height(16.dp))
            Text("Confirm", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("We are here to help you!", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = vm.email,
                onValueChange = {},
                label = { Text("Email") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = vm.otpString(),
                onValueChange = {},
                label = { Text("OTP Code") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = vm.password,
                onValueChange = {},
                label = { Text("Password") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    vm.setLastSubmittedData(vm.email, vm.otpString(), vm.password)
                    vm.resetAll()
                    navController.navigate("forgot") {
                        popUpTo("forgot") { inclusive = false }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Submit") }
        }
    }
}
