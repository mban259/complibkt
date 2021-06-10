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

        fun pow(v: Int, k: Long): ModInt = pow(v.toLong(), k)
        fun pow(v: ModInt, k: Int) = pow(v.num, k.toLong())
        fun pow(v: Int, k: Int) = pow(v.toLong(), k.toLong())
        fun pow(v: Long, k: Int) = pow(v, k.toLong())

        fun inverse(v: ModInt) = pow(v.num, mod - 2)
        fun inverse(v: Long) = pow(v, mod - 2)
        fun inverse(v: Int) = pow(v.toLong(), mod - 2)
    }

    constructor(num: Int) : this(num.toLong())

    operator fun plus(right: ModInt): ModInt {
        var ret = num + right.num
        if (ret >= mod) ret -= mod
        return ModInt(ret)
    }

    operator fun plus(right: Long) = plus(rem(right))
    operator fun plus(right: Int) = plus(rem(right))

    operator fun minus(right: ModInt): ModInt {
        var ret = num - right.num
        if (ret < 0) ret += mod
        return ModInt(ret)
    }

    operator fun minus(right: Long) = minus(rem(right))
    operator fun minus(right: Int) = minus(rem(right))

    operator fun times(right: ModInt) = ModInt((num * right.num) % mod)
    operator fun times(right: Long) = times(rem(right))
    operator fun times(right: Int) = times(rem(right))

    override fun toString(): String {
        return num.toString()
    }


    fun rem(num: Long): ModInt {
        return if (num >= 0) ModInt(num % mod) else ModInt(num % mod + mod)
    }

    fun rem(num: Int): ModInt {
        return if (num >= 0) ModInt(num % mod) else ModInt(num % mod + mod)
    }
}

class ModIntArray(private val ar: LongArray) : Collection<ModInt> {
    operator fun get(i: Int) = ModInt(ar[i])
    operator fun set(i: Int, v: ModInt) {
        ar[i] = v.num
    }

    override val size = ar.size
    override fun contains(element: ModInt) = ar.contains(element.num)
    override fun containsAll(elements: Collection<ModInt>) = elements.all(::contains)
    override fun isEmpty() = ar.isEmpty()
    override fun iterator(): Iterator<ModInt> = object : Iterator<ModInt> {
        var index = 0
        override fun hasNext(): Boolean = index < size
        override fun next(): ModInt = get(index++)
    }
}

fun ModIntArray(size: Int) = ModIntArray(LongArray(size))
fun ModIntArray(size: Int, init: (Int) -> ModInt) = ModIntArray(LongArray(size) { init(it).num })
