package algo;

public class MarkCommand extends Command {
    private final String indexArg;

    public MarkCommand(String indexArg) {
        this.indexArg = indexArg;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.setDone(indexArg, true);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
