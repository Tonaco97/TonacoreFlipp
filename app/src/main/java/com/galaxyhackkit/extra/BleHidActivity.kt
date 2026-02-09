
package com.galaxyhackkit.extra

import android.bluetooth.*
import android.bluetooth.hid.BluetoothHidDevice
import android.bluetooth.hid.BluetoothHidDeviceAppQosSettings
import android.bluetooth.hid.BluetoothHidDeviceAppSdpSettings
import android.bluetooth.hid.BluetoothHidDeviceCallback
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BleHidActivity : AppCompatActivity() {
    private var hid: BluetoothHidDevice? = null
    private var host: BluetoothDevice? = null
    private lateinit var log: TextView

    private val sdp by lazy {
        BluetoothHidDeviceAppSdpSettings(
            "GalaxyKB", "Keyboard demo", "GalaxyHackKit",
            BluetoothHidDevice.SUBCLASS1_KEYBOARD,
            byteArrayOf(
                0x05, 0x01, 0x09, 0x06, 0xA1, 0x01, 0x05, 0x07, 0x19, 0xE0.toByte(), 0x29, 0xE7.toByte(),
                0x15, 0x00, 0x25, 0x01, 0x75, 0x01, 0x95, 0x08, 0x81, 0x02, 0x95, 0x01, 0x75, 0x08,
                0x81, 0x01, 0x95, 0x06, 0x75, 0x08, 0x15, 0x00, 0x26, 0xA4.toByte(), 0x00, 0x05, 0x07,
                0x19, 0x00, 0x29, 0xA4.toByte(), 0x81, 0x00, 0xC0
            )
        )
    }
    private val qos = BluetoothHidDeviceAppQosSettings(0,9,0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        log = TextView(this).apply { text = "1) Emparelhe como 'GalaxyKB' no host.\n2) Toque no botão para enviar 'h'." }
        val btn = Button(this).apply { text = "Enviar tecla h" }
        root.addView(log); root.addView(btn); setContentView(root)

        val manager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        manager.adapter.getProfileProxy(this, object: BluetoothProfile.ServiceListener{
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                if (profile == BluetoothProfile.HID_DEVICE) {
                    hid = proxy as BluetoothHidDevice
                    hid?.registerApp(sdp, null, qos, Runnable {}, object: BluetoothHidDeviceCallback(){
                        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
                            log.append("\nStatus HID: $registered device=$pluggedDevice")
                            if (registered) host = pluggedDevice
                        }
                    })
                }
            }
            override fun onServiceDisconnected(profile: Int) {}
        }, BluetoothProfile.HID_DEVICE)

        btn.setOnClickListener {
            val report = byteArrayOf(0x00, 0x0B, 0x00, 0,0,0,0,0) // 'h'
            host?.let { h -> hid?.sendReport(h, 0, report); hid?.sendReport(h, 0, ByteArray(8){0}) }
            log.append("\nTentado enviar...")
        }
    }
}
