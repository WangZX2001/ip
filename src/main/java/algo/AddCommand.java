package algo;

public class AddCommand extends Command {
    private final String fullInput;

    public AddCommand(String fullInput) {
        this.fullInput = fullInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.addTask(fullInput);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
