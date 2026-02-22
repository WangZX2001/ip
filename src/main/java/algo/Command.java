package algo;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException;
    
    public boolean isExit() {
        return false;
    }
}
