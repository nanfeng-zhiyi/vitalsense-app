package com.example.vitalsense.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.R
import com.example.vitalsense.components.BottomNavigationBar

@Composable
fun MineScreen(navController: NavHostController) {
    // 对话框状态
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 头部个人信息
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // 背景
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    )
                    
                    // 个人信息卡片
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 100.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // 用户名
                            Text(
                                text = "张三",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // 用户ID
                            Text(
                                text = "ID: 10086",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // 会员标签
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(4.dp))
                                
                                Text(
                                    text = "健康会员",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                
                                Spacer(modifier = Modifier.width(4.dp))
                                
                                Text(
                                    text = "有效期至: 2025-12-31",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                    
                    // 头像
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .align(Alignment.TopCenter)
                            .offset(y = 60.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "用户头像",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 健康数据统计
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "健康数据",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatCard(
                                icon = Icons.Default.Favorite,
                                value = "143",
                                label = "测量记录"
                            )
                            
                            StatCard(
                                icon = Icons.Default.DateRange,
                                value = "28",
                                label = "连续使用"
                            )
                            
                            StatCard(
                                icon = Icons.Default.Description,
                                value = "18",
                                label = "健康报告"
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 功能列表
            item {
                FunctionSection(
                    title = "健康服务",
                    items = listOf(
                        FunctionItem(
                            Icons.Default.HealthAndSafety,
                            "健康档案",
                            "查看您的健康档案和记录"
                        ),
                        FunctionItem(
                            Icons.Default.Assignment,
                            "健康报告",
                            "查看生成的健康报告"
                        ) {
                            navController.navigate("reports")
                        },
                        FunctionItem(
                            Icons.Default.Notifications,
                            "健康提醒",
                            "设置健康提醒和通知"
                        )
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 更多功能
            item {
                FunctionSection(
                    title = "更多服务",
                    items = listOf(
                        FunctionItem(
                            Icons.Default.Settings,
                            "设置",
                            "应用设置和个人偏好"
                        ) {
                            navController.navigate("settings")
                        },
                        FunctionItem(
                            Icons.Default.Info,
                            "关于",
                            "应用信息和版本"
                        ),
                        FunctionItem(
                            Icons.Default.Feedback,
                            "反馈",
                            "提交问题或建议"
                        ),
                        FunctionItem(
                            Icons.Default.Help,
                            "帮助",
                            "查看使用帮助和指南"
                        )
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 退出登录按钮
            item {
                Button(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("退出登录")
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
    
    // 退出登录确认对话框
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("退出登录") },
            text = { Text("确认要退出登录吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        // 执行退出登录操作
                    }
                ) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
fun StatCard(icon: ImageVector, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun FunctionSection(
    title: String,
    items: List<FunctionItem>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Card {
            Column {
                items.forEachIndexed { index, item ->
                    FunctionListItem(item = item)
                    
                    if (index < items.size - 1) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FunctionListItem(item: FunctionItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = item.onClick ?: {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 图标
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 文本
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Medium
                )
                
                if (item.subtitle.isNotEmpty()) {
                    Text(
                        text = item.subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            // 箭头
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}

data class FunctionItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String = "",
    val onClick: (() -> Unit)? = null
)
