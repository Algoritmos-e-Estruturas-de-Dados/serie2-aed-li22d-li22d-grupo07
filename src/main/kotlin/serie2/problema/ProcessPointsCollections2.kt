package serie2.problema

import serie2.part4.AEDHashMap

object ProcessPointsCollection2 {
    private data class Point(val x: Float, val y: Float)
    // Definido o tipo Int no parâmetro "value" do mapa que representa o subficheiro de onde foi extraída a chave (Ponto)
    // Quando value = 1, o ponto foi extraído do primeiro ficheiro.
    // Quando value = 2, o ponto foi extraído do segundo ficheiro.
    // O tipo "Point" como chave permite ter chaves iguais para pontos iguais e chaves diferentes para pontos diferentes.
    private var hashMap = AEDHashMap<Point, Array<Boolean>>() // Cria o hashMap

    fun load(file1: String, file2: String){
        val reader1 = createReader(file1) // Abre os leitores dos ficheiros de entrada
        val reader2 = createReader(file2)

        var x1 = reader1.readLine() // Lê uma linha do ficheiro (file1)
        var x2 = reader2.readLine() // Lê uma linha do ficheiro (file2)

        while (x1.split(' ')[0] != "v" && x2.split(' ')[0] != "v"){ // Lê continuamente as linhas dos ficheiros até chegar aos pontos
            x1 = reader1.readLine()
            x2 = reader2.readLine()
            continue
        }
        // Caso a leitura das linhas do ficheiro resulte num "null", significa que o ficheiro não tem mais linhas para serem lidas.
        while (x1 != null || x2 != null) {
            if (x1 != null) {
                val x = x1.split(' ')[2].toFloat() // Extrai o valor de x
                val y = x1.split(' ')[3].toFloat() // Extrai o valor de y
                val oldValue = hashMap[Point(x,y)] // Guarda o valor anteriormente associado ao ponto (permite perceber a sua origem caso já exista na tabela)

                if (oldValue != null && !oldValue[0]) hashMap.put(Point(x,y), arrayOf(true, true))
                else if (oldValue == null) hashMap.put(Point(x,y), arrayOf(true, false))

                x1 = reader1.readLine() // Lê a próxima linha
            }
            if (x2 != null) {
                val x = x2.split(' ')[2].toFloat() // Extrai o valor da chave
                val y = x2.split(' ')[3].toFloat() // Extrai o valor de "value"
                val oldValue = hashMap.get(Point(x,y)) // Coloca o par no mapa

                if (oldValue != null && !oldValue[1]) hashMap.put(Point(x,y), arrayOf(true, true)) // Ponto está presente na tabela e no outro ficheiro
                else if (oldValue == null) hashMap.put(Point(x,y), arrayOf(false, true)) // Ponto ainda não está na tabela

                x2 = reader2.readLine() // Lê a próxima linha
            }
        }
        reader1.close() // Fecha os leitores dos ficheiros
        reader2.close()
    }


    fun union(file: String){
        val exitFile = createWriter(file)

        for (point in hashMap) {
            exitFile.println("${point.key.x} , ${point.key.y}")
        }
        exitFile.close()
    }

    fun intersection(file: String){
        val exitFile = createWriter(file)

        for (point in hashMap) {
            if (point.value[0] && point.value[1])
                exitFile.println("${point.key.x} , ${point.key.y}")
        }
        exitFile.close()
    }

    fun difference(file: String){
        val exitFile = createWriter(file)

        for (point in hashMap) {
            if (point.value[0] && !point.value[1])
                exitFile.println("${point.key.x} , ${point.key.y}")
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

        when (command.uppercase()) {
            "LOAD" -> {
                if (input.size != 3) continue
                else ProcessPointsCollection2.load(input[1], input[2])
                println("Ficheiros carregados!")
            }
            "UNION" -> {
                if (input.size != 2) continue
                ProcessPointsCollection2.union(input[1])
                println("Operação Concluída!")
            }
            "INTERSECTION" -> {
                if (input.size != 2) continue
                ProcessPointsCollection2.intersection(input[1])
                println("Operação Concluída!")
            }
            "DIFFERENCE" -> {
                if (input.size != 2) continue
                ProcessPointsCollection2.difference(input[1])
                println("Operação Concluída!")
            }
        }
    }
    println("Aplicação Terminada!")
}