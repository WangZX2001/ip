package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

/**
 * Represents a command that deletes a task from the task list.
 * The command uses the provided task index to remove the corresponding task.
 */
public class DeleteCommand extends Command {
    private final String indexArg;

    /**
     * Creates a DeleteCommand with the specified task index argument.
     *
     * @param indexArg The index of the task to be deleted, provided as user input.
     */
    public DeleteCommand(String indexArg) {
        this.indexArg = indexArg;
    }

    /**
     * Executes the delete command by removing the specified task from the task list,
     * saving the updated list to storage, and printing a confirmation message.
     *
     * @param tasks The task list that contains all tasks.
     * @param ui The user interface used to interact with the user.
     * @param storage The storage handler responsible for saving tasks.
     * @throws AlgoException If the provided index is invalid or deletion fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.deleteTask(indexArg);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
}
