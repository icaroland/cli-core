const val DEPS_FILE = "deps.json"
const val CLI_VERSION_DEPS_ATTRIBUTE_NAME = "cliVersion"
const val LANG_VERSION_DEPS_ATTRIBUTE_NAME = "langVersion"
const val SOURCE_DIR_NAME = "src"
const val MAIN_SOURCE_FILE = "main.ic"
const val BINARY_DIR_NAME = ".bin"
const val CLASSES_DIR_NAME = "classes"
val LANG_DIR_PATH = "${System.getenv("ICARO_HOME")}/lang"

class DepsFileNotFound(msg: String) : Throwable(msg)
class LangVersionNotFound(msg: String) : Throwable(msg)

