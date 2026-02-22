package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

public class UnmarkCommand extends Command {
    private final String indexArg;

    public UnmarkCommand(String indexArg) {
        this.indexArg = indexArg;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.setDone(indexArg, false);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
