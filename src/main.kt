import commands.NewHere
import commands.Run
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.HelpCommand
import kotlin.system.exitProcess

@Command(
    name = "icaro",
    subcommands = [NewHere::class, Run::class, HelpCommand::class],
    description = ["The Icaro programming language CLI"]
)
class Icaro

fun main(args: Array<String>) {
    try {
        exitProcess(CommandLine(Icaro()).execute(*args))
    } catch (e: Throwable) {
        println(e.message)
    }
}