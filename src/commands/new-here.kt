package commands

import BINARY_DIR_NAME
import CLASSES_DIR_NAME
import CLI_VERSION_DEPS_ATTRIBUTE_NAME
import DEPS_FILE_NAME
import LANG_VERSION_DEPS_ATTRIBUTE_NAME
import MAIN_SOURCE_FILE_NAME
import SOURCE_DIR_NAME
import com.google.gson.GsonBuilder
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@CommandLine.Command(name = "new-here", description = ["the current directory will become an Icaro project"])
class NewHere : Runnable {
    override fun run() {
        try {
            createDependenciesFile("$DEPS_FILE_NAME.json")
            createSourceDir(SOURCE_DIR_NAME, "$MAIN_SOURCE_FILE_NAME.ic")
            createBinariesDir(".$BINARY_DIR_NAME", CLASSES_DIR_NAME)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun createDependenciesFile(path: String) {
        print("insert cli version: ")
        val cliVersion = readLine()!!

        print("insert lang version: ")
        val langVersion = readLine()!!

        val depsInJson = GsonBuilder().setPrettyPrinting().create().toJson(
            mapOf(
                CLI_VERSION_DEPS_ATTRIBUTE_NAME to cliVersion,
                LANG_VERSION_DEPS_ATTRIBUTE_NAME to langVersion
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