package serie2.part1_2
/*
 --- TESTES ERRADOS?????
 */
fun minimum(maxHeap: Array<Int>, heapSize: Int): Int {
    var minimum = maxHeap[heapSize/2]

    for(i in maxHeap.size/2 until maxHeap.size){
        if (maxHeap[i] < minimum) minimum = maxHeap[i]
    }

    return minimum
}