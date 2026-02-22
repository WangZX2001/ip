package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

public class DeleteCommand extends Command {
    private final String indexArg;

    public DeleteCommand(String indexArg) {
        this.indexArg = indexArg;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.deleteTask(indexArg);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
