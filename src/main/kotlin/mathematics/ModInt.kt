package mathematics

inline class ModInt(val num: Long) {
    companion object {
        const val mod: Long = 1000000007L
        // const val mod: Long = 998244353L

        inline fun pow(v: ModInt, k: Long) = pow(v.num, k)

        inline fun pow(v: Long, k: Long): ModInt {
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

        inline fun pow(v: Int, k: Long): ModInt = pow(v.toLong(), k)
        inline fun pow(v: ModInt, k: Int) = pow(v.num, k.toLong())
        inline fun pow(v: Int, k: Int) = pow(v.toLong(), k.toLong())
        inline fun pow(v: Long, k: Int) = pow(v, k.toLong())

        inline fun inverse(v: ModInt) = pow(v.num, mod - 2)
        inline fun inverse(v: Long) = pow(v, mod - 2)
        inline fun inverse(v: Int) = pow(v.toLong(), mod - 2)
    }

    constructor(num: Int) : this(num.toLong())

    inline operator fun plus(right: ModInt): ModInt {
        var ret = num + right.num
        if (ret >= mod) ret -= mod
        return ModInt(ret)
    }

    inline operator fun plus(right: Long) = plus(rem(right))
    inline operator fun plus(right: Int) = plus(rem(right))

    inline operator fun minus(right: ModInt): ModInt {
        var ret = num - right.num
        if (ret < 0) ret += mod
        return ModInt(ret)
    }

    inline operator fun minus(right: Long) = minus(rem(right))
    inline operator fun minus(right: Int) = minus(rem(right))

    inline operator fun times(right: ModInt) = ModInt((num * right.num) % mod)
    inline operator fun times(right: Long) = times(rem(right))
    inline operator fun times(right: Int) = times(rem(right))

    override inline fun toString(): String {
        return num.toString()
    }


    inline fun rem(num: Long): ModInt {
        return if (num >= 0) ModInt(num % mod) else ModInt(num % mod + mod)
    }

    inline fun rem(num: Int): ModInt {
        return if (num >= 0) ModInt(num % mod) else ModInt(num % mod + mod)
    }
}

class ModIntArray(val ar: LongArray) : Collection<ModInt> {
    inline operator fun get(i: Int) = ModInt(ar[i])
    inline operator fun set(i: Int, v: ModInt) {
        ar[i] = v.num
    }

    override inline val size get() = ar.size
    override inline fun contains(element: ModInt) = ar.contains(element.num)
    override inline fun containsAll(elements: Collection<ModInt>) = elements.all(::contains)
    override inline fun isEmpty() = ar.isEmpty()
    override inline fun iterator(): Iterator<ModInt> = object : Iterator<ModInt> {
        var index = 0
        override inline fun hasNext(): Boolean = index < size
        override inline fun next(): ModInt = get(index++)
    }
}

inline fun ModIntArray(size: Int) = ModIntArray(LongArray(size))
inline fun ModIntArray(size: Int, init: (Int) -> ModInt) = ModIntArray(LongArray(size) { init(it).num })
