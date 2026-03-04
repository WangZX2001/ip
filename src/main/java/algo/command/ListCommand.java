package algo.command;

import algo.Storage;
import algo.TaskList;
import algo.Ui;

/**
 * Represents a command that lists all tasks currently stored in the task list.
 * The command retrieves the list of tasks and displays them to the user.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command by printing all tasks in the task list.
     *
     * @param tasks The task list containing all tasks.
     * @param ui The user interface used for user interaction.
     * @param storage The storage handler (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println(tasks.listTasks());
    }
}
