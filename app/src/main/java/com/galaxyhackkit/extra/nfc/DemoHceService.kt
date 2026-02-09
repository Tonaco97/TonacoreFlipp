
package com.galaxyhackkit.extra.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle

class DemoHceService : HostApduService() {
    private val SELECT_OK = byteArrayOf(0x90.toByte(), 0x00.toByte())
    private val UNKNOWN_CMD = byteArrayOf(0x6F.toByte(), 0x00.toByte())
    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {
        if (commandApdu == null || commandApdu.isEmpty()) return UNKNOWN_CMD
        val isSelect = commandApdu.size >= 4 &&
            commandApdu[0] == 0x00.toByte() && commandApdu[1] == 0xA4.toByte()
        return if (isSelect) { "HELLO".toByteArray(Charsets.US_ASCII) + SELECT_OK }
        else { "PING".toByteArray(Charsets.US_ASCII) + SELECT_OK }
    }
    override fun onDeactivated(reason: Int) {}
}
