package commands

import AutoRemover
import BINARY_DIR_NAME
import CLASSES_DIR_NAME
import CLI_VERSION_DEPS_ATTRIBUTE_NAME
import DEPS_FILE
import LANG_VERSION_DEPS_ATTRIBUTE_NAME
import MAIN_SOURCE_FILE
import SOURCE_DIR_NAME
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NewHereTest : AutoRemover() {
    @Test
    fun shouldCreateDependenciesFile() {
        val cliVersion = "1.2"
        val langVersion = "3.1-rc.3"

        createDependenciesFile(cliVersion, langVersion)

        val dependencies = Gson().fromJson(File(DEPS_FILE).readText(), Map::class.java)

        assertEquals(cliVersion, dependencies[CLI_VERSION_DEPS_ATTRIBUTE_NAME])
        assertEquals(langVersion, dependencies[LANG_VERSION_DEPS_ATTRIBUTE_NAME])
    }

    @Test
    fun shouldCreateSourceAndBinaryDirectories() {
        createSrcAndBinDirs()

        assertTrue(File("$SOURCE_DIR_NAME/$MAIN_SOURCE_FILE").isFile)
        assertTrue(File("$BINARY_DIR_NAME/$CLASSES_DIR_NAME").isDirectory)
    }
}