// Mapeamento simplificado de Scancodes para automação
object HidPayloads {
    // Exemplo: Abrir terminal (Win+R) e digitar um comando
    val OPEN_TERMINAL = arrayOf(
        byteArrayOf(0x08, 0x15, 0x00, 0, 0, 0, 0, 0), // GUI + R
        byteArrayOf(0x00, 0x00, 0x00, 0, 0, 0, 0, 0), // Release
        byteArrayOf(0x00, 0x06, 0x00, 0, 0, 0, 0, 0), // 'c'
        byteArrayOf(0x00, 0x10, 0x00, 0, 0, 0, 0, 0), // 'm'
        byteArrayOf(0x00, 0x07, 0x00, 0, 0, 0, 0, 0), // 'd'
        byteArrayOf(0x00, 0x28, 0x00, 0, 0, 0, 0, 0)  // ENTER
    )
}
