package commands

import picocli.CommandLine

@CommandLine.Command(name = "lang-use", description = ["From now the code will be compiled with the given language version"])
class LangUse : Runnable {
    @CommandLine.Parameters(paramLabel = "<version>", description = ["language version"])
    lateinit var langVersion: String

    override fun run() {
        
    }
}