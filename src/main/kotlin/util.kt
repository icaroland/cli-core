import org.apache.commons.io.FileUtils
import java.io.File
import java.net.URL

val ICARO_HOME: String = System.getenv("ICARO_HOME")

fun download(githubRepoName: String, tag: String, targetFolder: String) {
    val url = "https://github.com/icaroland/$githubRepoName/releases/download/$tag/$tag.jar"

    FileUtils.copyURLToFile(URL(url), File("$ICARO_HOME/$targetFolder/$tag.jar"))
}