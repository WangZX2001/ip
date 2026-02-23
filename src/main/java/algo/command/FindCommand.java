package algo.command;

import algo.AlgoException;
import algo.Storage;
import algo.TaskList;
import algo.Ui;
import algo.task.Task;

import java.util.List;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) throws AlgoException {
        String k = keyword == null ? "" : keyword.trim();
        if (k.isEmpty()) {
            throw new AlgoException("Usage: find <keyword>");
        }
        this.keyword = k;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matches = tasks.findByKeyword(keyword);

        ui.showMessage("Here are the matching tasks in your list:");
        ui.showTaskList(matches);
    }
}