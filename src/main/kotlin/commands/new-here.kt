package commands

import com.google.gson.GsonBuilder
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@CommandLine.Command(name = "new-here", description = ["the current directory will become an Icaro project"])
class NewHere : Runnable {

    private fun createDependenciesFile(path: String) {
        File(path).delete()
        
        val depsInJson = GsonBuilder().setPrettyPrinting().create().toJson(
            mapOf(
                "langVersion" to System.getenv("ICARO_LANG_VERSION"),
                "cliVersion" to System.getenv("ICARO_CLI_VERSION")
            )
        )

        File(path).writeText(depsInJson)
    }

    private fun createSourceDir(pathToSrcDir: String, pathToMainFile: String) {
        File(pathToSrcDir).deleteRecursively()
        
        Files.createDirectory(Path.of(pathToSrcDir))
        File("$pathToSrcDir${File.separator}$pathToMainFile").writeText("")
    }

    private fun createBinariesDir(pathToBinDir: String, pathToClassesDir: String) {
        File(pathToBinDir).deleteRecursively()

        Files.createDirectory(Path.of(pathToBinDir))
        Files.createDirectory(Path.of("$pathToBinDir${File.separator}$pathToClassesDir"))
    }

    override fun run() {
        val currentDir = Paths.get("").toAbsolutePath().toString()

        createDependenciesFile("$currentDir${File.separator}dependencies.json")
        createSourceDir("$currentDir${File.separator}src", "main.ic")
        createBinariesDir("$currentDir${File.separator}.bin", "classes")
    }
}