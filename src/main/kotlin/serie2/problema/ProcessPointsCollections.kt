package serie2.problema

object ProcessPointsCollection {
    private data class Point(val x: Float, val y: Float)
    // Definido o tipo Int no parâmetro "value" do mapa que representa o subficheiro de onde foi extraída a chave (Ponto)
    // Quando value = 1, o ponto foi extraído do primeiro ficheiro.
    // Quando value = 2, o ponto foi extraído do segundo ficheiro.
    // O tipo "Point" como chave permite ter chaves iguais para pontos iguais e chaves diferentes para pontos diferentes.
    private var hashMap = HashMap<Point, Array<Boolean>>() // Cria o hashMap

    fun load(file1: String, file2: String){
        val reader1 = createReader(file1) // Abre os leitores dos ficheiros de entrada
        val reader2 = createReader(file2)

        var file1Lines = reader1.readLine() // Lê uma linha do ficheiro (file1)
        var file2Lines = reader2.readLine() // Lê uma linha do ficheiro (file2)
        // Lê continuamente as linhas dos ficheiros até chegar aos pontos (Salta a parte dos comentários dos ficheiros de teste)
        while (file1Lines.split(' ')[0] != "v" && file2Lines.split(' ')[0] != "v"){
            file1Lines = reader1.readLine()
            file2Lines = reader2.readLine()
            continue
        }
        // Caso a leitura das linhas do ficheiro resulte num "null", significa que o ficheiro não tem mais linhas para serem lidas.
        while (file1Lines != null || file2Lines != null) {
            if (file1Lines != null) {
                val point = getPoint(file1Lines) // Extrai o ponto da linha
                val oldValue = hashMap[point] // Guarda o valor anteriormente associado ao ponto (permite perceber a sua origem caso já exista na tabela)

                if (oldValue != null && !oldValue[0]) hashMap.put(point, arrayOf(true, true)) // Ponto está presente na tabela e no outro ficheiro
                else if (oldValue == null) hashMap.put(point, arrayOf(true, false)) // Ponto ainda não está na tabela

                file1Lines = reader1.readLine() // Lê a próxima linha
            }
            if (file2Lines != null) { // O procedimento é igual ao do primeiro ficheiro (em cima)
                val point = getPoint(file2Lines)
                val oldValue = hashMap[point]

                if (oldValue != null && !oldValue[1]) hashMap.put(point, arrayOf(true, true))
                else if (oldValue == null) hashMap.put(point, arrayOf(false, true))

                file2Lines = reader2.readLine() // Lê a próxima linha
            }
        }
        reader1.close() // Fecha os leitores dos ficheiros
        reader2.close()
    }
    // Extrai o ponto da linha recebida como parâmetro.
    private fun getPoint(line: String) = Point( line.split(' ')[2].toFloat(), line.split(' ')[3].toFloat())


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
    println("Aplicação Terminada")
}