package com.example.vitalsense.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.AnimatedButton
import com.example.vitalsense.components.AnimatedChart
import com.example.vitalsense.components.BottomNavigationBar
import com.example.vitalsense.ui.theme.BloodOxygenColor
import kotlinx.coroutines.delay

@Composable
fun BloodOxygenScreen(navController: NavHostController) {
    // 是否在测量血氧
    var isDetecting by remember { mutableStateOf(false) }
    // 当前血氧值
    var currentSpO2 by remember { mutableStateOf("--") }
    // 动态血氧数据
    val spO2DataPoints = remember { mutableStateListOf<Float>() }
    // 历史记录
    val oxygenHistory = remember { mutableStateListOf<Pair<String, Int>>() }
    // 是否显示提示弹窗
    var showTips by remember { mutableStateOf(false) }
    
    // 模拟血氧检测
    LaunchedEffect(isDetecting) {
        if (isDetecting) {
            // 清空历史数据
            spO2DataPoints.clear()
            // 等待2秒模拟手指触摸传感器过程
            delay(2000)
            // 逐渐增加血氧值，模拟传感器分析结果
            for (i in 1..12) {
                delay(500)
                // 模拟从传感器获取血氧值
                val spO2 = 94f + (Math.random() * 5).toFloat()
                // 更新当前血氧值
                currentSpO2 = spO2.toInt().toString()
                // 添加到数据点集合
                spO2DataPoints.add(spO2)
            }
            // 检测完成后添加到历史记录
            val timestamp = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            oxygenHistory.add(0, Pair(timestamp, currentSpO2.toInt()))
            if (oxygenHistory.size > 5) {
                oxygenHistory.removeAt(oxygenHistory.size - 1)
            }
            // 检测完成
            isDetecting = false
        }
    }
    
    // 呼吸动画
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顶部栏
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "血氧监测",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { showTips = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "信息",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 血氧显示区域
            item {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // 外圈动画
                    if (isDetecting) {
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .scale(scale)
                                .clip(CircleShape)
                                .background(BloodOxygenColor.copy(alpha = 0.1f))
                        )
                    }
                    
                    // 内圈
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(
                                if (isDetecting) BloodOxygenColor.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // 血氧图标
                            Icon(
                                imageVector = Icons.Default.Air,
                                contentDescription = "血氧",
                                tint = BloodOxygenColor,
                                modifier = Modifier.size(40.dp)
                            )
                            
                            // 血氧值
                            Text(
                                text = currentSpO2,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDetecting) BloodOxygenColor else MaterialTheme.colorScheme.onSurface
                            )
                            
                            // 单位
                            Text(
                                text = "%",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 操作按钮
            item {
                AnimatedButton(
                    text = if (isDetecting) "停止测量" else "开始测量",
                    onClick = { isDetecting = !isDetecting },
                    backgroundColor = if (isDetecting) MaterialTheme.colorScheme.error else BloodOxygenColor
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 血氧值图表
            item {
                if (spO2DataPoints.isNotEmpty()) {
                    Text(
                        text = "血氧趋势",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        AnimatedChart(
                            dataPoints = spO2DataPoints,
                            color = BloodOxygenColor,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // 历史记录
            item {
                if (oxygenHistory.isNotEmpty()) {
                    Text(
                        text = "历史记录",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column {
                            oxygenHistory.forEachIndexed { index, (timestamp, value) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp, horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // 时间戳
                                    Text(
                                        text = timestamp,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                        modifier = Modifier.width(60.dp)
                                    )
                                    
                                    // 血氧数值
                                    val spO2Text = "$value%"
                                    Text(
                                        text = spO2Text,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    // 血氧状态评估
                                    val (statusText, statusColor) = when {
                                        value < 90 -> Pair("偏低", Color.Red)
                                        value < 95 -> Pair("注意", Color(0xFFFFA500)) // Orange
                                        else -> Pair("正常", Color.Green)
                                    }
                                    
                                    Text(
                                        text = statusText,
                                        fontSize = 14.sp,
                                        color = statusColor
                                    )
                                }
                                
                                if (index < oxygenHistory.size - 1) {
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
            
            // 血氧知识
            item {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "血氧知识",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "血氧饱和度正常值范围",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "• 正常值: 95-100%",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 轻度缺氧: 90-94%",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 中度缺氧: 85-89%",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 重度缺氧: <85%",
                            fontSize = 14.sp
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "影响因素",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "环境、呼吸系统疾病、贫血、一氧化碳中毒等因素都可能影响血氧水平。",
                            fontSize = 14.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    
    // 提示弹窗
    if (showTips) {
        AlertDialog(
            onDismissRequest = { showTips = false },
            title = { Text("使用提示") },
            text = { 
                Column {
                    Text("1. 请确保指尖干净，不要涂抹指甲油")
                    Text("2. 保持手指静止不动")
                    Text("3. 测量时避免说话或移动")
                    Text("4. 如果测量结果异常低于90%，请重新测量或就医")
                }
            },
            confirmButton = {
                TextButton(onClick = { showTips = false }) {
                    Text("我知道了")
                }
            }
        )
    }
}
