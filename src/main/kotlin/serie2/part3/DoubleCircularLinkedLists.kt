package serie2.part3


// calcula a dimensão da lista
fun <E> size(head: Node<E>): Int {
    var x = head.next
    var count = 0
    while (x != head) {
        count++
        x = x?.next
    }
    return count
}

// verifica se o item com chave k existe na lista
fun <E> contains(head: Node<E>, k: E, cmp: Comparator<E>): Boolean {
    head.value = k
    var x = head.next
    while (cmp.compare(k, x?.value) != 0) x = x?.next
    return x != head
}

// devolve o elemento da lista na posição idx
fun <E> get(head: Node<E>, idx: Int): E? {
    if (idx >= size(head)) return null
    var x = head.next
    var count = 0
    while (x != head) {
        if (count == idx) return x?.value
        count++
        x = x?.next
    }
    return null
}

// insere um novo elemento na primeira posição da lista
fun <E> add(head: Node<E>, newItem: E) {
    val x = Node(newItem)
    x.next = head.next
    head.next?.previous = x
    head.next = x
    x.previous = head
}

// remove o elemento da lista com chave k
fun <E> remove(head: Node<E>, k: E, cmp:Comparator<E>) {
    var x = head.next
    while (x != head) {
        if (x != null) {
            if (cmp.compare(k, x.value) == 0) {
                x.previous?.next = x.next
                x.next?.previous = x.previous
                break
            }
            else x = x.next
        }
    }
}

// mostra a lista
fun <E> showList(head: Node<E>) {
    print("List = ")
    var x = head.next
    while (x != head) {
        print("${x?.value}  ")
        x = x?.next
    }
    println()
}

fun <E> showListReverse(head: Node<E>) {
    print("List reverse = ")
    var x = head.previous
    while (x != head) {
        print("${x?.value}  ")
        x = x?.previous
    }
    println()
}