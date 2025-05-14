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

class hashMap<K,V>(capacity: Int = 16): MutableMap<K,V>{

     // private var mutableSize = 0 // ????
    override var size: Int = 0 // COMO ATUALIZAR O VALOR DO TAMANNHO???

    // private var mutableCapacity = capacity // ?????
    override var capacity: Int = capacity // COMO ATUALIZAR O VALOR DA CAPACIDADE???

    private var hashTable = arrayOfNulls<HashNode<K, V>?>(capacity)

    private fun hash(e: K): Int {
        val idx = e.hashCode() % capacity
        return if (idx < 0) idx + capacity else idx
    }

    override operator fun get(key: K): V? {

        var node = hashTable[hash(key)]

        while(node != null){
            if (node.key == key) return node.value
            else node = node.next
        }
        return null
    }

    override fun put(key: K, value: V): V? {

        val idx = hash(key)
        val oldValue = get(key)
        var node = hashTable[idx]

    when {
        node == null -> {
            hashTable[idx] = HashNode(key, value, null)
            size++
        }

        oldValue == null -> {
            hashTable[idx] = HashNode(key, value, hashTable[idx])
            size++
        }

        else -> { // ??????
            while (node?.key != key) {
                node = node?.next
            }
            node?.value = value
            return oldValue
        } }

        if (size * size/capacity >= capacity) {
            capacity *= 2
            val newTable = arrayOfNulls<HashNode<K, V>>(capacity)

            hashTable.forEach { // PODEMOS USAR ????? (CASO NÃO POSSAMOS, SUBSTITUIR POR UM LOOP FOR)
                if(it != null) {
                    newTable[hash(it.key)] = it
                }
            }

            hashTable = newTable
        }
            return oldValue
    }


    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K,V>> {
        TODO("HAVEN´T BEEN IMPLEMENTED YET")
    }
}
/*
class HashMap<K, V> (initialCapacity: Int = 16, val loadFactor: Float = 0.75f) /: MutableMap<K, V>/ {
    private class HashNode<K, V>(override val key: K, override var value: V,
                                 var next: HashNode<K, V>? = null
                                ): MutableMap.MutableEntry<K,V> {
        var hc = key.hashCode()
        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }

    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)


    private fun expand() {
        TODO()
    }
}
 */