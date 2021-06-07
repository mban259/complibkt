package mathematics

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ModIntTest {
    @Test
    fun plus1() {
        val mint1 = ModInt(10)
        val mint2 = mint1 + 1000000000
        assertEquals(3, mint2.num)
    }

    @Test
    fun plus2() {
        val mint1 = ModInt(10)
        val mint2 = mint1 + 100000000000000
        assertEquals(100000000000010 % 1000000007, mint2.num)
    }
}