package com.example.vitalsense.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.BottomNavigationBar
import com.example.vitalsense.navigation.Screen
import com.example.vitalsense.ui.theme.HeartRateColor
import com.example.vitalsense.ui.theme.RespiratoryColor
import com.example.vitalsense.ui.theme.BloodOxygenColor
import com.example.vitalsense.ui.theme.BloodPressureColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    // 最近测量数据
    val recentMeasurements = remember {
        listOf(
            HealthMetric("心率", "75", "次/分钟", HeartRateColor, Icons.Default.Favorite) { navController.navigate(Screen.HeartRate.route) },
            HealthMetric("血氧", "98", "%", BloodOxygenColor, Icons.Default.Air) { navController.navigate(Screen.BloodOxygen.route) },
            HealthMetric("呼吸率", "16", "次/分钟", RespiratoryColor, Icons.Default.AirlineSeatFlatAngled) { navController.navigate(Screen.RespiratoryRate.route) },
            HealthMetric("血压", "120/80", "mmHg", BloodPressureColor, Icons.Default.Speed) { navController.navigate(Screen.BloodPressure.route) }
        )
    }

    // 健康文章
    val healthArticles = remember {
        listOf(
            DashboardArticle("如何提高睡眠质量", "良好的睡眠对健康至关重要，本文将介绍几种提高睡眠质量的方法..."),
            DashboardArticle("每日运动的好处", "坚持每日运动可以增强心肺功能，提高免疫力，改善情绪..."),
            DashboardArticle("健康饮食指南", "均衡的饮食对健康的影响不可忽视，本文将为您提供健康饮食的建议...")
        )
    }

    // 健康提醒
    val healthReminders = remember {
        listOf(
            "今天已经喝水1500ml，再喝500ml达到日标",
            "已经4天没有锻炼了，建议今天进行30分钟有氧运动",
            "您的睡眠质量近一周有所下降，建议改善睡眠环境"
        )
    }
    
    // 是否显示健康评估
    var showHealthAssessment by remember { mutableStateOf(false) }

    // 主屏幕内容
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "健康中心",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "设置",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 欢迎消息
            item {
                Spacer(modifier = Modifier.height(8.dp))
                WelcomeMessage()
            }

            // 健康指标卡片
            item {
                Text(
                    text = "健康指标",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    recentMeasurements.forEach { metric ->
                        HealthMetricCard(metric)
                    }
                }
            }

            // 健康评估按钮
            item {
                Button(
                    onClick = { showHealthAssessment = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.HealthAndSafety,
                        contentDescription = "健康评估",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("开始健康评估")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 健康提醒
            item {
                Text(
                    text = "健康提醒",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                healthReminders.forEach { reminder ->
                    HealthReminderItem(reminder)
                }
            }

            // 健康文章
            item {
                Text(
                    text = "健康阅读",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                healthArticles.forEach { article ->
                    HealthArticleItem(article)
                }
            }

            // 底部间距
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // 健康评估对话框
        if (showHealthAssessment) {
            AlertDialog(
                onDismissRequest = { showHealthAssessment = false },
                title = { Text("健康评估") },
                text = { Text("通过回答一系列问题，我们将为您提供个性化的健康建议。完成评估大约需要5分钟。") },
                confirmButton = {
                    Button(
                        onClick = {
                            showHealthAssessment = false
                            navController.navigate(Screen.DiseaseScreening.route)
                        }
                    ) {
                        Text("开始")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showHealthAssessment = false }) {
                        Text("取消")
                    }
                }
            )
        }
    }
}

@Composable
fun WelcomeMessage() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "你好，用户",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "今天感觉如何？",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun HealthMetricCard(metric: HealthMetric) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = metric.onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = metric.color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = metric.icon,
                contentDescription = metric.name,
                tint = metric.color,
                modifier = Modifier.size(28.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = metric.value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = metric.name,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        
        Text(
            text = metric.unit,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun HealthReminderItem(reminder: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "提醒",
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = reminder,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun HealthArticleItem(article: DashboardArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = article.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = article.summary,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 2
            )
        }
    }
}

// 数据类
data class HealthMetric(
    val name: String,
    val value: String,
    val unit: String,
    val color: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

data class DashboardArticle(
    val title: String,
    val summary: String
)
