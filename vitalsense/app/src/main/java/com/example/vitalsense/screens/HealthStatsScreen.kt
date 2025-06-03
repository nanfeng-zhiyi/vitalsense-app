package com.example.vitalsense.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.AnimatedChart
import com.example.vitalsense.components.BottomNavigationBar
import com.example.vitalsense.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthStatsScreen(navController: NavHostController) {
    // 活跃的健康指标标签
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("心率", "血氧", "血压", "呼吸率")
    
    // 模拟数据
    val heartRateData = remember {
        listOf(72f, 75f, 73f, 78f, 74f, 76f, 75f, 79f, 76f, 74f, 72f, 73f, 78f, 77f)
    }
    
    val bloodOxygenData = remember {
        listOf(97f, 98f, 98f, 99f, 98f, 97f, 98f, 99f, 97f, 98f, 96f, 97f, 98f, 97f)
    }
    
    val systolicData = remember {
        listOf(120f, 123f, 125f, 118f, 122f, 121f, 119f, 120f, 124f, 122f, 120f, 121f, 119f, 120f)
    }
    
    val diastolicData = remember {
        listOf(80f, 81f, 83f, 79f, 82f, 80f, 78f, 79f, 81f, 80f, 79f, 80f, 78f, 79f)
    }
    
    val respiratoryData = remember {
        listOf(16f, 15f, 16f, 17f, 15f, 16f, 15f, 16f, 14f, 15f, 17f, 16f, 15f, 16f)
    }
    
    // 健康事件
    val healthEvents = remember {
        listOf(
            HealthEvent("2025-06-01", "每日目标已完成", Icons.Default.EmojiEvents, "您已达成每日10000步的目标!"),
            HealthEvent("2025-05-31", "健康提醒", Icons.Default.Notifications, "您已连续3天未测量血压"),
            HealthEvent("2025-05-30", "异常检测", Icons.Default.Warning, "检测到心率异常波动"),
            HealthEvent("2025-05-27", "健康评估", Icons.Default.AssignmentTurnedIn, "已完成每周健康评估")
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "健康统计",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { /* 筛选功能 */ }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "筛选"
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
                .padding(horizontal = 16.dp)
        ) {
            // 时间范围选择器
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = true,
                            onClick = { /* 切换为周视图 */ },
                            label = { Text("周") },
                            leadingIcon = {
                                if (true) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            }
                        )
                        
                        FilterChip(
                            selected = false,
                            onClick = { /* 切换为月视图 */ },
                            label = { Text("月") }
                        )
                        
                        FilterChip(
                            selected = false,
                            onClick = { /* 切换为季视图 */ },
                            label = { Text("季") }
                        )
                        
                        FilterChip(
                            selected = false,
                            onClick = { /* 切换为年视图 */ },
                            label = { Text("年") }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 健康指标标签
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 0.dp,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { 
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                ) 
                            },
                            selectedContentColor = when (index) {
                                0 -> HeartRateColor
                                1 -> BloodOxygenColor
                                2 -> BloodPressureColor
                                3 -> RespiratoryColor
                                else -> MaterialTheme.colorScheme.primary
                            },
                            unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 健康指标图表
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // 图表标题栏
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 图表标题
                            Text(
                                text = when (selectedTabIndex) {
                                    0 -> "心率趋势"
                                    1 -> "血氧趋势"
                                    2 -> "血压趋势"
                                    3 -> "呼吸率趋势"
                                    else -> "健康趋势"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            
                            // 日期范围
                            Text(
                                text = "2025年5月28日 - 6月3日",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // 图表
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            when (selectedTabIndex) {
                                0 -> {
                                    // 心率图表
                                    AnimatedChart(
                                        dataPoints = heartRateData,
                                        color = HeartRateColor
                                    )
                                }
                                1 -> {
                                    // 血氧图表
                                    AnimatedChart(
                                        dataPoints = bloodOxygenData,
                                        color = BloodOxygenColor
                                    )
                                }
                                2 -> {
                                    // 血压图表 - 这里只显示收缩压
                                    AnimatedChart(
                                        dataPoints = systolicData,
                                        color = BloodPressureColor
                                    )
                                }
                                3 -> {
                                    // 呼吸率图表
                                    AnimatedChart(
                                        dataPoints = respiratoryData,
                                        color = RespiratoryColor
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // 统计数据
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatisticCard(
                                title = "平均",
                                value = when (selectedTabIndex) {
                                    0 -> "75"
                                    1 -> "98%"
                                    2 -> "121/80"
                                    3 -> "16"
                                    else -> "--"
                                },
                                color = when (selectedTabIndex) {
                                    0 -> HeartRateColor
                                    1 -> BloodOxygenColor
                                    2 -> BloodPressureColor
                                    3 -> RespiratoryColor
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                            
                            StatisticCard(
                                title = "最高",
                                value = when (selectedTabIndex) {
                                    0 -> "79"
                                    1 -> "99%"
                                    2 -> "125/83"
                                    3 -> "17"
                                    else -> "--"
                                },
                                color = when (selectedTabIndex) {
                                    0 -> HeartRateColor
                                    1 -> BloodOxygenColor
                                    2 -> BloodPressureColor
                                    3 -> RespiratoryColor
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                            
                            StatisticCard(
                                title = "最低",
                                value = when (selectedTabIndex) {
                                    0 -> "72"
                                    1 -> "96%"
                                    2 -> "118/78"
                                    3 -> "14"
                                    else -> "--"
                                },
                                color = when (selectedTabIndex) {
                                    0 -> HeartRateColor
                                    1 -> BloodOxygenColor
                                    2 -> BloodPressureColor
                                    3 -> RespiratoryColor
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 健康事件标题
            item {
                Text(
                    text = "健康事件",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // 健康事件列表
            items(healthEvents.size) { index ->
                val event = healthEvents[index]
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 图标
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = event.icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // 事件内容
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = event.title,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            Text(
                                text = event.description,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        // 日期
                        Text(
                            text = event.date,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
            
            // 底部间距
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatisticCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .height(70.dp)
            .width(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = color
            )
            
            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

data class HealthEvent(
    val date: String,
    val title: String,
    val icon: ImageVector,
    val description: String
)
