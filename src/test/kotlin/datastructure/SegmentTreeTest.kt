package datastructure

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.math.max

internal class SegmentTreeTest {

    @Test
    fun aclJInput1() {

        val st = SegmentTree(arrayOf(1, 2, 3, 2, 1), { l, r -> max(l, r) }, 0)
        assertEquals(3, st.query(0, 5))
        assertEquals(3, st.maxRight(1) { it < 3 } + 1)
        st[2] = 1
        assertEquals(2, st.query(1, 4))
        assertEquals(6, st.maxRight(0) { it < 3 } + 1)
    }
}