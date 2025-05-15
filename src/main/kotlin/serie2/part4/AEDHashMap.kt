package serie2.part4

import java.util.NoSuchElementException

class AEDHashMap<K,V>(capacity: Int = 16, private val loadFactor: Float = 0.75f): AEDMutableMap<K, V> {

    private class HashNode<K, V>(key:K, value: V, next: AEDHashMap.HashNode<K, V>? = null): AEDMutableMap.MutableEntry<K, V> {
        override val key: K = key
        override var value: V = value
        var next: AEDHashMap.HashNode<K, V>? = null
        var previous: AEDHashMap.HashNode<K, V>? = null

        override fun setValue(newValue: V): V {
            value = newValue
            return value
        }
    }
    // private var mutableSize = 0 // ????
    override var size: Int = 0 // COMO ATUALIZAR O VALOR DO TAMANNHO???

    // private var mutableCapacity = capacity // ?????
    override var capacity: Int = capacity // COMO ATUALIZAR O VALOR DA CAPACIDADE???

    private var hashTable = arrayOfNulls<AEDHashMap.HashNode<K, V>?>(capacity)

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
                hashTable[idx] = AEDHashMap.HashNode(key, value, null)
                size++
            }

            oldValue == null -> {
                hashTable[idx] = AEDHashMap.HashNode(key, value, hashTable[idx])
                size++
            }

            else -> { // ??????
                while (node?.key != key) {
                    node = node?.next
                }
                node?.value = value
                return oldValue
            } }

        if (size * loadFactor >= capacity) {
            capacity *= 2
            val newTable = arrayOfNulls<AEDHashMap.HashNode<K, V>>(capacity)

            hashTable.forEach { // PODEMOS USAR ????? (CASO NÃO POSSAMOS, SUBSTITUIR POR UM LOOP FOR)
                if(it != null) {
                    newTable[hash(it.key)] = it
                }
            }

            hashTable = newTable
        }
        return oldValue
    }

    private fun expand() {
        TODO()
    }

    private inner class MyIterator: Iterator<AEDMutableMap.MutableEntry<K, V>> {
        var currIdx = -1;
        var currNode: AEDHashMap.HashNode<K, V>? = null
        var list: AEDHashMap.HashNode<K, V>? = null

        override fun hasNext(): Boolean {
            if (currNode != null) return true
            while (currIdx < capacity) {
                if (list == null) {
                    currIdx++
                    if (currIdx < capacity) list = hashTable[currIdx]
                } else {
                    currNode = list
                    list?.let { l -> list = l.next }
                    return true
                }
            }
            return false;
        }

        override fun next(): AEDMutableMap.MutableEntry<K, V> {
            if (!hasNext()) throw NoSuchElementException()
            val aux = currNode
            currNode = null
            return aux!! //aux ?: Any() as MutableMap.MutableEntry<K, V>
        }
    }

    override fun iterator(): Iterator<AEDMutableMap.MutableEntry<K, V>> = MyIterator()
}