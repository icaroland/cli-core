package commands

import CLASSES_DIR_NAME
import CLI_VERSION_DEPS_ATTRIBUTE_NAME
import DEPS_FILE
import LANG_VERSION_DEPS_ATTRIBUTE_NAME
import MAIN_SOURCE_FILE
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NewHereTest {
    @Test
    fun shouldCreateDependenciesFile() {
        val cliVersion = "1.2"
        val langVersion = "3.1-rc.3"

        createDependenciesFile(cliVersion, langVersion)

        val dependencies = Gson().fromJson(File(DEPS_FILE).readText(), Map::class.java)

        assertEquals(cliVersion, dependencies[CLI_VERSION_DEPS_ATTRIBUTE_NAME])
        assertEquals(langVersion, dependencies[LANG_VERSION_DEPS_ATTRIBUTE_NAME])

        File(DEPS_FILE).delete()
    }

    @Test
    fun shouldCreateSourceAndBinaryDirectories() {
        val sourceDir = "src43234"
        val binDir = ".bin43334"
        
        createSrcAndBinDirs(sourceDir, binDir)

        assertTrue(File("$sourceDir/$MAIN_SOURCE_FILE").isFile)
        assertTrue(File("$binDir/$CLASSES_DIR_NAME").isDirectory)

        File(sourceDir).deleteRecursively()
        File(binDir).deleteRecursively()
    }
}