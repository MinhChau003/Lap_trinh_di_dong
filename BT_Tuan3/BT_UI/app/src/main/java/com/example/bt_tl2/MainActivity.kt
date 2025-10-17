package com.example.bt_tl2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.bt_tl2.ui.theme.ComposeIntroTheme

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
        composable("home") { UIComponentsList(navController) }
        composable("textDetail") { TextDetailScreen() }
        composable("imageDetail") { ImageDetailScreen() }
        composable("textFieldDetail") { TextFieldScreen() }
        composable("passwordFieldDetail") { PasswordFieldScreen() }
        composable("rowLayout") { RowLayoutScreen() }
        composable("columnLayout") { ColumnLayoutScreen() }
        composable("buttonDemo") { ButtonDemoScreen() }
        composable("switchDemo") { SwitchDemoScreen() }
        composable("checkboxDemo") { CheckboxDemoScreen() }
        composable("sliderDemo") { SliderScreen() }
    }
}

// ---------------- INTRO SCREEN ----------------
@Composable
fun IntroScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 80.dp),
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
                "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative approach.",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        Button(
            onClick = { navController.navigate("home") },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Text("I'm ready", color = Color.White, fontSize = 18.sp)
        }
    }
}

// ---------------- UI COMPONENTS LIST ----------------
@Composable
fun UIComponentsList(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("UI Components List", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        // Display
        SectionHeader("Display")
        ButtonItem("Text", "Displays text") { navController.navigate("textDetail") }
        ButtonItem("Image", "Displays an image") { navController.navigate("imageDetail") }

        // Input
        SectionHeader("Input")
        ButtonItem("TextField", "Input field for text") { navController.navigate("textFieldDetail") }
        ButtonItem("PasswordField", "Input field for passwords") { navController.navigate("passwordFieldDetail") }

        // Layout
        SectionHeader("Layout")
        ButtonItem("Row", "Arranges elements horizontally") { navController.navigate("rowLayout") }
        ButtonItem("Column", "Arranges elements vertically") { navController.navigate("columnLayout") }

        // Extra Components
        SectionHeader("Extra Components")
        ButtonItem("Button", "Interactive button example") { navController.navigate("buttonDemo") }
        ButtonItem("Switch", "Toggle switch example") { navController.navigate("switchDemo") }
        ButtonItem("Checkbox", "Selectable checkbox example") { navController.navigate("checkboxDemo") }
        ButtonItem("Slider", "Thanh trượt điều chỉnh âm lượng") { navController.navigate("sliderDemo") }

        Spacer(modifier = Modifier.height(16.dp))
        ButtonItem(
            title = "Tự tìm hiểu",
            description = "Tìm ra tất cả các thành phần UI cơ bản",
            background = Color(0xFFFFCDD2),
            textColor = Color.Red
        ) {}
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
}

@Composable
fun ButtonItem(
    title: String,
    description: String,
    background: Color = Color(0xFFE3F2FD),
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(background, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold, color = textColor)
        Text(description, fontSize = 12.sp, color = textColor.copy(alpha = 0.7f))
    }
}


// ---------------- DETAIL SCREENS ----------------
@Composable
fun TextDetailScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("The quick Brown fox jumps over the lazy dog.",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(10.dp))
        Text("Normal text")
        Text("Bold text", fontWeight = FontWeight.Bold)
        Text("Italic text", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
        Text("Underlined text", style = TextStyle(textDecoration = TextDecoration.Underline))
    }
}

@Composable
fun ImageDetailScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth_image),
            contentDescription = "UTH building",
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("In app", color = Color.Gray)
    }
}

@Composable
fun TextFieldScreen() {
    var input by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Thông tin nhập") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text("Tự động cập nhật dữ liệu theo textfield", color = Color.Red)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = input, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PasswordFieldScreen() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Nhập mật khẩu") },
            singleLine = true,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.ic_visibility)
                else
                    painterResource(id = R.drawable.ic_visibility_off)

                val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = description)
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text("Độ dài mật khẩu: ${password.length}")
    }
}


@Composable
fun RowLayoutScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(4) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) {
                    Box(
                        modifier = Modifier.size(110.dp, 70.dp)
                            .background(
                                if (it % 2 == 0) Color(0xFF90CAF9) else Color(0xFF1565C0),
                                RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun ColumnLayoutScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(5) {
            Box(
                modifier = Modifier.fillMaxWidth().height(60.dp)
                    .background(
                        if (it % 2 == 0) Color(0xFFFFCDD2) else Color(0xFFBBDEFB),
                        RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
fun ButtonDemoScreen() {
    var count by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bạn đã bấm $count lần", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { count++ }) {
            Text("Bấm tôi nè!")
        }
    }
}

@Composable
fun SwitchDemoScreen() {
    var isOn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Trạng thái: ${if (isOn) "Bật" else "Tắt"}")
        Spacer(modifier = Modifier.height(20.dp))
        Switch(checked = isOn, onCheckedChange = { isOn = it })
    }
}

@Composable
fun CheckboxDemoScreen() {
    var checked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bạn đồng ý điều khoản?")
        Spacer(modifier = Modifier.height(10.dp))
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        Text(if (checked) "✅ Đã đồng ý" else "❌ Chưa đồng ý")
    }
}

@Composable
fun SliderScreen() {
    var sliderValue by remember { mutableStateOf(0.5f) } // Giá trị mặc định giữa 0–1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Điều chỉnh âm lượng",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..1f,
            steps = 9, // chia nhỏ thanh trượt thành 10 nấc
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Âm lượng: ${(sliderValue * 100).toInt()}%",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}

