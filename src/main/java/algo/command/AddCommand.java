package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

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

    public static class ByeCommand extends Command {
        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
        }

        @Override
        public boolean isExit() {
            return true;
        }
    }
}
