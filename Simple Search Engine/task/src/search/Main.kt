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
                else -> sayIncorrectOption()
            }
        }
    }

    private fun sayIncorrectOption() {
        println("Incorrect option! Try again.")
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

        println("Select a matching strategy: ALL, ANY, NONE")
        val strategy = scanner.nextLine()!!.toUpperCase().trim()

        println("\nEnter a name or email to search all suitable people.")
        val searchLine = scanner.nextLine()!!.toUpperCase().trim()
        val searchWords = searchLine.split(" ").toMutableList()
        for (i in searchWords.indices) {
            searchWords[i] = searchWords[i].trim()
        }

        val foundSet =
                when (strategy) {
                    "ALL" -> searchAll(searchWords)
                    "ANY" -> searchAny(searchWords)
                    "NONE" -> searchNone(searchWords)
                    else -> {
                        sayIncorrectOption(); return
                    }
                }

        if (foundSet.isEmpty()) {
            println("No matching people found.")
            return
        }

        println("\nFound people:")
        foundSet.forEach {
            println(lines[it])
        }
    }

    private fun searchAny(searchWords: List<String>): Set<Int> {
        val ret = mutableSetOf<Int>()
        for (searchWord in searchWords) {
            val found = index[searchWord]
            if (found != null) {
                ret.addAll(found)
            }
        }
        return ret
    }

    private fun searchNone(searchWords: List<String>): Set<Int> {
        val ret = mutableSetOf<Int>()
        val found = searchAny(searchWords)
        for (i in lines.indices) {
            if (!found.contains(i)) ret.add(i)
        }
        return ret
    }

    private fun searchAll(searchWords: List<String>): Set<Int> {
        val ret = mutableSetOf<Int>()
        var foundAll = true
        for (searchWord in searchWords) {
            val found = index[searchWord]
            if (found != null) {
                ret.addAll(found)
            } else {
                foundAll = false
            }
        }
        return if (foundAll) ret else mutableSetOf<Int>()
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
