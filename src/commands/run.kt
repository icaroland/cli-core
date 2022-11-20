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
        val path = "$LANG_DIR_PATH/${langVersionToUse()}.jar"

        ProcessBuilder(listOf("java", "-jar", path)).inheritIO().start().waitFor()

        executeClassFile()
    }
}

fun langVersionToUse(): String {
    if (!File(DEPS_FILE).isFile) throw DepsFileNotFound("In order to run this command you should be inside an icaro project")

    try {
        val dependencies = Gson().fromJson(File(DEPS_FILE).readText(), Map::class.java)

        val langVersion = dependencies[LANG_VERSION_DEPS_ATTRIBUTE_NAME].toString()

        if (!File("$LANG_DIR_PATH/${langVersionToUse()}.jar").exists()) throw LangVersionNotFound("I can't find installed the lang version you specified")

        return langVersion
    } catch (e: Throwable) {
        throw JsonSyntaxException("deps.json file doesn't contain valid JSON!")
    }
}

fun executeClassFile() {
    val className = "Main"

    val bytecode = File("$BINARY_DIR_NAME/$CLASSES_DIR_NAME/$className.class").readBytes()

    IcaroClassLoader().defineClass(className, bytecode).getMethod("main", Array<String>::class.java)
        .invoke(null, arrayOf<String>())
}

class IcaroClassLoader : ClassLoader() {
    fun defineClass(name: String, bytecode: ByteArray): Class<*> = defineClass(name, bytecode, 0, bytecode.size)
}