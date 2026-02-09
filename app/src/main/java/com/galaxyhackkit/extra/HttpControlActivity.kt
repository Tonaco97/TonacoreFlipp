
package com.galaxyhackkit.extra

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class HttpControlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_control)
        val edt = findViewById<EditText>(R.id.edtUrl)
        val btn = findViewById<Button>(R.id.btnSend)
        val out = findViewById<TextView>(R.id.txtResp)

        btn.setOnClickListener {
            val url = edt.text.toString()
            thread {
                try {
                    val conn = (URL(url).openConnection() as HttpURLConnection).apply {
                        connectTimeout = 5000
                        readTimeout = 5000
                        requestMethod = "GET"
                    }
                    val text = conn.inputStream.bufferedReader().readText()
                    runOnUiThread { out.text = "HTTP ${conn.responseCode}\n\n$text" }
                    conn.disconnect()
                } catch (e: Exception) {
                    runOnUiThread { out.text = "Erro: ${e.message}" }
                }
            }
        }
    }
}
