package commands

import BINARY_DIR_NAME
import CLASSES_DIR_NAME
import DEPS_FILE
import DepsFileNotFound
import LANG_DIR_PATH
import LANG_VERSION_DEPS_ATTRIBUTE_NAME
import LangVersionNotFound
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import picocli.CommandLine
import java.io.File
import java.util.*

@CommandLine.Command(name = "run", description = ["build and execute the code inside src"])
class Run : Runnable {
    override fun run() {
        ProcessBuilder(listOf("java", "-jar", "$LANG_DIR_PATH/${langVersionToUse()}.jar")).inheritIO().start().waitFor()

        executeClassFile()
    }
}

fun langVersionToUse(): String {
    if (!File(DEPS_FILE).isFile) throw DepsFileNotFound("In order to run this command you should be inside an icaro project")

    var langVersion = ""

    try {
        val dependencies = Gson().fromJson(File(DEPS_FILE).readText(), Map::class.java)

        langVersion = dependencies[LANG_VERSION_DEPS_ATTRIBUTE_NAME].toString()
    } catch (e: Throwable) {
        throw JsonSyntaxException("deps.json file doesn't contain valid JSON!")
    }

    if (!File("$LANG_DIR_PATH/$langVersion.jar").exists())
        throw LangVersionNotFound("I can't find installed the lang version you specified")

    return langVersion
}

fun executeClassFile() {
    val bytecode = File("$BINARY_DIR_NAME/$CLASSES_DIR_NAME/Main.class").readBytes()

    IcaroClassLoader().defineClass("Main", bytecode).getMethod("main", Array<String>::class.java)
        .invoke(null, arrayOf<String>())
}

class IcaroClassLoader : ClassLoader() {
    fun defineClass(name: String, bytecode: ByteArray): Class<*> = defineClass(name, bytecode, 0, bytecode.size)
}