package search

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val engine = SearchEngine();
    engine.readData(args[1])
    engine.serve()
}

class SearchEngine() {

    private var lines: List<String> = emptyList()
    private val scanner = Scanner(System.`in`)
    private val index = mutableMapOf<String, MutableList<Int>>()
    val UNKNOWN_OPTION = -1

    fun serve() {
        while (true) {
            when (askMode()) {
                1 -> doSearch()
                2 -> printAll()
                0 -> {
                    println("Bye")
                    return
                }
                else -> println("Incorrect option! Try again.")
            }
        }
    }

    fun askMode(): Int {
        println("\n=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")

        return try {
            scanner.nextLine().toInt()
        } catch (e: Exception) {
            return UNKNOWN_OPTION
        }
    }

    fun doSearch() {
        println("\nEnter a name or email to search all suitable people.")
        val term = scanner.nextLine()!!.toUpperCase().trim()

        val linesList = index[term]

        if (linesList == null) {
            println("No matching people found.")
            return
        }

        println("\nFound people:")
        linesList.forEach {
            println(lines[it])
        }
    }

    private fun printAll() {
        println("\n=== List of people ===")
        lines.forEach { println(it) }
    }

    fun readData(fileName: String) {

        lines = File(fileName).readLines()
        for (lineIndex in lines.indices) {
            for (word in lines[lineIndex].toUpperCase().split(" ")) {
                if (word.trim().isEmpty()) continue
                val wordLines = index[word]
                if (wordLines == null) {
                    index[word] = mutableListOf(lineIndex)
                } else {
                    if (!wordLines.contains(lineIndex)) wordLines.add(lineIndex)
                }

            }
        }
    }

}
