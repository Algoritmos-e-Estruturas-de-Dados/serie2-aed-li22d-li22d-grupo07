package serie2.part4

interface AEDMutableMap<K,V> : Iterable<AEDMutableMap.MutableEntry<K, V>> {
    interface MutableEntry<K, V>{
        val key: K
        var value: V
        fun setValue(newValue: V): V
    }
    val size: Int
    val capacity: Int
    operator fun get(key: K): V?
    fun put(key: K, value: V): V?
}