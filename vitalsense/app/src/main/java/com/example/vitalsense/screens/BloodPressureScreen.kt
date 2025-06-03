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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Speed
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.AnimatedButton
import com.example.vitalsense.components.BottomNavigationBar
import com.example.vitalsense.ui.theme.BloodPressureColor
import kotlinx.coroutines.delay

@Composable
fun BloodPressureScreen(navController: NavHostController) {
    // 是否在测量血压
    var isDetecting by remember { mutableStateOf(false) }
    // 当前收缩压和舒张压
    var systolic by remember { mutableStateOf("--") }
    var diastolic by remember { mutableStateOf("--") }
    // 历史记录
    val bloodPressureHistory = remember { 
        mutableListOf<Triple<String, Int, Int>>() 
    }
    // 是否显示提示弹窗
    var showTips by remember { mutableStateOf(false) }
    
    // 模拟血压检测
    LaunchedEffect(isDetecting) {
        if (isDetecting) {
            // 等待3秒模拟袖带充气和检测过程
            delay(3000)
            
            // 模拟血压测量结果
            val systolicValue = (115 + (Math.random() * 20).toInt())
            val diastolicValue = (75 + (Math.random() * 15).toInt())
            
            // 更新血压值
            systolic = systolicValue.toString()
            diastolic = diastolicValue.toString()
            
            // 检测完成后添加到历史记录
            val timestamp = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            bloodPressureHistory.add(0, Triple(timestamp, systolicValue, diastolicValue))
            if (bloodPressureHistory.size > 5) {
                bloodPressureHistory.removeAt(bloodPressureHistory.size - 1)
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
                        text = "血压监测",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { showTips = true }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "提示",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 血压显示区域
            item {
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // 外圈动画
                    if (isDetecting) {
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .scale(scale)
                                .clip(CircleShape)
                                .background(BloodPressureColor.copy(alpha = 0.1f))
                        )
                    }
                    
                    // 内圈
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(
                                if (isDetecting) BloodPressureColor.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // 血压图标
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "血压",
                                tint = BloodPressureColor,
                                modifier = Modifier.size(40.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // 收缩压文字
                            Text(
                                text = "收缩压",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            
                            // 收缩压值
                            Text(
                                text = systolic,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDetecting) BloodPressureColor else MaterialTheme.colorScheme.onSurface
                            )
                            
                            Divider(
                                modifier = Modifier
                                    .width(60.dp)
                                    .padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                            
                            // 舒张压文字
                            Text(
                                text = "舒张压",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            
                            // 舒张压值
                            Text(
                                text = diastolic,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDetecting) BloodPressureColor else MaterialTheme.colorScheme.onSurface
                            )
                            
                            // 单位
                            Text(
                                text = "mmHg",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // 操作按钮
            item {
                AnimatedButton(
                    text = if (isDetecting) "停止测量" else "开始测量",
                    onClick = { isDetecting = !isDetecting },
                    backgroundColor = if (isDetecting) MaterialTheme.colorScheme.error else BloodPressureColor
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // 血压状态
            item {
                if (systolic != "--" && diastolic != "--") {
                    val (statusText, statusColor) = getBloodPressureStatus(systolic.toInt(), diastolic.toInt())
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = statusColor.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "血压状态",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = statusText,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = statusColor
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val bpText = buildAnnotatedString {
                                append("您的血压: ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("$systolic/$diastolic mmHg")
                                }
                            }
                            
                            Text(
                                text = bpText,
                                fontSize = 14.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            
            // 历史记录
            item {
                if (bloodPressureHistory.isNotEmpty()) {
                    Text(
                        text = "历史记录",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column {
                            bloodPressureHistory.forEachIndexed { index, (timestamp, sys, dia) ->
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
                                    
                                    // 血压数值
                                    Text(
                                        text = "$sys/$dia mmHg",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    // 血压状态
                                    val (statusText, statusColor) = getBloodPressureStatus(sys, dia)
                                    
                                    Text(
                                        text = statusText,
                                        fontSize = 14.sp,
                                        color = statusColor
                                    )
                                }
                                
                                if (index < bloodPressureHistory.size - 1) {
                                    Divider(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // 血压知识
            item {
                Text(
                    text = "血压知识",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                
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
                            text = "血压级别",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "• 理想血压: <120/80 mmHg",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 正常血压: <130/85 mmHg",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 正常高值: 130-139/85-89 mmHg",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 轻度高血压: 140-159/90-99 mmHg",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 中度高血压: 160-179/100-109 mmHg",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 重度高血压: ≥180/110 mmHg",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 低血压: <90/60 mmHg",
                            fontSize = 14.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
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
                    Text("1. 测量前请保持放松，坐姿端正")
                    Text("2. 测量时手臂与心脏保持同一水平")
                    Text("3. 测量前30分钟避免剧烈运动")
                    Text("4. 测量前避免饮用咖啡和吸烟")
                    Text("5. 如测量结果异常，请咨询医生")
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

/**
 * 获取血压状态
 */
fun getBloodPressureStatus(systolic: Int, diastolic: Int): Pair<String, Color> {
    return when {
        systolic < 90 || diastolic < 60 -> Pair("低血压", Color.Blue)
        systolic < 120 && diastolic < 80 -> Pair("理想血压", Color.Green)
        systolic < 130 && diastolic < 85 -> Pair("正常血压", Color.Green)
        (systolic in 130..139) || (diastolic in 85..89) -> Pair("正常高值", Color(0xFFFFA500)) // Orange
        (systolic in 140..159) || (diastolic in 90..99) -> Pair("轻度高血压", Color(0xFFFF8C00)) // DarkOrange
        (systolic in 160..179) || (diastolic in 100..109) -> Pair("中度高血压", Color(0xFFFF4500)) // OrangeRed
        systolic >= 180 || diastolic >= 110 -> Pair("重度高血压", Color.Red)
        else -> Pair("异常值", Color.Gray)
    }
}
