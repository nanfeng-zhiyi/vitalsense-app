package com.example.vitalsense.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vitalsense.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.HeartRate.route) {
            HeartRateScreen(navController = navController)
        }
        composable(Screen.BloodPressure.route) {
            BloodPressureScreen(navController = navController)
        }
        composable(Screen.BloodOxygen.route) {
            BloodOxygenScreen(navController = navController)
        }
        composable(Screen.RespiratoryRate.route) {
            RespiratoryRateScreen(navController = navController)
        }
        composable(Screen.DiseaseScreening.route) {
            DiseaseScreeningScreen(navController = navController)
        }
        composable(Screen.HealthStats.route) {
            HealthStatsScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(Screen.QA.route) {
            QAScreen(navController = navController)
        }
        composable(Screen.Reports.route) {
            ReportsScreen(navController = navController)
        }
        composable(Screen.Mine.route) {
            MineScreen(navController = navController)
        }
        composable(Screen.Health.route) {
            HealthScreen(navController = navController)
        }
    }
}
