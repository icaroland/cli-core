package commands
import picocli.CommandLine

data class DepsFileData(val a: Int, val b: String)

@CommandLine.Command(name = "new-here", description = ["the current directory become an Icaro project"])
class NewHere : Runnable {
    override fun run() {
        
    }
}