package algo;

import algo.task.Task;
import java.util.List;
import java.util.ArrayList;

public class Algo {

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String FILE_PATH = "data/algo.txt";
    private static final Storage storage = new Storage(FILE_PATH);


    public static void main(String[] args) {
        Ui ui = new Ui();
        try {
            List<Task> loaded = storage.load();
            tasks.addAll(loaded);
        } catch (AlgoException e) {
            ui.showLoadingError();
        }
        ui.showWelcomeMessage();
        executeCommand(ui);
        ui.showByeMessage();
        ui.close();
    }

    private static void executeCommand(Ui ui) {
        while (true) {
            String input = ui.readCommand();

            try {
                ParsedCommand parsed = Parser.parseCommand(input);

                switch (parsed.command) {

                case "bye":
                    return;

                case "list":
                    printList(ui);
                    break;

                case "todo":
                case "deadline":
                case "event": {
                    addTask(ui, parsed.fullInput);
                    break;
                }

                case "mark": {
                    setDone(ui, parsed.args, true);
                    break;
                }

                case "unmark": {
                    setDone(ui, parsed.args, false);
                    break;
                }

                case "delete": {
                    deleteTask(ui, parsed.args);
                    break;
                }

                default:
                    throw new AlgoException("Invalid command.");
                }

            } catch (AlgoException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }
    }

    private static void addTask(Ui ui, String input) throws AlgoException {
        Task task = Parser.parseTask(input);
        tasks.add(task);
        storage.save(tasks);
        ui.printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.printLine();
    }

    private static void printList(Ui ui) {
        ui.printLine();
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        ui.printLine();
    }

    private static void setDone(Ui ui, String indexArg, boolean isDone) throws AlgoException {
        int index = Parser.parseIndex(indexArg, tasks.size());
        Task t = tasks.get(index);
        t.setDone(isDone);
        storage.save(tasks);

        ui.printLine();
        System.out.println(isDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println(t);
        ui.printLine();
    }

    private static void deleteTask(Ui ui, String indexArg) throws AlgoException {
        int index = Parser.parseIndex(indexArg, tasks.size());
        Task removed = tasks.remove(index);
        storage.save(tasks);
        ui.printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.printLine();
    }
}