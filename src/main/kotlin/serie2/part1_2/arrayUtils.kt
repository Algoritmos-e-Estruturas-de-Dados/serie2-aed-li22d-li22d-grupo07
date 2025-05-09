package serie2.part1_2

fun minimum(maxHeap: Array<Int>, heapSize: Int): Int {
   var minimum = maxHeap[heapSize/2]

    for(i in heapSize/2 until heapSize) {
        if (maxHeap[i] < minimum) minimum = maxHeap[i]
    }

    return minimum
}