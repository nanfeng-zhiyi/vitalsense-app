package com.example.vitalsense.screens

// import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
// import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.BottomNavigationBar
import com.example.vitalsense.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(navController: NavHostController) {
    // 文章分类
    val categories = listOf("全部", "心脏健康", "血压管理", "呼吸系统", "睡眠健康", "营养饮食", "慢性病管理")
    var selectedCategory by remember { mutableStateOf("全部") }
    
    // 健康文章
    val healthArticles = remember {
        listOf(
            HealthArticle(
                "心率变异性：健康的重要指标",
                "心率变异性(HRV)是指连续心跳之间时间间隔的变化，是评估心脏健康和自主神经系统功能的重要指标。高HRV通常表示更好的心血管健康和压力适应能力...",
                "心脏健康",
                "专家解读",
                "2025-05-28"
            ),
            HealthArticle(
                "居家血压监测的正确方法",
                "居家血压监测可以帮助您更全面地了解自己的血压状况。正确的测量方法包括：测量前休息5分钟；坐姿，背部有支撑；避免caffeine和烟草；保持安静；记录读数...",
                "血压管理",
                "健康指南",
                "2025-05-30"
            ),
            HealthArticle(
                "呼吸训练改善肺功能的科学依据",
                "规律的呼吸训练可以增强呼吸肌力量，提高肺容量，改善气体交换效率。研究表明，每天10-15分钟的腹式呼吸练习能显著改善肺功能指标...",
                "呼吸系统",
                "研究报告",
                "2025-06-01"
            ),
            HealthArticle(
                "健康睡眠的7个黄金法则",
                "优质睡眠对身心健康至关重要。健康睡眠的关键包括：固定的作息时间；舒适的睡眠环境；睡前放松习惯；避免午后咖啡因；适量运动；控制光照暴露；限制电子设备使用...",
                "睡眠健康",
                "健康指南",
                "2025-06-02"
            ),
            HealthArticle(
                "抗炎饮食与心血管健康",
                "慢性炎症是多种慢性疾病的共同病理机制。富含抗氧化物质和omega-3脂肪酸的饮食可以减轻炎症反应，降低心血管疾病风险。推荐多摄入彩色蔬果、坚果、橄榄油和深海鱼...",
                "营养饮食",
                "专家观点",
                "2025-05-25"
            ),
            HealthArticle(
                "2型糖尿病的综合管理策略",
                "2型糖尿病管理不仅仅是血糖控制，还需要综合考虑血压、血脂、体重和生活方式等因素。个体化治疗方案、规律运动、健康饮食和心理支持缺一不可...",
                "慢性病管理",
                "疾病管理",
                "2025-06-03"
            )
        )
    }
    
    // 过滤文章
    val filteredArticles = remember(selectedCategory) {
        if (selectedCategory == "全部") healthArticles
        else healthArticles.filter { it.category == selectedCategory }
    }
    
    // 健康服务
    val primaryColor = MaterialTheme.colorScheme.primary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val errorColor = MaterialTheme.colorScheme.error
    
    val healthServices = remember {
        listOf(
            HealthService(
                "健康评估",
                "全面了解您的健康状况",
                Icons.Default.AssignmentTurnedIn,
                primaryColor
            ) {
                navController.navigate(Screen.DiseaseScreening.route)
            },
            HealthService(
                "健康统计",
                "查看您的健康数据趋势",
                Icons.Default.ShowChart,
                tertiaryColor
            ) {
                navController.navigate(Screen.HealthStats.route)
            },
            HealthService(
                "健康问答",
                "解答您的健康疑问",
                Icons.Default.QuestionAnswer,
                secondaryColor
            ) {
                navController.navigate(Screen.QA.route)
            },
            HealthService(
                "健康报告",
                "专业的健康分析报告",
                Icons.Default.Description,
                errorColor
            ) {
                navController.navigate(Screen.Reports.route)
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "健康专区",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                actions = {
                    IconButton(onClick = { /* 搜索功能 */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "搜索",
                            modifier = Modifier.size(24.dp)
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
        ) {
            // 健康服务
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "健康服务",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        healthServices.forEach { service ->
                            ServiceItem(service = service)
                        }
                    }
                }
            }
            
            // 分割线
            item {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 文章分类
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "健康知识",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories.size) { index ->
                            val category = categories[index]
                            CategoryChip(
                                category = category,
                                selected = category == selectedCategory,
                                onClick = { selectedCategory = category }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 文章列表
            items(filteredArticles.size) { index ->
                val article = filteredArticles[index]
                ArticleCard(article = article)
            }
            
            // 底部空间
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ServiceItem(service: HealthService) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = service.onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(service.color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = service.title,
                tint = service.color,
                modifier = Modifier.size(32.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = service.title,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        Text(
            text = service.description,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(category) },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "已选择",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun ArticleCard(article: HealthArticle) {
    val category = article.category
    val type = article.type
    val date = article.date
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { /* 打开文章详情 */ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 标题
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 内容摘要
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 底部信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 分类和类型
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = type,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                // 日期
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

data class HealthService(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

data class HealthArticle(
    val title: String,
    val content: String,
    val category: String,
    val type: String,
    val date: String
)
