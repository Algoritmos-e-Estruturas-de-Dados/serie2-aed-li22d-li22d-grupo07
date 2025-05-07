package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null)
/*
 --- ERRO NO TESTE "testOnlyOneEven"
 */
fun splitEvensAndOdds(list:Node<Int>){
    var node = list.next
    while (node != list){
        if(node != null){ // NULL SAFETY
            if (node.value % 2 == 0){
                val nodeValue = node.value
                remove(list, nodeValue, { a: Int, b: Int -> a - b })
                add(list, nodeValue)
            }
            node = node.next
        }
    }
}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    var nodeList1 = list1.next
    var nodeList2 = list2.next

    var listHead: Node<T>? = null
    var lastNode: Node<T>? = null

    while (nodeList1 != list1 && nodeList2 != list2) {
        if (nodeList1 != null && nodeList2 != null) { // NULL SAFETY

            val comparison = cmp.compare(nodeList1.value, nodeList2.value)

            /*
            if (comparison == 0) {
                if (listHead == null) {
                    listHead = Node(nodeList1.value, null, null)
                    lastNode = listHead
                }
                else {
                    lastNode = addToEnd(lastNode, nodeList1.value) // Adiciona à lista o nó duplicado
                }

                remove(list1, nodeList1.value, cmp) // Remove o nó de ambas as listas
                remove(list2, nodeList2.value, cmp)
            }

            if (comparison < 0) {
                nodeList1 = nodeList1.next
            }
            else nodeList2 = nodeList2.next
        } */

            when {
                comparison == 0 -> {
                    if (listHead == null) {
                    listHead = Node(nodeList1.value, null, null)
                    lastNode = listHead
                    }
                    else {
                        lastNode = addToEnd(lastNode, nodeList1.value) // Adiciona à lista o nó duplicado
                    }

                    remove(list1, nodeList1.value, cmp) // Remove o nó de ambas as listas
                    remove(list2, nodeList2.value, cmp)

                    nodeList2 = nodeList2.next
                }

               comparison < 0 -> { nodeList1 = nodeList1.next }

                else -> { nodeList2 = nodeList2.next }
            }
        }
    }
    return listHead
}


// FUNÇÃO QUE ADICIONA ELEMENTOS AO FIM DA LISTA. (ADAPTADA DA FUNÇÃO "ADD" DAS LISTAS DUPLAMENTE LIGADAS FORNECIDA PELO DOCENTE)
private fun <E> addToEnd(head: Node<E>?, newItem: E): Node<E>? {
    var head = head
    var x = Node(newItem, null, null)
    x.previous = head
    if (head != null) head.next = x
    head = x
    x.next = null
    return head
}