package commands

import com.google.gson.GsonBuilder
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@CommandLine.Command(name = "new-here", description = ["the current directory will become an Icaro project"])
class NewHere : Runnable {
    override fun run() {
        try {
            createDependenciesFile("deps.json")
            createSourceDir("src", "main.ic")
            createBinariesDir(".bin", "classes")
        } catch (e: Throwable) {
            return
        }
    }

    private fun createDependenciesFile(path: String) {
        println("insert cli version: ")
        val cliVersion = readLine()!!

        println("\ninsert lang version: ")
        val langVersion = readLine()!!

        val depsInJson = GsonBuilder().setPrettyPrinting().create().toJson(
            mapOf(
                "cliVersion" to cliVersion,
                "langVersion" to langVersion
            )
        )

        File(path).writeText(depsInJson)
    }

    private fun createSourceDir(pathToSrcDir: String, pathToMainFile: String) {
        Files.createDirectory(Path.of(pathToSrcDir))
        File("$pathToSrcDir/$pathToMainFile").writeText("")
    }

    private fun createBinariesDir(pathToBinDir: String, pathToClassesDir: String) {
        Files.createDirectory(Path.of(pathToBinDir))
        Files.createDirectory(Path.of("$pathToBinDir/$pathToClassesDir"))
    }
}