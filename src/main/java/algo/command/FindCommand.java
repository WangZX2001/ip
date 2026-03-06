package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;
import algo.task.Task;

import java.util.List;

/**
 * Represents a command that searches for tasks containing a given keyword.
 * The command retrieves and displays tasks whose descriptions match the keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a FindCommand with the specified search keyword.
     *
     * @param keyword The keyword used to search for matching tasks.
     * @throws AlgoException If the keyword is null or empty.
     */
    public FindCommand(String keyword) throws AlgoException {
        String k = keyword == null ? "" : keyword.trim();
        if (k.isEmpty()) {
            throw new AlgoException("Usage: find <keyword>");
        }
        this.keyword = k;
    }

    /**
     * Executes the find command by searching for tasks containing the specified
     * keyword and displaying the matching tasks to the user.
     *
     * @param tasks The task list containing all tasks.
     * @param ui The user interface used to display messages and task lists.
     * @param storage The storage handler (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matches = tasks.findByKeyword(keyword);

        if (matches.isEmpty()) {
            ui.showMessage("No matching tasks found.");
            return;
        }

        ui.showMessage("Here are the matching tasks in your list:");
        ui.showTaskList(matches);
    }
}