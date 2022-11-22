const val DEPS_FILE = "deps.json"
const val CLI_VERSION_DEPS_ATTRIBUTE_NAME = "cliVersion"
const val LANG_VERSION_DEPS_ATTRIBUTE_NAME = "langVersion"
const val SOURCE_DIR_NAME = "src"
const val MAIN_SOURCE_FILE = "main.ic"
const val BINARY_DIR_NAME = ".bin"
const val CLASSES_DIR_NAME = "classes"
val ICARO_HOME: String = if (System.getenv("ICARO_HOME") != null) System.getenv("ICARO_HOME") else "homeDir/icaro"
val LANG_DIR_PATH = "$ICARO_HOME/lang"

class DepsFileNotFound(msg: String) : Throwable(msg)
class LangVersionNotFound(msg: String) : Throwable(msg)

