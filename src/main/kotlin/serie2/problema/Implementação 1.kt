package serie2.problema

object ProcessPointsCollection {

    private data class Point(val id: Int, val x: Int, val y: Int)
    private var hashMap = hashMapOf<Int, Point>() // Cria o hashMap
    private var duplicatedPoints = arrayOf<Point>()

    fun load(file1: String, file2: String){
        val reader1 = createReader(file1) // Abre os leitores dos ficheiros de entrada
        val reader2 = createReader(file2)

        var x1 = reader1.readLine() // Lê uma linha do ficheiro (file1)
        var x2 = reader2.readLine() // Lê uma linha do ficheiro (file2)
        // Caso a leitura das linhas do ficheiro resulte num "null", significa que o ficheiro não tem mais linhas para serem lidas.
        while (x1 != null || x2 != null) {
            if (x1 != null && x2 != null && x1.split(' ')[0] != "v" && x2.split(' ')[0] != "v"){
                x1 = reader1.readLine()
                x2 = reader2.readLine()
                continue
            }
            if (x1 != null) {
                val x = x1.split(' ')[2].toInt() // Extrai o valor de x
                val y = x1.split(' ')[3].toInt() // Extrai o valor de y
                val oldValue = hashMap.put(x, Point(1, x, y)) // Coloca o par no mapa e retorna o último Ponto associado à chave x

                if (oldValue != null && oldValue.x == x && oldValue.id != 1) duplicatedPoints +=  Point(1, x, y)

                x1 = reader1.readLine() // Lê a próxima linha
            }
            if (x2 != null) {
                val x = x2.split(' ')[2].toInt() // Extrai o valor da chave
                val y = x2.split(' ')[3].toInt() // Extrai o valor de "value"
                val oldValue = hashMap.put(x, Point(2, x, y)) // Coloca o par no mapa

                if (oldValue != null && oldValue.x == x && oldValue.id != 2 && oldValue !in duplicatedPoints) {
                    duplicatedPoints += Point(2, x, y)
                }
                x2 = reader2.readLine() // Lê a próxima linha
            }
        }
        reader1.close() // Fecha os leitores dos ficheiros
        reader2.close()
    }


    fun union(file: String){
        val exitFile = createWriter(file)

        for (element in hashMap) {
            exitFile.println("${element.value.x} , ${element.value.y}")
        }
        exitFile.close()
    }

    fun intersection(file: String){
        val exitFile = createWriter(file)

        for (point in duplicatedPoints) {
            exitFile.println("${point.x} , ${point.y}")
        }
        exitFile.close()
    }

    fun difference(file: String){
        val exitFile = createWriter(file)

        for (element in hashMap) {
            if (element.value !in duplicatedPoints)
                exitFile.println("${element.value.x} , ${element.value.y}")
        }
        exitFile.close()
    }
}

fun main() {
//    var command = ""
//    while (command != "exit") {
//        println("""Write a command
//>
//        """.trimMargin())
//
//        val input = readln().split(' ')
//        command = input[0] // Separa-se a palavra chave do comando do restante para ser mais percetível
//
//        when (command) {
//            "load" -> ProcessPointsCollection().load(input[1], input[2])
//            "union" -> ProcessPointsCollection().union(input[1])
//            "intersection" -> ProcessPointsCollection().intersection(input[1])
//            "difference" -> ProcessPointsCollection().difference(input[1])
//            else -> println("Write a valid command")
//        }
//    }
//    println("Aplicação Terminada")

    ProcessPointsCollection.load("Test1.co", "Test2.co")
    ProcessPointsCollection.union("union.co")
    ProcessPointsCollection.intersection("intersection.co")
    ProcessPointsCollection.difference("difference.co")
}