package serie2.part4

import java.util.NoSuchElementException

class AEDHashMap<K,V>(capacity: Int = 13, private val loadFactor: Float = 0.75f): AEDMutableMap<K, V> {

    private class HashNode<K, V>(override val key:K, override var value: V, var next: HashNode<K, V>? = null): AEDMutableMap.MutableEntry<K, V> { // Classe dos nós da tabela
        // override val key: K = key
        // override var value: V = value
        // var next: HashNode<K, V>? = next

        override fun setValue(newValue: V): V {
            value = newValue
            return value
        }
    }
    override var size: Int = 0

    override var capacity: Int = capacity

    private var hashTable = arrayOfNulls<HashNode<K, V>?>(capacity)

    private fun hash(e: K): Int { // Função de dispersão
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

            else -> {
                while (node?.key != key) {
                    node = node?.next
                }
                node?.setValue(value) // Define o novo valor ao nó
                return oldValue
            }
        }

        if (size >= capacity * loadFactor) expand()

        return oldValue
    }

    private fun expand() { // USA O ITERADOR DOS ARRAYS, LOGO, É NECESSÁRIO VERIFICAR CADA UM DOS NÓS DAS LISTAS LIGADAS
        val oldTable = hashTable
        capacity *= 2
        hashTable = arrayOfNulls<HashNode<K, V>>(capacity)
        size = 0 // Redefinido o tamanho da lista porque a função put incrementa-o cada vez que adiciona um elemento (O tamanho no fim é o mesmo)
        for (point in oldTable) {
            var element = point
            while (element != null) { // Percorre os nós da lista ligada
                put(element.key, element.value)
                element = element.next
            }
        }
    }
// --- ITERADOR ---
    private inner class MyIterator: Iterator<AEDMutableMap.MutableEntry<K, V>> {
        var currIdx = -1
        var currNode: HashNode<K, V>? = null
        var list: HashNode<K, V>? = null

        override fun hasNext(): Boolean {
            if (currNode != null) return true
            while (currIdx < capacity) {
                if (list == null) { // Percorre os índices da tabela
                    currIdx++
                    if (currIdx < capacity) list = hashTable[currIdx]
                } else { // Percorre os nós da lista ligada
                    currNode = list
                    list?.let { l -> list = l.next }
                    return true
                }
            }
            return false
        }

        override fun next(): AEDMutableMap.MutableEntry<K, V> {
            if (!hasNext()) throw NoSuchElementException()
            val aux = currNode
            currNode = null
            return aux!!
        }
    }

    override fun iterator(): Iterator<AEDMutableMap.MutableEntry<K, V>> = MyIterator()
}