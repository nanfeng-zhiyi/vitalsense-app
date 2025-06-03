package com.example.vitalsense.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseScreeningScreen(navController: NavHostController) {
    // 当前步骤
    var currentStep by remember { mutableStateOf(0) }
    // 总步骤数
    val totalSteps = 5
    // 选择的答案
    val selectedAnswers = remember { mutableStateListOf<Int?>(null, null, null, null, null) }
    // 评估结果
    var showResult by remember { mutableStateOf(false) }
    // 风险等级
    var riskLevel by remember { mutableStateOf("") }
    
    // 问题列表
    val questions = listOf(
        "您最近两周内有无发热症状？",
        "您是否经常感到胸闷、气短或呼吸困难？",
        "您是否有吸烟习惯？",
        "您的直系亲属中是否有心脑血管疾病患者？",
        "您每周进行运动的频率是？"
    )
    
    // 答案选项
    val answerOptions = listOf(
        listOf("没有", "偶尔有", "经常有"),
        listOf("从不", "偶尔", "经常"),
        listOf("不吸烟", "偶尔吸烟", "经常吸烟"),
        listOf("没有", "有，但不严重", "有，且情况严重"),
        listOf("每周5次以上", "每周2-4次", "很少或不运动")
    )
    
    // 计算风险等级
    fun calculateRiskLevel(): String {
        // 如果有未回答的问题，返回空
        if (selectedAnswers.any { it == null }) return ""
        
        // 计算风险分数
        val score = selectedAnswers.sumOf { it ?: 0 }
        
        return when {
            score <= 3 -> "低风险"
            score <= 7 -> "中等风险"
            else -> "高风险"
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "健康评估",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!showResult) {
                // 问题页面
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // 进度条
                    item {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "问题 ${currentStep + 1}/$totalSteps",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                                
                                Spacer(modifier = Modifier.weight(1f))
                                
                                Text(
                                    text = "${currentStep + 1 * 100 / totalSteps}%",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            
                            LinearProgressIndicator(
                                progress = (currentStep + 1) * 1f / totalSteps,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                    
                    // 当前问题
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = questions[currentStep],
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    
                    // 答案选项
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            answerOptions[currentStep].forEachIndexed { index, option ->
                                val isSelected = selectedAnswers[currentStep] == index
                                
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedAnswers[currentStep] = index
                                        }
                                        .border(
                                            width = 1.dp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp, horizontal = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = isSelected,
                                            onClick = {
                                                selectedAnswers[currentStep] = index
                                            },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = MaterialTheme.colorScheme.primary,
                                                unselectedColor = MaterialTheme.colorScheme.outline
                                            )
                                        )
                                        
                                        Spacer(modifier = Modifier.width(16.dp))
                                        
                                        Text(
                                            text = option,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    
                    // 导航按钮
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // 上一步按钮
                            Button(
                                onClick = {
                                    if (currentStep > 0) {
                                        currentStep--
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                enabled = currentStep > 0
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "上一步",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("上一步")
                            }
                            
                            // 下一步/完成按钮
                            Button(
                                onClick = {
                                    if (selectedAnswers[currentStep] != null) {
                                        if (currentStep < totalSteps - 1) {
                                            currentStep++
                                        } else {
                                            riskLevel = calculateRiskLevel()
                                            showResult = true
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                enabled = selectedAnswers[currentStep] != null
                            ) {
                                Text(if (currentStep < totalSteps - 1) "下一步" else "完成")
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = if (currentStep < totalSteps - 1) Icons.Default.ArrowForward else Icons.Default.Check,
                                    contentDescription = if (currentStep < totalSteps - 1) "下一步" else "完成",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // 结果页面
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 结果卡片
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when (riskLevel) {
                                    "低风险" -> Color.Green.copy(alpha = 0.1f)
                                    "中等风险" -> Color(0xFFFFA500).copy(alpha = 0.1f) // Orange
                                    "高风险" -> Color.Red.copy(alpha = 0.1f)
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = when (riskLevel) {
                                        "低风险" -> Icons.Default.CheckCircle
                                        "中等风险" -> Icons.Default.Warning
                                        "高风险" -> Icons.Default.Error
                                        else -> Icons.Default.Help
                                    },
                                    contentDescription = null,
                                    tint = when (riskLevel) {
                                        "低风险" -> Color.Green
                                        "中等风险" -> Color(0xFFFFA500) // Orange
                                        "高风险" -> Color.Red
                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    modifier = Modifier.size(64.dp)
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Text(
                                    text = "您的健康风险等级",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = riskLevel,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (riskLevel) {
                                        "低风险" -> Color.Green
                                        "中等风险" -> Color(0xFFFFA500) // Orange
                                        "高风险" -> Color.Red
                                        else -> MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            }
                        }
                    }
                    
                    // 健康建议
                    item {
                        Text(
                            text = "健康建议",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                        
                        val recommendations = when (riskLevel) {
                            "低风险" -> listOf(
                                "继续保持健康的生活方式",
                                "定期体检",
                                "均衡饮食，适量运动",
                                "保持良好的作息习惯"
                            )
                            "中等风险" -> listOf(
                                "增加运动频率，每周至少3次，每次30分钟",
                                "减少高盐、高脂肪食物摄入",
                                "戒烟限酒",
                                "定期检测血压和血糖",
                                "每年进行一次全面体检"
                            )
                            "高风险" -> listOf(
                                "建议尽快就医进行专业检查",
                                "严格控制饮食，减少高热量食物摄入",
                                "每天监测血压",
                                "必须戒烟",
                                "在医生指导下进行适当运动",
                                "定期随访"
                            )
                            else -> listOf("无法提供健康建议，请重新评估")
                        }
                        
                        recommendations.forEach { recommendation ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
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
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(end = 16.dp)
                                    )
                                    
                                    Text(
                                        text = recommendation,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    
                    // 操作按钮
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // 重新评估按钮
                            Button(
                                onClick = {
                                    currentStep = 0
                                    selectedAnswers.forEachIndexed { index, _ ->
                                        selectedAnswers[index] = null
                                    }
                                    showResult = false
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "重新评估",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("重新评估")
                            }
                            
                            // 保存结果按钮
                            Button(
                                onClick = {
                                    // 保存健康评估结果
                                    navController.popBackStack()
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Text("保存结果")
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = "保存结果",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
