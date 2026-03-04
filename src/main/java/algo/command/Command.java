package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

/**
 * Represents a generic command in the application.
 * Each command performs a specific action when executed.
 * Subclasses should implement the {@code execute} method
 * to define the behavior of the command.
 */
public abstract class Command {
    /**
     * Executes the command using the provided task list, user interface,
     * and storage.
     *
     * @param tasks The task list that contains all tasks.
     * @param ui The user interface used to communicate with the user.
     * @param storage The storage handler responsible for saving tasks.
     * @throws AlgoException If an error occurs during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException;

    /**
     * Indicates whether this command will terminate the program.
     *
     * @return {@code true} if the command signals program termination,
     *         {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
