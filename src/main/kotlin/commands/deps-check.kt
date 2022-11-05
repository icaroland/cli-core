package commands

import ICARO_HOME
import com.google.gson.Gson
import org.apache.commons.io.FileUtils
import picocli.CommandLine
import java.io.File
import java.net.URL

@CommandLine.Command(name = "deps-check", description = ["check for every dependency to be installed and correct"])
class DepsCheck : Runnable {
    override fun run() {
        try {
            if (!File("deps.json").isFile) {
                println("we are not inside an icaro project!")
                return
            }

            val toDownload = mutableListOf<List<String>>()

            val (cliVersion, langVersion) = getDependencies("deps.json")

            if (!File("$ICARO_HOME/cli/core/$cliVersion").isFile)
                toDownload.add(listOf("cli-core", cliVersion, "cli/core"))

            if (!File("$ICARO_HOME/lang/$langVersion").isFile)
                toDownload.add(listOf("lang-implementation", langVersion, "lang"))

            if (toDownload.size > 0) {
                println("some dependencies aren't installed! Do you want to install them? (y/n)")

                if (readLine()!! == "y")
                    toDownload.forEach { download(it) }
            }
        } catch (e: Throwable) {
            return
        }
    }

    private fun getDependencies(file: String): List<String> {
        val dependencies = Gson().fromJson(File("deps.json").readText(), Map::class.java)

        return listOf(dependencies["cliVersion"], dependencies["langVersion"]).map { toString() }
    }

    private fun download(downloadInfo: List<String>) {
        val (githubRepoName, tag, targetFolder) = downloadInfo

        val url = "https://github.com/icaroland/$githubRepoName/releases/download/$tag/$tag.jar"

        FileUtils.copyURLToFile(URL(url), File("$ICARO_HOME/$targetFolder/$tag.jar"))
    }
}