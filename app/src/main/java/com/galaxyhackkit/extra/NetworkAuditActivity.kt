
package com.galaxyhackkit.extra

import android.content.Context
import android.net.DhcpInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket
import kotlin.concurrent.thread

class NetworkAuditActivity : AppCompatActivity() {
    private lateinit var out: TextView
    private val ports = intArrayOf(22, 80, 443, 554, 8000, 8080, 1883) // comuns/IoT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_audit)
        out = findViewById(R.id.txtLog)
        findViewById<Button>(R.id.btnScanLan).setOnClickListener { scanLan() }
    }

    private fun scanLan() {
        out.text = "Somente para redes próprias. Varredura /24 e portas comuns...\n"
        val wm = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dh: DhcpInfo = wm.dhcpInfo ?: run { out.append("Sem DHCP info."); return }
        val ip = dh.ipAddress
        val net = dh.netmask
        val base = ip and net
        thread {
            for (i in 1..254) {
                val addr = base + i
                val ipStr = String.format("%d.%d.%d.%d",
                    addr and 0xff, (addr shr 8) and 0xff, (addr shr 16) and 0xff, (addr shr 24) and 0xff)
                try {
                    val reachable = InetAddress.getByName(ipStr).isReachable(80)
                    if (reachable) {
                        runOnUiThread { out.append("\nHost: $ipStr (ping)") }
                    }
                    for (p in ports) {
                        try {
                            Socket().use { s ->
                                s.connect(InetSocketAddress(ipStr, p), 120)
                                runOnUiThread { out.append("\n  • porta $p ABERTA") }
                            }
                        } catch (_: Exception) { /* fechada */ }
                    }
                } catch (_: Exception) {}
            }
            runOnUiThread { out.append("\n\nConcluído.") }
        }
    }
}
