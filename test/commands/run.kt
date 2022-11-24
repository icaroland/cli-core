package commands

import AutoRemover
import DEPS_FILE
import DepsFileNotFound
import LANG_DIR_PATH
import LANG_VERSION_DEPS_ATTRIBUTE_NAME
import LangVersionNotFound
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class LangVersionToUseTest : AutoRemover() {
    @Test
    fun shouldReturnTheLangVersionWhenTheVersionIsInstalled() {
        val langVersion = "0.1"

        val depsInJson = GsonBuilder().setPrettyPrinting().create().toJson(
            mapOf(
                LANG_VERSION_DEPS_ATTRIBUTE_NAME to langVersion,
            )
        )

        File(DEPS_FILE).writeText(depsInJson)
        File(LANG_DIR_PATH).mkdirs()
        File("$LANG_DIR_PATH/$langVersion.jar").writeText("")

        assert(langVersionToUse() == langVersion)
    }

    @Test
    fun shouldThrowWhenWeAreNotInsideIcaroProject() {
        assertThrows<DepsFileNotFound> { langVersionToUse() }
    }
    
    @Test
    fun shouldThrowWhenNoValidJsonInDepsFile() {
        File(DEPS_FILE).writeText("")

        assertThrows<JsonSyntaxException> { langVersionToUse() }
    }

    @Test
    fun shouldThrowWhenVersionOnDepsIsNotInstalled() {
        val langVersion = "0.1"

        val depsInJson = GsonBuilder().setPrettyPrinting().create().toJson(
            mapOf(
                LANG_VERSION_DEPS_ATTRIBUTE_NAME to langVersion,
            )
        )

        File(DEPS_FILE).writeText(depsInJson)

        assertThrows<LangVersionNotFound> { langVersionToUse() }
    }
}