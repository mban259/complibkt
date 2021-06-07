package mathematics

class ModInt(val num: Long) {
    companion object {
        const val mod: Long = 1000000007L
        // const val mod: Long = 998244353L

        fun pow(v: ModInt, k: Long) = pow(v.num, k)

        fun pow(v: Long, k: Long): ModInt {
            var ret = 1L
            var tmpV = v
            var tmpK = k % (mod - 1)
            while (tmpK > 0) {
                if ((tmpK and 1L) == 1L) ret = (ret * tmpV) % mod
                tmpK = tmpK shr 1
                tmpV = (tmpV * tmpV) % mod
            }
            return ModInt(ret)
        }

        fun pow(v: Number, k: Long): ModInt = pow(v.toLong(), k)
        fun pow(v: ModInt, k: Number) = pow(v.num, k.toLong())
        fun pow(v: Number, k: Number) = pow(v.toLong(), k.toLong())

        fun inverse(v: ModInt) = pow(v.num, mod - 2)
        fun inverse(v: Number) = pow(v.toLong(), mod - 2)

    }

    constructor(num: Number) : this(num.toLong())

    operator fun plus(right: ModInt): ModInt {
        var ret = num + right.num
        if (ret >= mod) ret -= mod
        return ModInt(ret)
    }

    operator fun plus(right: Number) = plus(rem(right))

    operator fun minus(right: ModInt): ModInt {
        var ret = num - right.num
        if (ret < 0) ret += mod
        return ModInt(ret)
    }

    operator fun minus(right: Number) = minus(rem(right))

    operator fun times(right: ModInt) = ModInt((num * right.num) % mod)
    operator fun times(right: Number) = times(rem(right))

    override fun toString(): String {
        return num.toString()
    }


    private fun rem(num: Number): ModInt {
        val l = num.toLong()
        return if (l >= 0) ModInt(l % mod) else ModInt(l % mod + mod)
    }
}