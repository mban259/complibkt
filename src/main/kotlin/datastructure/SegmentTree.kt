package datastructure

class SegmentTree<T>(val n: Int, private val op: (T, T) -> T, private val id: T) {

    private val array: MutableList<T>
    private var size: Int = 1

    init {
        while (size < n) {
            size *= 2
        }
        array = MutableList(2 * size) { id }
    }

    constructor(a: Array<T>, op: (T, T) -> T, id: T) : this(a.size, op, id) {
        for (i in 0 until n) {
            array[i + size] = a[i]
        }
        for (i in size - 1 downTo 1) {
            array[i] = op(array[2 * i], array[2 * i + 1])
        }
    }

    operator fun set(i: Int, item: T) {
        assert(i in 0 until n)
        var ptr = i + size
        array[ptr] = item
        while (ptr > 1) {
            ptr /= 2
            array[ptr] = op(array[2 * ptr], array[2 * ptr + 1])
        }
    }

    operator fun get(i: Int) = array[i + size]

    /**
     * op(a[l],a[l+1],...a[r-1])を求める
     */
    fun query(l: Int, r: Int): T {
        assert(l in 0 until n)
        assert(r in l..n)
        var sml = id
        var smr = id
        var left = l + size
        var right = r + size
        while (left < right) {
            if ((left and 1) != 0) sml = op(sml, array[left++])
            if ((right and 1) != 0) smr = op(array[--right], smr)
            left = left shr 1
            right = right shr 1
        }
        return op(sml, smr)
    }


    fun all(): T = array[1]

    /**
     * f(op(a[l],a[l+1],...a[r-1])) = trueとなる最大のrを返す
     */
    fun maxRight(l: Int, f: (T) -> Boolean): Int {
        assert(l in 0..n)
        assert(f(id))
        if (l == n) return n
        var left = l + size
        var sm = id

        do {
            while (left % 2 == 0) left = left shr 1
            if (!f(op(sm, array[left]))) {
                while (left < size) {
                    left = left shl 1
                    if (f(op(sm, array[left]))) {
                        sm = op(sm, array[left])
                        left++
                    }
                }
                return left - size
            }
            sm = op(sm, array[left])
            left++
        } while ((left and -left) != left)
        return n
    }

    /**
     * f(op(a\[l\] ,a[l+1],...a[r-1])) = trueとなる最小のlを返します
     */
    fun minLeft(r: Int, f: (T) -> Boolean): Int {
        assert(r in 0..n)
        assert(f(id))
        if (r == 0) return 0
        var right = r + size
        var sm = id
        do {
            right--
            while (right > 1 && (right % 2 != 0)) right = right shr 1
            if (!f(op(array[right], sm))) {
                while (right < size) {
                    right = (2 * right + 1)
                    if (f(op(array[right], sm))) {
                        sm = op(array[right], sm)
                        right--
                    }
                }
                return right + 1 - size
            }
            sm = op(array[right], sm)
        } while ((right and -right) != right)
        return 0
    }
}