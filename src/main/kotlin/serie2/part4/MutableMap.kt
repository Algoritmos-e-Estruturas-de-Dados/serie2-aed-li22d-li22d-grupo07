package serie2.part4

interface MutableMap<K,V>: Iterable<MutableMap.MutableEntry<K,V>> {
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

data class HashNode<K, V>(var key:K, var value: V, var next: HashNode<K, V>? = null)

class hashMap<K,V>(capacity: Int): MutableMap<K,V>{

    override var size: Int = 0

    override val capacity: Int = capacity

    private var hashTable = arrayOfNulls<HashNode<K, V>?>(capacity)

    override operator fun get(key: K): V? {
        val idx = key.hashCode() % capacity
        var node = hashTable[idx]
        while(node != null){
            if (node.key == key) return node.value
            else node = node.next
        }
        return node
    }

    override fun put(key: K, value: V): V? {
        val idx = key.hashCode() % capacity
        if (hashTable[idx] == null) {
            hashTable[idx] = HashNode(key, value, null)
            return null
        }

        var node = HashNode(key, value, hashTable[idx])
        val previousValue = hashTable[idx]?.value
        hashTable[idx] = node

        size++
        return previousValue
    }

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K,V>> {
        TODO("HAVEN´T BEEN IMPLEMENTED YET")
    }
}