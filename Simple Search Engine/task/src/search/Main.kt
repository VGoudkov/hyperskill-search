package search

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val engine = SearchEngine(scanner);

    engine.init()
    engine.serve()
}

class SearchEngine(val scanner: Scanner) {

    val data: MutableList<String> = mutableListOf()

    fun init() {
        println("Enter the number of people:")
        val lines = scanner.nextLine().toInt()

        println("Enter all people:")
        for (i in 1..lines) data.add(scanner.nextLine()!!)
    }


    fun serve() {
        while (true) {
            when (askMode()) {
                1 -> doSearch()
                2 -> printAll()
                0 -> return
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
        for (line in data) {
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
        data.forEach { println(it) }
    }
}
