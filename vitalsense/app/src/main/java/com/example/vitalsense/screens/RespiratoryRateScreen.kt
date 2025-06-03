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
import androidx.compose.material.icons.filled.AirlineSeatFlatAngled
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
import com.example.vitalsense.ui.theme.RespiratoryColor
import kotlinx.coroutines.delay

@Composable
fun RespiratoryRateScreen(navController: NavHostController) {
    // 是否在检测呼吸率
    var isDetecting by remember { mutableStateOf(false) }
    // 当前呼吸率
    var currentRate by remember { mutableStateOf("--") }
    // 动态呼吸率数据
    val respiratoryDataPoints = remember { mutableStateListOf<Float>() }
    // 历史记录
    val respiratoryHistory = remember { mutableStateListOf<Pair<String, Int>>() }
    // 是否显示提示弹窗
    var showTips by remember { mutableStateOf(false) }
    
    // 模拟呼吸率检测
    LaunchedEffect(isDetecting) {
        if (isDetecting) {
            // 清空历史数据
            respiratoryDataPoints.clear()
            // 等待2秒模拟检测启动过程
            delay(2000)
            // 逐渐增加呼吸率，模拟检测结果
            for (i in 1..15) {
                delay(500)
                // 模拟获取呼吸率
                val rate = 14f + (Math.random() * 6).toFloat()
                // 更新当前呼吸率
                currentRate = rate.toInt().toString()
                // 添加到数据点集合
                respiratoryDataPoints.add(rate)
            }
            // 检测完成后添加到历史记录
            val timestamp = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            respiratoryHistory.add(0, Pair(timestamp, currentRate.toInt()))
            if (respiratoryHistory.size > 5) {
                respiratoryHistory.removeAt(respiratoryHistory.size - 1)
            }
            // 检测完成
            isDetecting = false
        }
    }
    
    // 呼吸动画
    val infiniteTransition = rememberInfiniteTransition(label = "breath")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath"
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
                        text = "呼吸率监测",
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
            
            // 呼吸率显示区域
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
                                .background(RespiratoryColor.copy(alpha = 0.1f))
                        )
                    }
                    
                    // 内圈
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(
                                if (isDetecting) RespiratoryColor.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // 呼吸率图标
                            Icon(
                                imageVector = Icons.Default.AirlineSeatFlatAngled,
                                contentDescription = "呼吸率",
                                tint = RespiratoryColor,
                                modifier = Modifier.size(40.dp)
                            )
                            
                            // 呼吸率值
                            Text(
                                text = currentRate,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDetecting) RespiratoryColor else MaterialTheme.colorScheme.onSurface
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
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 操作按钮
            item {
                AnimatedButton(
                    text = if (isDetecting) "停止测量" else "开始测量",
                    onClick = { isDetecting = !isDetecting },
                    backgroundColor = if (isDetecting) MaterialTheme.colorScheme.error else RespiratoryColor
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // 呼吸率图表
            item {
                if (respiratoryDataPoints.isNotEmpty()) {
                    Text(
                        text = "呼吸趋势",
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
                            dataPoints = respiratoryDataPoints,
                            color = RespiratoryColor,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // 历史记录
            item {
                if (respiratoryHistory.isNotEmpty()) {
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
                            respiratoryHistory.forEachIndexed { index, (timestamp, value) ->
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
                                    
                                    // 呼吸率数值
                                    val rateText = "$value 次/分钟"
                                    Text(
                                        text = rateText,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    // 呼吸率状态评估
                                    val (statusText, statusColor) = when {
                                        value < 12 -> Pair("偏低", Color.Blue)
                                        value > 20 -> Pair("偏高", Color.Red)
                                        else -> Pair("正常", Color.Green)
                                    }
                                    
                                    Text(
                                        text = statusText,
                                        fontSize = 14.sp,
                                        color = statusColor
                                    )
                                }
                                
                                if (index < respiratoryHistory.size - 1) {
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
            
            // 呼吸率知识
            item {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "呼吸率知识",
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
                            text = "呼吸率正常值范围",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "• 成人: 12-20 次/分钟",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 儿童: 20-30 次/分钟",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "• 新生儿: 30-40 次/分钟",
                            fontSize = 14.sp
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "呼吸率测量的重要性",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "呼吸率是重要的生命体征，可反映呼吸系统和心血管系统的健康状况。异常的呼吸频率可能是多种疾病的早期信号，包括肺炎、哮喘、慢性阻塞性肺病等呼吸系统疾病，以及心衰等心血管疾病。",
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
                    Text("1. 请保持平静自然的呼吸")
                    Text("2. 将手机摄像头对准胸部或面部")
                    Text("3. 测量过程中保持静止")
                    Text("4. 尝试在安静、光线充足的环境中测量")
                    Text("5. 如呼吸率异常，建议重新测量或咨询医生")
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
