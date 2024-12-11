package com.example.pmdm

data class AppModel(
    val appName: String,
    val packageName: String,
    var category: String = "Unrestricted",
    val riskLevel: String = "Low" // Default risk level

)