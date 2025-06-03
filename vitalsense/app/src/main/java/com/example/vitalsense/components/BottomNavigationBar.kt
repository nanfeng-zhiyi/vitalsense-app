package com.example.vitalsense.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vitalsense.navigation.Screen

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // 定义底部导航栏的项目
    val items = listOf(
        BottomNavItem("健康", Screen.Dashboard.route, Icons.Default.Dashboard),
        BottomNavItem("心率", Screen.HeartRate.route, Icons.Default.Favorite),
        BottomNavItem("问答", Screen.QA.route, Icons.Default.QuestionAnswer),
        BottomNavItem("报告", Screen.Reports.route, Icons.Default.Description),
        BottomNavItem("我的", Screen.Mine.route, Icons.Default.Person)
    )
    
    // 获取当前导航路由
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // 创建底部导航栏
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                label = { Text(text = item.name) },
                icon = { Icon(item.icon, contentDescription = item.name) },
                selected = currentRoute == item.route,
                onClick = {
                    // 避免重复导航到当前页面
                    if(currentRoute != item.route) {
                        // 导航到选中的页面
                        navController.navigate(item.route) {
                            // 避免构建重复的栈
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // 避免重复点击
                            launchSingleTop = true
                            // 保存状态
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
