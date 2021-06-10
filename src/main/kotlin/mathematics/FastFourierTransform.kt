package mathematics

import kotlin.math.min

class FastFourierTransform {
    companion object {
        fun ceilPow2(n: Int): Int {
            var x = 0
            while ((1 shl x) < n) x++
            return x
        }

        val revBsf = IntArray(1 shl 23) {
            var x = 0
            while ((it and (1 shl x)) != 0) x++
            x
        }
        val sumE = ModIntArray(22) { ModInt(0) }
        val sumIE = ModIntArray(22) { ModInt(0) }

        init {
            val es = ModIntArray(22) { ModInt(0) }
            val ies = ModIntArray(22) { ModInt(0) }

            // 998244353 = 119 * 2^23 + 1
            // g = 3
            // cnt2 = 23
            // (mod-1) >> cnt2 = 119
            val g = 3
            val cnt2 = 23
            var e = ModInt.pow(g, (ModInt.mod - 1) shr cnt2)
            var ie = ModInt.inverse(e)
            for (i in cnt2 downTo 2) {
                es[i - 2] = e
                ies[i - 2] = ie
                e *= e
                ie *= ie
            }
            var now = ModInt(1)
            var iNow = ModInt(1)
            for (i in 0..cnt2 - 2) {
                sumE[i] = es[i] * now
                sumIE[i] = ies[i] * iNow
                now *= ies[i]
                iNow *= es[i]
            }
        }

        fun butterfly(a: ModIntArray, h: Int) {
            for (ph in 1..h) {
                val p = 1 shl (h - ph)
                var now = ModInt(1)
                for (s in 0 until (1 shl (ph - 1))) {
                    val offset = s shl (h - ph + 1)
                    for (i in 0 until p) {
                        val l = a[i + offset]
                        val r = a[i + offset + p] * now
                        a[i + offset] = l + r
                        a[i + offset + p] = l - r
                    }
                    now *= sumE[revBsf[s]]
                }
            }
        }

        fun butterflyInv(a: ModIntArray, h: Int) {
            for (ph in h downTo 1) {
                val p = 1 shl (h - ph)
                var iNow = ModInt(1)
                for (s in 0 until (1 shl (ph - 1))) {
                    val offset = s shl (h - ph + 1)
                    for (i in 0 until p) {
                        val l = a[i + offset]
                        val r = a[i + offset + p]
                        a[i + offset] = l + r
                        a[i + offset + p] = (l - r) * iNow
                    }
                    iNow *= sumIE[revBsf[s]]
                }
            }
        }

        // mod = 998244353
        fun convolution(a: ModIntArray, b: ModIntArray): ModIntArray {
            val n = a.size
            val m = b.size
            if (min(n, m) <= 60) {
                val ans = ModIntArray(n + m - 1) { ModInt(0) }
                for (i in 0 until n) {
                    for (j in 0 until m) {
                        ans[i + j] += a[i] * b[i]
                    }
                }
                return ans
            }
            val h = ceilPow2(n + m - 1)
            val z = 1 shl h
            val tmpA = ModIntArray(z) { if (it < n) a[it] else ModInt(0) }
            butterfly(tmpA, h)
            val tmpB = ModIntArray(z) { if (it < m) b[it] else ModInt(0) }
            butterfly(tmpB, h)
            for (i in 0 until z) {
                tmpA[i] *= tmpB[i]
            }
            butterflyInv(tmpA, h)
            val iz = ModInt.inverse(z)
            return ModIntArray(n + m - 1) { tmpA[it] * iz }
        }
    }
}