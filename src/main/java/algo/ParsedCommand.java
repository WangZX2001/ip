package algo;

public class ParsedCommand {
    public final String command;
    public final String args;
    public final String fullInput;

    public ParsedCommand(String command, String args, String fullInput) {
        this.command = command;
        this.args = args;
        this.fullInput = fullInput;
    }
}