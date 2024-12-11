package com.example.pmdm

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Device Admin Components
    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    // UI Components
    private lateinit var appListRecyclerView: RecyclerView

    // Data
    private val appList = mutableListOf<AppModel>() // List of installed applications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize DevicePolicyManager and AdminReceiver
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, AdminReceiver::class.java)

        // Initialize UI Elements
        appListRecyclerView = findViewById(R.id.app_list_recycler_view)
        appListRecyclerView.layoutManager = LinearLayoutManager(this)

        // Load installed applications
        loadInstalledApplications()

        // Set up RecyclerView with the adapter
        val appListAdapter = AppListAdapter(appList) { app, category ->
            categorizeApp(app, category)
        }
        appListRecyclerView.adapter = appListAdapter
    }

    private fun loadInstalledApplications() {
        val pm = packageManager
        val installedApps = pm.getInstalledApplications(0)

        for (app in installedApps) {
            if ((app.flags and ApplicationInfo.FLAG_SYSTEM) == 0 &&
                (app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {

                val installerInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    pm.getInstallSourceInfo(app.packageName).installingPackageName
                } else {
                    @Suppress("DEPRECATION")
                    pm.getInstallerPackageName(app.packageName)
                }

                if (installerInfo == "com.android.vending") { // Play Store
                    val riskLevel = when (app.packageName) {
                        "com.einnovation.temu", "com.zhiliaoapp.musically" -> "High"
                        "com.fitbit.FitbitMobile", "com.discord" -> "Medium"
                        else -> "Low"
                    }

                    appList.add(
                        AppModel(
                            appName = pm.getApplicationLabel(app).toString(),
                            packageName = app.packageName,
                            riskLevel = riskLevel
                        )
                    )
                }
            }
        }
    }

    private fun categorizeApp(app: AppModel, category: String) {
        app.category = category
        Toast.makeText(this, "${app.appName} categorized as $category", Toast.LENGTH_SHORT).show()

        when (category) {
            "Restricted" -> revokePermissions(app.packageName)
            "Unrestricted" -> Log.d("MainActivity", "${app.appName} marked as Unrestricted. No changes made.")
        }
    }
    private fun revokePermissions(packageName: String) {
        // Informational message to indicate that the function is called
        Toast.makeText(this, "Revoke Permissions called for $packageName (no action taken)", Toast.LENGTH_SHORT).show()

        // Check if the app is a Device Admin
        if (!devicePolicyManager.isAdminActive(adminComponent)) {
            Toast.makeText(this, "App is not a Device Admin!", Toast.LENGTH_LONG).show()
            return
        }

        // Log statement to keep track of this call for debugging purposes
        Log.d("MainActivity", "Revoke Permissions called for $packageName, but no actions are being performed.")
    }

    /**private fun revokePermissions(packageName: String) {
    // List of permissions to revoke
    val permissions = listOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_BACKGROUND_LOCATION",
        "android.permission.READ_CONTACTS",
        "android.permission.WRITE_CONTACTS",
        "android.permission.READ_CALENDAR",
        "android.permission.WRITE_CALENDAR",
        "android.permission.READ_SMS",
        "android.permission.RECEIVE_SMS",
        "android.permission.SEND_SMS",
        "android.permission.READ_PHONE_NUMBERS",
        "android.permission.READ_PHONE_STATE",
        "android.permission.READ_CALL_LOG",
        "android.permission.WRITE_CALL_LOG",
        "android.permission.RECORD_AUDIO",
        "android.permission.CAMERA",
        "android.permission.BODY_SENSORS",
        "android.permission.BODY_SENSORS_BACKGROUND",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.MANAGE_EXTERNAL_STORAGE",
        "android.permission.ACCESS_MEDIA_LOCATION",
        "android.permission.ACCESS_NETWORK_STATE",
        "android.permission.ACCESS_WIFI_STATE",
        "android.permission.BLUETOOTH_SCAN",
        "android.permission.NEARBY_WIFI_DEVICES",
        "android.permission.PACKAGE_USAGE_STATS",
        "android.permission.ACTIVITY_RECOGNITION",
        "android.permission.LOCATION_HARDWARE",
        "android.permission.WRITE_SECURE_SETTINGS",
        "android.permission.MODIFY_PHONE_STATE",
        "android.permission.INSTALL_PACKAGES",
        "android.permission.REQUEST_INSTALL_PACKAGES",
        "android.permission.DELETE_PACKAGES",
        "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE",
        "android.permission.READ_LOGS",
        "android.permission.CAPTURE_AUDIO_OUTPUT",
        "android.permission.DETECT_SCREEN_RECORDING",
        "android.permission.FOREGROUND_SERVICE_LOCATION",
        "android.permission.SYSTEM_ALERT_WINDOW",
        "android.permission.BIND_VPN_SERVICE",
        "android.permission.USE_BIOMETRIC"
    )

    // Check if the app is a Device Admin
    if (!devicePolicyManager.isAdminActive(adminComponent)) {
        Toast.makeText(this, "App is not a Device Admin!", Toast.LENGTH_LONG).show()
        return
    }

    // Revoke permissions for the specified package
    for (permission in permissions) {
        try {
            devicePolicyManager.revokeRuntimePermission(
                adminComponent,
                packageName,
                permission
            )
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to revoke $permission for $packageName", Toast.LENGTH_SHORT).show()
            Log.e("MainActivity", "Error revoking permission: $permission for $packageName", e)
        }
    }

    Toast.makeText(this, "Permissions revoked for $packageName", Toast.LENGTH_LONG).show()
}*/
}