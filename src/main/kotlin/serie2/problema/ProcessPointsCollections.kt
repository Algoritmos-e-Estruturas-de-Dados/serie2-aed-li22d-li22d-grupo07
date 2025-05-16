package serie2.problema

object ProcessPointsCollection {
    private data class Point(val x: Float, val y: Float)
    // Definido o tipo Int no parâmetro "value" do mapa que representa o subficheiro de onde foi extraída a chave (Ponto)
    // Quando value = 1, o ponto foi extraído do primeiro ficheiro.
    // Quando value = 2, o ponto foi extraído do segundo ficheiro.
    // O tipo "Point" como chave permite ter chaves iguais para pontos iguais e chaves diferentes para pontos diferentes.
    private var hashMap = HashMap<Point, Int>() // Cria o hashMap
    private var duplicatedPoints = listOf<Point>()

    fun load(file1: String, file2: String){
        val reader1 = createReader(file1) // Abre os leitores dos ficheiros de entrada
        val reader2 = createReader(file2)

        var x1 = reader1.readLine() // Lê uma linha do ficheiro (file1)
        var x2 = reader2.readLine() // Lê uma linha do ficheiro (file2)

        while (x1.split(' ')[0] != "v" && x2.split(' ')[0] != "v"){
            x1 = reader1.readLine()
            x2 = reader2.readLine()
            continue
        }
        // Caso a leitura das linhas do ficheiro resulte num "null", significa que o ficheiro não tem mais linhas para serem lidas.
        while (x1 != null || x2 != null) {
            if (x1 != null) {
                val x = x1.split(' ')[2].toFloat() // Extrai o valor de x
                val y = x1.split(' ')[3].toFloat() // Extrai o valor de y
                val oldValue = hashMap.put(Point(x,y), 1) // Coloca o par no mapa e retorna o último Ponto associado à chave x

                if (oldValue == 2 && Point(x, y) !in duplicatedPoints) duplicatedPoints +=  Point(x, y)

                x1 = reader1.readLine() // Lê a próxima linha
            }
            if (x2 != null) {
                val x = x2.split(' ')[2].toFloat() // Extrai o valor da chave
                val y = x2.split(' ')[3].toFloat() // Extrai o valor de "value"
                val oldValue = hashMap.put(Point(x,y), 2) // Coloca o par no mapa

                if (oldValue == 1 && Point(x, y) !in duplicatedPoints) {
                    duplicatedPoints += Point(x, y)
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
            exitFile.println("${element.key.x} , ${element.key.y}")
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
            if (element.key !in duplicatedPoints)
                exitFile.println("${element.key.x} , ${element.key.y}")
        }
        exitFile.close()
    }
}

fun main() {
    var command = ""
    while (command != "exit") {
        println("""Write a command
>
        """.trimMargin())

        val input = readln().split(' ')

        command = input[0] // Separa-se a palavra chave do comando do restante para ser mais percetível

        when (command) {
            "load" -> {
                if (input.size != 3) continue
                else ProcessPointsCollection.load(input[1], input[2])
            }
            "union" -> {
                if (input.size != 2) continue
                ProcessPointsCollection.union(input[1])
            }
            "intersection" -> {
                if (input.size != 2) continue
                ProcessPointsCollection.intersection(input[1])
            }
            "difference" -> {
                if (input.size != 2) continue
                ProcessPointsCollection.difference(input[1])
            }
        }
    }
    println("Aplicação Terminada")

//    ProcessPointsCollection.load("Test1.co", "Test2.co")
//    ProcessPointsCollection.union("union.co")
//    ProcessPointsCollection.difference("difference.co")
//    ProcessPointsCollection.intersection("intersection.co")
}