package algo;

import algo.task.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> loadedTasks) {
        this.tasks = new ArrayList<>(loadedTasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getAll() {
        return tasks;
    }

    public String addTask(String input) throws AlgoException {
        Task task = Parser.parseTask(input);
        add(task);
        return "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + size() + " tasks in the list.";
    }

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

    public String setDone(String indexArg, boolean isDone) throws AlgoException {
        int index = Parser.parseIndex(indexArg, size());
        Task t = get(index);
        t.setDone(isDone);

        return (isDone
                ? "Nice! I've marked this task as done:\n"
                : "OK, I've marked this task as not done yet:\n")
                + t;
    }

    public String deleteTask(String indexArg) throws AlgoException {
        int index = Parser.parseIndex(indexArg, size());
        Task removed = delete(index);

        return "Noted. I've removed this task:\n"
                + "  " + removed + "\n"
                + "Now you have " + size() + " tasks in the list.";
    }
}