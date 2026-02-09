
package com.galaxyhackkit.extra

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread
import kotlin.math.PI
import kotlin.math.sin

class ToneGeneratorActivity : AppCompatActivity() {
    @Volatile private var playing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tone)
        val edt = findViewById<EditText>(R.id.edtFreq)
        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val f = edt.text.toString().toIntOrNull() ?: 18000
            startTone(f)
        }
        findViewById<Button>(R.id.btnStop).setOnClickListener { playing = false }
    }

    private fun startTone(freq: Int) {
        if (playing) return
        playing = true
        thread {
            val sr = 48000
            val bufferSize = AudioTrack.getMinBufferSize(sr, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)
            val track = AudioTrack(AudioManager.STREAM_MUSIC, sr, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM)
            track.play()
            var t = 0.0
            val amp = 0.2 // 20% de volume para segurança
            val buf = ShortArray(1024)
            while (playing) {
                for (i in buf.indices) {
                    val v = sin(2.0 * PI * freq * t) * amp
                    buf[i] = (v * Short.MAX_VALUE).toInt().toShort()
                    t += 1.0 / sr
                }
                track.write(buf, 0, buf.size)
            }
            track.stop(); track.release()
        }
    }
}
