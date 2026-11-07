package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class ExampleUnitTest {

    @Test
    fun testIndonesianCurrencyFormatting() {
        val localeId = Locale("id", "ID")
        val format = NumberFormat.getNumberInstance(localeId)
        val formatted = format.format(1500000)
        assertEquals("1.500.000", formatted)
    }

    @Test
    fun testShortNumberFormatting() {
        fun formatShort(n: Long): String {
            return when {
                n >= 1_000_000 -> {
                    val jt = n / 1_000_000.0
                    String.format(Locale("id", "ID"), "%.1fjt", jt).replace(".0jt", "jt").replace(",0jt", "jt")
                }
                n >= 1_000 -> "${n / 1_000}rb"
                else -> n.toString()
            }
        }

        assertEquals("500", formatShort(500))
        assertEquals("50rb", formatShort(50_000))
        assertTrue(formatShort(1_500_000).contains("1,5jt") || formatShort(1_500_000).contains("1.5jt"))
    }
}
