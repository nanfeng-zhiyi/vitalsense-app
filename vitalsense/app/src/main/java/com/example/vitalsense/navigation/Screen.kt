package com.example.vitalsense.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object HeartRate : Screen("heart_rate")
    object BloodPressure : Screen("blood_pressure")
    object BloodOxygen : Screen("blood_oxygen")
    object RespiratoryRate : Screen("respiratory_rate")
    object DiseaseScreening : Screen("disease_screening")
    object HealthStats : Screen("health_stats")
    object Settings : Screen("settings")
    object QA : Screen("qa")
    object Reports : Screen("reports")
    object Mine : Screen("mine")
    object Health : Screen("health")
}
