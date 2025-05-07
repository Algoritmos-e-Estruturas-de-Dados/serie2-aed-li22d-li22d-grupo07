package serie2.part1_2
/*
  --- PASSARAM NOS TESTES TODOS
 */

class IntArrayList(val k: Int): Iterable<Int>{
    private var intArrayList = IntArray(k)
    private var pointer = -1

     fun append(x:Int): Boolean{
        return if (pointer == k - 1) false
         else {
             pointer++
             intArrayList[pointer] = x
             true
         }
    }

     fun get(n: Int): Int?{
        return if(n !in 0 until k) null
        else intArrayList[n]
    }

     fun addToAll(x: Int){
        for (i in 0..pointer){
            intArrayList[i] += x
        }
    }

     fun remove(): Boolean{
        return if (pointer == -1) false
         else {
            for (i in 0 until pointer){
                intArrayList[i] = intArrayList[i+1]
            }
            pointer--
            true
         }
    }
// OQ Q É PRA FZR NESTA ???????
    override fun iterator(): Iterator<Int> {
        TODO("Not yet implemented")
    }
}