package search

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val engine = SearchEngine(args[1]);
    engine.serve()
}

class SearchEngine(fileName: String) {

    private var lines: List<String> = emptyList()
    private val scanner = Scanner(System.`in`)

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

        return scanner.nextLine().toInt()
    }


    fun doSearch() {
        println("\nEnter data to search people:")
        val term = scanner.nextLine()!!.toUpperCase()
        var isFirstResult = true
        var isFoundInAnyLine = false
        for (line in lines) {
            var isFoundInLine = false
            for (word in line.split(" ")) {
                if (word.toUpperCase().contains(term)) {
                    isFoundInLine = true
                    isFoundInAnyLine = true
                    break
                }
            }
            if (isFoundInLine && isFirstResult) {
                println("\nFound people:")
                isFirstResult = false
            }
            if (isFoundInLine) println(line)
        }
        if (!isFoundInAnyLine) println("No matching people found.")
    }

    private fun printAll() {
        println("\n=== List of people ===")
        lines.forEach { println(it) }
    }

    init {
        lines = File(fileName).readLines()
    }
}
