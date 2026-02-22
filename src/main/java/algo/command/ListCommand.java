package algo.command;

import algo.Storage;
import algo.TaskList;
import algo.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println(tasks.listTasks());
    }
}
