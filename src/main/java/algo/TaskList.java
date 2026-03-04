package algo;

import algo.task.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the collection of tasks in the application.
 * This class provides methods for adding, deleting, retrieving,
 * and searching tasks stored in the task list.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList initialized with a list of previously loaded tasks.
     *
     * @param loadedTasks Tasks loaded from storage.
     */
    public TaskList(List<Task> loadedTasks) {
        this.tasks = new ArrayList<>(loadedTasks);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task.
     * @return The task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks whether the task list is empty.
     *
     * @return {@code true} if the task list contains no tasks, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the complete list of tasks.
     *
     * @return A list containing all tasks.
     */
    public List<Task> getAll() {
        return tasks;
    }

    /**
     * Parses and adds a task from a user input string.
     *
     * @param input The full user input describing the task.
     * @return A confirmation message indicating the task added and current list size.
     * @throws AlgoException If the task input format is invalid.
     */
    public String addTask(String input) throws AlgoException {
        Task task = Parser.parseTask(input);
        add(task);
        return "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + size() + " tasks in the list.";
    }

    /**
     * Returns a formatted string listing all tasks in the task list.
     *
     * @return A formatted list of tasks, or a message if no tasks exist.
     */
    public String listTasks() {
        if (isEmpty()) {
            return "No tasks yet";
        }
        StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < size(); i++) {
            result.append((i + 1)).append(".").append(get(i)).append("\n");
        }
        return result.toString().trim();
    }

    /**
     * Marks or unmarks a task as completed.
     *
     * @param indexArg The task index provided by the user.
     * @param isDone {@code true} to mark the task as done, {@code false} to mark it as not done.
     * @return A confirmation message showing the updated task.
     * @throws AlgoException If the provided index is invalid.
     */
    public String setDone(String indexArg, boolean isDone) throws AlgoException {
        int index = Parser.parseIndex(indexArg, size());
        Task t = get(index);
        t.setDone(isDone);

        return (isDone
                ? "Nice! I've marked this task as done:\n"
                : "OK, I've marked this task as not done yet:\n")
                + t;
    }

    /**
     * Deletes a task based on the user-provided index.
     *
     * @param indexArg The task index provided by the user.
     * @return A confirmation message showing the removed task and updated list size.
     * @throws AlgoException If the provided index is invalid.
     */
    public String deleteTask(String indexArg) throws AlgoException {
        int index = Parser.parseIndex(indexArg, size());
        Task removed = delete(index);

        return "Noted. I've removed this task:\n"
                + "  " + removed + "\n"
                + "Now you have " + size() + " tasks in the list.";
    }

    /**
     * Searches for tasks whose descriptions contain the specified keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of tasks whose descriptions match the keyword.
     */
    public List<Task> findByKeyword(String keyword) {
        String k = keyword.toLowerCase();
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(k)) {
                result.add(t);
            }
        }
        return result;
    }
}