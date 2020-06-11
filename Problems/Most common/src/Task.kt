import java.util.*

fun main() {
    val words = mutableMapOf<String, Int>()


    var word = ""
    val scanner = Scanner(System.`in`)

    while (true) {
        word = scanner.nextLine()
        if (word == "stop") break

        val count = words[word]
        if (count == null) words[word] = 1
        else words.put(word, count + 1)
    }
    if (words.size == 0) println("null")
    else {
        var frequentWord = ""
        var max = Int.MIN_VALUE
        for (entry in words.entries) {
            if (entry.value > max) {
                frequentWord = entry.key
                max = entry.value
            }
        }
        println(frequentWord)
    }
}