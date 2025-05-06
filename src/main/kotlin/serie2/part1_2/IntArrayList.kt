package serie2.part1_2
/*
  --- PASSARAM NOS TESTES TODOS
 */

interface Iterable<Int>{
    fun append(x: Int): Boolean
    fun get(n: Int): Int?
    fun addToAll(x: Int)
    fun remove(): Boolean
}

class IntArrayList(private val k: Int): Iterable<Int>{
    private var list = listOf<Int>()
    private var addToAll = 0

    override fun append(x:Int): Boolean{
        return if (list.size == k) false
        else {
            list += x
            return true
        }
    }

    override fun get(n: Int): Int?{
        return if(n !in 0 until k) null
        else list[n] + addToAll
    }

    override fun addToAll(x: Int){
        list = list.map { it + x }
    }

    override fun remove(): Boolean{
        return if (list.size == 0) false
        else {
            list -= list.first()
            true
        }
    }
}