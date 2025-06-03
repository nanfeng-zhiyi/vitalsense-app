package com.example.vitalsense.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.AnimatedButton
import com.example.vitalsense.components.AnimatedChart
import com.example.vitalsense.components.BottomNavigationBar
import com.example.vitalsense.ui.theme.HeartRateColor
import kotlinx.coroutines.delay
import kotlin.math.sin

@Composable
fun HeartRateScreen(navController: NavHostController) {
    // 是否在测量心率
    var isDetecting by remember { mutableStateOf(false) }
    // 当前心率
    var currentHeartRate by remember { mutableStateOf("--") }
    // 动态心率数据
    val heartRateDataPoints = remember { mutableStateListOf<Float>() }
    // 历史记录
    val heartRateHistory = remember { mutableStateListOf<Pair<String, Int>>() }
    // 检测模式
    var detectionMode by remember { mutableStateOf("normal") } // normal, detailed
    // 是否显示提示弹窗
    var showTips by remember { mutableStateOf(false) }
    
    // 模拟心率检测
    LaunchedEffect(isDetecting, detectionMode) {
        if (isDetecting) {
            // 清空历史数据
            heartRateDataPoints.clear()
            // 等待2秒模拟面部视频分析启动过程
            delay(2000)
            // 逐渐增加心率值，模拟面部视频分析结果
            for (i in 1..20) {
                delay(500)
                // 模拟从面部视频分析中获取心率值
                val heartRate = if (detectionMode == "normal") {
                    65f + (Math.random() * 20).toFloat()
                } else {
                    // 详细模式提供更精确的数据
                    68f + (Math.random() * 15).toFloat()
                }
                // 更新当前心率
                currentHeartRate = heartRate.toInt().toString()
                // 添加到数据点集合
                heartRateDataPoints.add(heartRate)
            }
            // 检测完成后添加到历史记录
            val timestamp = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            heartRateHistory.add(0, Pair(timestamp, currentHeartRate.toInt()))
            if (heartRateHistory.size > 5) {
                heartRateHistory.removeAt(heartRateHistory.size - 1)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顶部栏
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
                    text = "心率检测",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.size(48.dp)) { /* 占位 */ }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 心率显示区域
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // 外圈心跳动画
                if (isDetecting) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(HeartRateColor.copy(alpha = 0.1f))
                    )
                }
                
                // 内圈
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(
                            if (isDetecting) HeartRateColor.copy(alpha = 0.2f)
                            else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // 心率图标
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "心率",
                            tint = HeartRateColor,
                            modifier = Modifier.size(40.dp)
                        )
                        
                        // 心率值
                        Text(
                            text = currentHeartRate,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDetecting) HeartRateColor else MaterialTheme.colorScheme.onSurface
                        )
                        
                        // 单位
                        Text(
                            text = "次/分钟",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 操作按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // 开始/停止检测按钮
                AnimatedButton(
                    text = if (isDetecting) "停止检测" else "开始检测",
                    onClick = { isDetecting = !isDetecting },
                    backgroundColor = if (isDetecting) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
                
                // 提示按钮
                AnimatedButton(
                    text = "使用提示",
                    onClick = { showTips = true },
                    backgroundColor = MaterialTheme.colorScheme.secondary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 检测模式选择
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "检测模式:",
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // 普通模式
                Surface(
                    modifier = Modifier.clickable { detectionMode = "normal" },
                    shape = RoundedCornerShape(16.dp),
                    color = if (detectionMode == "normal") 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "普通",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (detectionMode == "normal") 
                            MaterialTheme.colorScheme.onPrimary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // 详细模式
                Surface(
                    modifier = Modifier.clickable { detectionMode = "detailed" },
                    shape = RoundedCornerShape(16.dp),
                    color = if (detectionMode == "detailed") 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "详细",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (detectionMode == "detailed") 
                            MaterialTheme.colorScheme.onPrimary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 图表显示区域
            if (heartRateDataPoints.isNotEmpty()) {
                AnimatedVisibility(
                    visible = heartRateDataPoints.isNotEmpty(),
                    enter = fadeIn(animationSpec = tween(500))
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "心率波形",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            IconButton(onClick = { /* 分享功能 */ }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "分享",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(vertical = 8.dp)
                        ) {
                            // 心率波形图
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height
                                val path = Path()
                                
                                // 计算x轴和y轴的刻度
                                val maxRate = heartRateDataPoints.maxOrNull() ?: 100f
                                val minRate = heartRateDataPoints.minOrNull() ?: 60f
                                val range = maxOf(maxRate - minRate, 20f) // 至少有20的范围
                                val xStep = width / (heartRateDataPoints.size - 1)
                                
                                // 绘制曲线
                                heartRateDataPoints.forEachIndexed { index, rate ->
                                    val x = index * xStep
                                    val y = height - ((rate - minRate) / range * height)
                                    
                                    if (index == 0) {
                                        path.moveTo(x, y)
                                    } else {
                                        path.lineTo(x, y)
                                    }
                                }
                                
                                drawPath(
                                    path = path,
                                    color = HeartRateColor,
                                    style = Stroke(width = 3f)
                                )
                                
                                // 绘制数据点
                                heartRateDataPoints.forEachIndexed { index, rate ->
                                    val x = index * xStep
                                    val y = height - ((rate - minRate) / range * height)
                                    
                                    drawCircle(
                                        color = HeartRateColor,
                                        radius = 4f,
                                        center = Offset(x, y)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // 历史记录部分
            if (heartRateHistory.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "历史记录",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                
                Column {
                    heartRateHistory.forEach { (timestamp, rate) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 时间戳
                            Text(
                                text = timestamp,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                modifier = Modifier.width(60.dp)
                            )
                            
                            // 心率数值
                            val heartRateText = "$rate 次/分钟"
                            Text(
                                text = heartRateText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            
                            // 心率状态评估
                            val (statusText, statusColor) = when {
                                rate < 60 -> Pair("偏低", Color.Blue)
                                rate > 100 -> Pair("偏高", Color.Red)
                                else -> Pair("正常", Color.Green)
                            }
                            
                            Text(
                                text = statusText,
                                fontSize = 14.sp,
                                color = statusColor,
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                }
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
                    Text("1. 请将手机摄像头对准面部，确保光线充足")
                    Text("2. 保持面部在摄像头中心")
                    Text("3. 测量过程中请保持静止")
                    Text("4. 详细模式下获得更准确的结果，但需要更长时间")
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
