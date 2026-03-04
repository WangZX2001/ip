package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

/**
 * Represents a command that marks a task as not completed in the task list.
 * The command identifies the task using the provided index.
 */
public class UnmarkCommand extends Command {
    private final String indexArg;

    /**
     * Creates an UnmarkCommand with the specified task index argument.
     *
     * @param indexArg The index of the task to be marked as not completed.
     */
    public UnmarkCommand(String indexArg) {
        this.indexArg = indexArg;
    }

    /**
     * Executes the unmark command by marking the specified task as not done,
     * saving the updated task list to storage, and displaying a confirmation message.
     *
     * @param tasks The task list containing all tasks.
     * @param ui The user interface used to interact with the user.
     * @param storage The storage handler responsible for saving tasks.
     * @throws AlgoException If the provided task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.setDone(indexArg, false);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
