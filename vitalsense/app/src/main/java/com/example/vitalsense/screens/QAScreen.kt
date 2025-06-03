package com.example.vitalsense.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QAScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    
    // 预设的问答对
    val qaList = remember {
        listOf(
            QA(
                "什么是正常的心率范围？",
                "正常成人的静息心率通常在60-100次/分钟之间。运动员由于心脏功能更强，心率可能低至40-60次/分钟。心率过快(>100次/分钟)称为心动过速，心率过慢(<60次/分钟)称为心动过缓。"
            ),
            QA(
                "血压多少算正常？",
                "正常血压范围为收缩压<120mmHg且舒张压<80mmHg。血压120-129/<80mmHg为血压升高。血压130-139/80-89mmHg为高血压1期。血压≥140/90mmHg为高血压2期。血压>180/120mmHg为高血压危象，需立即就医。"
            ),
            QA(
                "血氧饱和度多少算正常？",
                "健康人的血氧饱和度通常在95%-100%之间。当血氧饱和度低于90%时，被视为低氧血症，应及时就医。低于80%可能危及生命。"
            ),
            QA(
                "如何正确测量血压？",
                "测量血压前，应休息5分钟；不要饮用咖啡或吸烟；坐姿，背部支撑，脚平放地面；袖带应位于心脏高度；测量过程中不要说话；建议连续测量2-3次，取平均值；最好在固定时间段测量。"
            ),
            QA(
                "为什么呼吸率是重要的健康指标？",
                "呼吸率是反映呼吸系统和整体健康状况的关键指标。正常成人呼吸率为12-20次/分钟。呼吸率异常可能提示肺部疾病、心脏问题或代谢紊乱。持续的呼吸率变化可能是慢性疾病的早期信号。"
            ),
            QA(
                "如何改善心脏健康？",
                "定期有氧运动，如每周150分钟的中等强度活动；健康饮食，减少盐、糖和饱和脂肪的摄入；保持健康体重；戒烟限酒；控制血压和胆固醇；充分休息，减轻压力；定期体检监测心脏健康指标。"
            ),
            QA(
                "什么是心房颤动？",
                "心房颤动是一种常见的心律失常，心房不规则收缩，可能导致心脏不能有效泵血。症状包括心悸、乏力、气短和焦虑。风险因素包括高血压、心脏病、甲状腺疾病、糖尿病和年龄增长。可能增加中风风险，需要医疗干预。"
            ),
            QA(
                "如何预防中风？",
                "控制高血压；治疗心房颤动；戒烟；控制胆固醇水平；管理糖尿病；保持健康饮食，多吃蔬果；定期运动；限制饮酒；维持健康体重；定期体检，尤其是有家族史的人。"
            )
        )
    }
    
    // 过滤问答列表
    val filteredQA = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            qaList
        } else {
            qaList.filter { 
                it.question.contains(searchQuery, ignoreCase = true) || 
                it.answer.contains(searchQuery, ignoreCase = true) 
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "健康问答",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 搜索框
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("搜索健康问题...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "搜索"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "清除"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(24.dp)
            )
            
            // 问答列表
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(filteredQA.size) { index ->
                    val qa = filteredQA[index]
                    
                    var expanded by remember { mutableStateOf(false) }
                    
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // 问题部分
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = qa.question,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                        contentDescription = if (expanded) "收起" else "展开"
                                    )
                                }
                            }
                            
                            // 答案部分
                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Divider()
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Text(
                                    text = qa.answer,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
                
                // 底部提示
                item {
                    Text(
                        text = "如果您有更多健康问题，请咨询专业医疗人员",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

data class QA(
    val question: String,
    val answer: String
)
