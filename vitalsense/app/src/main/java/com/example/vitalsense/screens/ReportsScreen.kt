package com.example.vitalsense.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.example.vitalsense.components.BottomNavigationBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReportsScreen(navController: NavHostController) {
    // 报告分类
    val tabs = listOf("日报", "周报", "月报", "年度报告")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()
    
    // 预先获取色彩
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    
    // 模拟报告数据
    val dailyReports = remember {
        listOf(
            Report(
                "2025-06-03",
                "每日健康报告",
                "您今天的各项健康指标均在正常范围内。心率平均75次/分钟，血氧饱和度平均98%，呼吸率平均16次/分钟。",
                Icons.Default.ThumbUp,
                "良好",
                Color.Green
            ),
            Report(
                "2025-06-02",
                "每日健康报告",
                "您的心率略高于正常范围，建议注意休息。血氧和呼吸率指标正常。",
                Icons.Default.Warning,
                "注意",
                Color(0xFFFFA500) // Orange
            ),
            Report(
                "2025-06-01",
                "每日健康报告",
                "所有健康指标均处于理想范围内。今天您完成了10000步的运动目标！",
                Icons.Default.ThumbUp,
                "优秀",
                Color.Green
            )
        )
    }
    
    val weeklyReports = remember {
        listOf(
            Report(
                "2025年第22周",
                "每周健康总结",
                "本周您的平均心率为72次/分钟，处于理想状态。血压和血氧指标均稳定在正常范围。本周运动量较上周提升15%。",
                Icons.Default.ThumbUp,
                "良好",
                Color.Green
            ),
            Report(
                "2025年第21周",
                "每周健康总结",
                "本周您的血压出现轻微波动，但仍在正常范围内。其他指标均正常。建议保持规律作息和均衡饮食。",
                Icons.Default.Info,
                "一般",
                primaryColor
            )
        )
    }
    
    val monthlyReports = remember {
        listOf(
            Report(
                "2025年5月",
                "月度健康报告",
                "本月健康状况总体良好。与上月相比，心肺功能有所提升，血压趋于稳定。建议继续保持健康生活方式。",
                Icons.Default.ThumbUp,
                "良好",
                Color.Green
            )
        )
    }
    
    val yearlyReports = remember {
        listOf(
            Report(
                "2024年度",
                "年度健康总结",
                "过去一年您的健康状况总体稳定。心脏健康指标有所提升，血压控制良好。建议来年增加有氧运动频率，保持健康饮食习惯。",
                Icons.Default.ThumbUp,
                "良好",
                Color.Green
            )
        )
    }
    
    // 根据tab获取对应报告
    val currentReports = remember(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> dailyReports
            1 -> weeklyReports
            2 -> monthlyReports
            3 -> yearlyReports
            else -> emptyList()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "健康报告",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = { /* 分享功能 */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "分享"
                        )
                    }
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
            // 标签栏
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 16.dp,
                containerColor = primaryColor.copy(alpha = 0.1f),
                contentColor = primaryColor
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }
            
            // 分页内容
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val reports = when (page) {
                    0 -> dailyReports
                    1 -> weeklyReports
                    2 -> monthlyReports
                    3 -> yearlyReports
                    else -> emptyList()
                }
                
                ReportList(reports = reports)
            }
        }
    }
}

@Composable
fun ReportList(reports: List<Report>) {
    if (reports.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "暂无报告",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 16.sp
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reports.size) { index ->
                val report = reports[index]
                ReportCard(report = report)
            }
            
            // 底部空间
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ReportCard(report: Report) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 头部信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 报告标题
                Text(
                    text = report.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                // 状态标签
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = report.statusColor.copy(alpha = 0.1f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = report.icon,
                            contentDescription = null,
                            tint = report.statusColor,
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = report.status,
                            fontSize = 12.sp,
                            color = report.statusColor
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 日期
            Text(
                text = "日期: ${report.date}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 报告内容
            Text(
                text = report.content,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 操作按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // 生成PDF按钮
                TextButton(
                    onClick = { /* 生成PDF功能 */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = "导出PDF",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("导出PDF")
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // 查看详情按钮
                TextButton(
                    onClick = { /* 查看详情功能 */ }
                ) {
                    Text("查看详情")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "查看详情",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

data class Report(
    val date: String,
    val title: String,
    val content: String,
    val icon: ImageVector,
    val status: String,
    val statusColor: Color
)
