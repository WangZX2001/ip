package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

/**
 * Represents a command that marks a task as completed in the task list.
 * The command identifies the task using the provided index.
 */
public class MarkCommand extends Command {
    private final String indexArg;

    /**
     * Creates a MarkCommand with the specified task index argument.
     *
     * @param indexArg The index of the task to be marked as completed.
     */
    public MarkCommand(String indexArg) {
        this.indexArg = indexArg;
    }

    /**
     * Executes the mark command by marking the specified task as done,
     * saving the updated task list to storage, and displaying a confirmation message.
     *
     * @param tasks The task list containing all tasks.
     * @param ui The user interface used to interact with the user.
     * @param storage The storage handler responsible for saving tasks.
     * @throws AlgoException If the provided task index is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.setDone(indexArg, true);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
