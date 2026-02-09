
package com.galaxyhackkit.extra

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WifiScanActivity : AppCompatActivity() {
    private lateinit var wifi: WifiManager
    private lateinit var txt: TextView
    private val rcLoc = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_scan)
        wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        txt = findViewById(R.id.txtWifi)
        findViewById<Button>(R.id.btnScan).setOnClickListener { startScan() }
        ensurePerms()
    }

    private fun ensurePerms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), rcLoc)
        }
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val lines = wifi.scanResults.sortedByDescending { it.level }.joinToString("\n") {
                val secured = listOf("WEP","WPA","WPA2","WPA3").any { p -> it.capabilities.contains(p) }
                val sec = if (secured) it.capabilities else "OPEN"
                "${it.SSID}  (${sec})  ${it.level} dBm"
            }
            txt.text = lines
        }
    }

    private fun startScan() {
        registerReceiver(receiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION), RECEIVER_EXPORTED)
        val ok = wifi.startScan()
        if (!ok) txt.text = "Falha ao iniciar scan. Tente novamente."
    }

    override fun onDestroy() {
        super.onDestroy()
        try { unregisterReceiver(receiver) } catch (_: Exception) {}
    }
}
