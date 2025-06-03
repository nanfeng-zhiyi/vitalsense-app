package com.example.vitalsense.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitalsense.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    // 是否显示主题切换对话框
    var showThemeDialog by remember { mutableStateOf(false) }
    // 是否显示健康数据导出对话框
    var showExportDialog by remember { mutableStateOf(false) }
    // 是否显示注销确认对话框
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "设置",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 用户资料区块
            item {
                UserProfileCard()
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // 通用设置
            item {
                Text(
                    text = "通用",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                SettingItem(
                    title = "通知设置",
                    subtitle = "管理应用通知",
                    icon = Icons.Default.Notifications,
                    onClick = { /* 打开通知设置 */ }
                )
                
                SettingItem(
                    title = "应用主题",
                    subtitle = "设置应用界面主题",
                    icon = Icons.Default.Palette,
                    onClick = { showThemeDialog = true }
                )
                
                SettingItem(
                    title = "语言",
                    subtitle = "简体中文",
                    icon = Icons.Default.Language,
                    onClick = { /* 打开语言设置 */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            // 健康设置
            item {
                Text(
                    text = "健康监测",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                SettingItem(
                    title = "测量提醒",
                    subtitle = "每日健康检测提醒",
                    icon = Icons.Default.AccessAlarm,
                    onClick = { /* 打开测量提醒设置 */ }
                )
                
                SettingItem(
                    title = "健康数据导出",
                    subtitle = "导出历史健康数据记录",
                    icon = Icons.Default.FileDownload,
                    onClick = { showExportDialog = true }
                )
                
                SettingItem(
                    title = "健康数据共享",
                    subtitle = "配置健康数据共享选项",
                    icon = Icons.Default.Share,
                    onClick = { /* 打开健康数据共享设置 */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            // 账户设置
            item {
                Text(
                    text = "账户",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                SettingItem(
                    title = "账户信息",
                    subtitle = "修改个人信息",
                    icon = Icons.Default.Person,
                    onClick = { /* 打开账户信息设置 */ }
                )
                
                SettingItem(
                    title = "隐私设置",
                    subtitle = "管理应用权限和数据",
                    icon = Icons.Default.Security,
                    onClick = { /* 打开隐私设置 */ }
                )
                
                SettingItem(
                    title = "同步设置",
                    subtitle = "配置数据同步选项",
                    icon = Icons.Default.Sync,
                    onClick = { /* 打开同步设置 */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            // 关于与支持
            item {
                Text(
                    text = "关于与支持",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                SettingItem(
                    title = "关于应用",
                    subtitle = "版本信息和开发团队",
                    icon = Icons.Default.Info,
                    onClick = { /* 打开关于页面 */ }
                )
                
                SettingItem(
                    title = "帮助与反馈",
                    subtitle = "获取帮助或提交反馈",
                    icon = Icons.Default.HelpOutline,
                    onClick = { /* 打开帮助页面 */ }
                )
                
                SettingItem(
                    title = "检查更新",
                    subtitle = "应用版本: 1.0.0",
                    icon = Icons.Default.Update,
                    onClick = { /* 检查更新 */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            // 注销按钮
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "注销",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("注销账号")
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
    
    // 主题对话框
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("选择主题") },
            text = {
                Column {
                    RadioButton(
                        selected = true,
                        onClick = { /* 选择跟随系统 */ },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text("跟随系统")
                    
                    RadioButton(
                        selected = false,
                        onClick = { /* 选择浅色主题 */ },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text("浅色主题")
                    
                    RadioButton(
                        selected = false,
                        onClick = { /* 选择深色主题 */ },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text("深色主题")
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
    
    // 导出数据对话框
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("导出健康数据") },
            text = {
                Column {
                    Text("选择要导出的数据类型:")
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = { /* 切换复选框 */ }
                        )
                        Text("心率数据")
                    }
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = { /* 切换复选框 */ }
                        )
                        Text("血氧数据")
                    }
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = { /* 切换复选框 */ }
                        )
                        Text("血压数据")
                    }
                    
                    Text(
                        text = "导出格式: CSV",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("导出")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
    
    // 注销确认对话框
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("确认注销") },
            text = { Text("您确定要注销账号吗？这将清除本地保存的所有数据。") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showLogoutDialog = false
                        /* 执行注销操作 */ 
                    }
                ) {
                    Text(
                        text = "确认注销",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
fun UserProfileCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 用户头像
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "用户头像",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 用户信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "用户",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "user@example.com",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "健康档案: 已完成",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            
            // 编辑按钮
            IconButton(
                onClick = { /* 编辑用户资料 */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "编辑",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 图标
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 标题和副标题
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            // 箭头图标
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
