package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;

/**
 * Represents a command that adds a new task to the task list.
 * The command processes the full user input and delegates the task
 * creation to the {@code TaskList}.
 */
public class AddCommand extends Command {
    private final String fullInput;

    /**
     * Creates an AddCommand with the specified user input.
     *
     * @param fullInput Full input string provided by the user that contains
     *                  the task information to be added.
     */
    public AddCommand(String fullInput) {
        this.fullInput = fullInput;
    }

    /**
     * Executes the add command by adding a new task to the task list,
     * saving the updated list to storage, and displaying a confirmation
     * message to the user.
     *
     * @param tasks The task list that stores all tasks.
     * @param ui The user interface used for interacting with the user.
     * @param storage The storage responsible for saving tasks persistently.
     * @throws AlgoException If an error occurs while adding the task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AlgoException {
        String msg = tasks.addTask(fullInput);
        storage.save(tasks.getAll());
        System.out.println(msg);
    }
    /**
     * Represents a command that exits the application.
     */
    public static class ByeCommand extends Command {
        /**
         * Executes the exit command. No operation is performed since
         * the purpose of this command is only to signal program termination.
         *
         * @param tasks The task list.
         * @param ui The user interface.
         * @param storage The storage handler.
         */

        @Override
        public void execute(TaskList tasks, Ui ui, Storage storage) {
        }
        /**
         * Indicates whether this command should terminate the program.
         *
         * @return {@code true} because this command exits the program.
         */
        @Override
        public boolean isExit() {
            return true;
        }
    }
}
