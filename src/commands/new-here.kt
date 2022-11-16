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
            print("insert cli version: ")
            val cliVersion = readLine()!!

            print("insert lang version: ")
            val langVersion = readLine()!!

            createDependenciesFile(cliVersion, langVersion)
            createSrcAndBinDirs(SOURCE_DIR_NAME, BINARY_DIR_NAME)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

/*
doesn't make sense to pass constants but
1. constants are useful
2. in this case, we make this function parametrized, or we couldn't test it 
   because creating a src directory in the root path of this folder conflict with this 
   project src file (DX with docker is bad because everytime we need to wait for maven to build)
 */
fun createSrcAndBinDirs(sourceDirName: String, binaryDirName: String) {
    File(sourceDirName).mkdir()
    File("$sourceDirName/$MAIN_SOURCE_FILE").writeText("")

    File("$binaryDirName/$CLASSES_DIR_NAME").mkdirs()
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