import commands.NewHere
import picocli.CommandLine
import picocli.CommandLine.*
import kotlin.system.exitProcess

@Command(
    name = "icaro",
    subcommands = [NewHere::class, HelpCommand::class],
    description = ["The Icaro programming language CLI"]
)
class Icaro

fun main(args: Array<String>) {
    exitProcess(CommandLine(Icaro()).execute(*args))
}