
package com.galaxyhackkit.extra

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NfcReaderActivity : AppCompatActivity() {
    private var adapter: NfcAdapter? = null
    private lateinit var txt: TextView
    private lateinit var pi: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)
        txt = findViewById(R.id.txt)
        adapter = NfcAdapter.getDefaultAdapter(this)
        val self = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pi = PendingIntent.getActivity(this, 0, self, PendingIntent.FLAG_MUTABLE)
        txt.text = "Aproxime uma tag NFC…"
    }

    override fun onResume() { super.onResume(); adapter?.enableForegroundDispatch(this, pi, null, null) }
    override fun onPause() { super.onPause(); adapter?.disableForegroundDispatch(this) }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            val idHex = tag.id.joinToString("") { String.format("%02X", it) }
            val techs = tag.techList.joinToString(", ")
            txt.text = "UID: $idHex\nTechs: $techs"
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                try {
                    ndef.connect()
                    val msg = ndef.ndefMessage
                    val records = msg?.records?.joinToString("\n") { String(it.payload) } ?: ""
                    txt.append("\n\nNDEF:\n$records")
                    ndef.close()
                } catch (e: Exception) { txt.append("\n\nFalha ao ler NDEF: ${e.message}") }
            } else {
                txt.append("\n\nSem NDEF (pode ser MIFARE/Ultralight/Desfire).")
            }
        }
    }
}
