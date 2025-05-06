package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null) {
}
/*
 --- ERRO NO TESTE "testOnlyOneEven"
 */
fun splitEvensAndOdds(list:Node<Int>){
    var node = list.next
    while (node != list){
        if(node != null){
            if (node.value % 2 == 0){
                val nodeValue = node.value
                remove(list, nodeValue, { a: Int, b: Int -> a.compareTo(b) })
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
    var previousNode = listHead

    var iterationNumber = 0

    while (nodeList1 != list1 && nodeList2 != list2) {
        if (nodeList1 != null && nodeList2 != null) { // NUNCA SÃO NULOS PORQUE A LISTA É DUPLAMENTE LIGADA CIRCULAR COM SENTINELA

            val comparison = cmp.compare(nodeList1.value, nodeList2.value)

            if (comparison == 0) {
                val node = Node(nodeList1.value, null, previousNode)
                if (iterationNumber == 0)
                    listHead = node
                else {
                    add(listHead, node.value)
                    remove(list1, nodeList1.value, cmp)
                    remove(list2, nodeList2.value, cmp)
                }
            }

            if (comparison < 0) {
                nodeList1 = nodeList1.next
            }
            else nodeList2 = nodeList2.next
        }
        iterationNumber++
    }

    return listHead
}

fun <E> add(head: Node<E>?, newItem: E): Node<E>? {
    var head = head
    var x = Node(newItem, null, null)
    x.next = head
    if (head != null) head.previous = x
    head = x
    x.previous = null
    return head
}