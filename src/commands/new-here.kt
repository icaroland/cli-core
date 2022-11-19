package commands

import BINARY_DIR_NAME
import CLASSES_DIR_NAME
import CLI_VERSION_DEPS_ATTRIBUTE_NAME
import DEPS_FILE
import LANG_VERSION_DEPS_ATTRIBUTE_NAME
import MAIN_SOURCE_FILE
import SOURCE_DIR_NAME
import com.google.gson.GsonBuilder
import picocli.CommandLine
import java.io.File

@CommandLine.Command(name = "new-here", description = ["the current directory will become an Icaro project"])
class NewHere : Runnable {
    override fun run() {
        try {
            readLine()!!

            print("insert cli version: ")
            val cliVersion = readLine()!!

            print("insert lang version: ")
            val langVersion = readLine()!!

            createDependenciesFile(cliVersion, langVersion)
            createSrcAndBinDirs()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

fun createSrcAndBinDirs() {
    File(SOURCE_DIR_NAME).mkdir()
    File("$SOURCE_DIR_NAME/$MAIN_SOURCE_FILE").writeText("")

    File("$BINARY_DIR_NAME/$CLASSES_DIR_NAME").mkdirs()
}

fun createDependenciesFile(cliVersion: String, langVersion: String) {
    val depsInJson = GsonBuilder().setPrettyPrinting().create().toJson(
        mapOf(
            CLI_VERSION_DEPS_ATTRIBUTE_NAME to cliVersion,
            LANG_VERSION_DEPS_ATTRIBUTE_NAME to langVersion
        )
    )

    File(DEPS_FILE).writeText(depsInJson)
}