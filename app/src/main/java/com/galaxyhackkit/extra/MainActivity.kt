
package com.galaxyhackkit.extra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galaxyhackkit.extra.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnNfc.setOnClickListener { startActivity(Intent(this, NfcReaderActivity::class.java)) }
        b.btnBle.setOnClickListener { startActivity(Intent(this, BleHidActivity::class.java)) }
        b.btnWifi.setOnClickListener { startActivity(Intent(this, WifiScanActivity::class.java)) }
        b.btnHttp.setOnClickListener { startActivity(Intent(this, HttpControlActivity::class.java)) }
        b.btnAudit.setOnClickListener { startActivity(Intent(this, NetworkAuditActivity::class.java)) }
        b.btnTone.setOnClickListener { startActivity(Intent(this, ToneGeneratorActivity::class.java)) }
    }
}
