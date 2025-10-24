package com.example.bt_vn1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Student(val name: String)
data class Book(val title: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LibraryApp() }
    }
}

@Composable
fun LibraryApp() {
    // 🟢 Lưu dữ liệu ở đây để không bị reset khi đổi tab
    val students = remember {
        mutableStateListOf(
            Student("Nguyen Van A"),
            Student("Nguyen Thi B"),
            Student("Nguyen Van C")
        )
    }

    // Dữ liệu mượn sách của từng sinh viên
    val borrowedBooks = remember {
        mutableStateMapOf(
            "Nguyen Van A" to mutableStateListOf("Sách 01", "Sách 02"),
            "Nguyen Thi B" to mutableStateListOf("Sách 01"),
            "Nguyen Van C" to mutableStateListOf()
        )
    }

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Quản lý") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("DS Sách") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Sinh viên") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> ManageScreen(
                students = students,
                borrowedBooks = borrowedBooks,
                modifier = Modifier.padding(padding)
            )
            1 -> BookListScreen(modifier = Modifier.padding(padding))
            2 -> StudentListScreen(students, modifier = Modifier.padding(padding))
        }
    }
}

// ---------- Màn hình Quản lý ----------
@Composable
fun ManageScreen(
    students: MutableList<Student>,
    borrowedBooks: SnapshotStateMap<String, SnapshotStateList<String>>,
    modifier: Modifier = Modifier
) {
    var currentStudentIndex by remember { mutableStateOf(0) }

    val currentStudent = students[currentStudentIndex]
    val currentBooks = borrowedBooks[currentStudent.name] ?: mutableListOf()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hệ thống", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Quản lý Thư viện", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // Sinh viên
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = currentStudent.name,
                onValueChange = {},
                label = { Text("Sinh viên") },
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    currentStudentIndex = (currentStudentIndex + 1) % students.size
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text("Thay đổi", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Danh sách sách", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFFEAEAEA), shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
        ) {
            if (currentBooks.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Bạn chưa mượn quyển sách nào", fontWeight = FontWeight.Medium)
                    Text(
                        "Nhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn {
                    items(currentBooks) { book ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = {
                                    // 🧹 Bỏ tick → xóa khỏi danh sách
                                    if (!it) {
                                        currentBooks.remove(book)
                                    }
                                }
                            )
                            Text(book)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                currentBooks.add("Sách 0${currentBooks.size + 1}")
                borrowedBooks[currentStudent.name] = currentBooks as SnapshotStateList<String>
            },
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text("Thêm", color = Color.White)
        }
    }
}

// ---------- Màn hình Danh sách Sách ----------
@Composable
fun BookListScreen(modifier: Modifier = Modifier) {
    val books = listOf("Sách 01", "Sách 02", "Sách 03", "Sách 04")
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Danh sách sách", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(books) { book ->
                Text(book, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

// ---------- Màn hình Danh sách Sinh viên ----------
@Composable
fun StudentListScreen(students: List<Student>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Danh sách sinh viên", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(students) { s ->
                Text(s.name, modifier = Modifier.padding(8.dp))
            }
        }
    }
}