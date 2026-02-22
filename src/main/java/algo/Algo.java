package algo;

import algo.task.Deadline;
import algo.task.Event;
import algo.task.Task;
import algo.task.Todo;

import java.util.List;
import java.util.ArrayList;

public class Algo {

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final Storage storage = new Storage();


    public static void main(String[] args) {
        Ui ui = new Ui();

        List<Task> loaded = storage.load();
        tasks.addAll(loaded);
        ui.showWelcomeMessage();
        executeCommand(ui);
        ui.showByeMessage();
        ui.close();
    }

    private static void executeCommand(Ui ui) {
        while (true) {
            String input = ui.readCommand();   // already trimmed
            String lower = input.toLowerCase();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    break;
                }

                //extract out the command and the argument
                int spaceIndex = lower.indexOf(" ");
                String command = (spaceIndex == -1) ? lower : lower.substring(0, spaceIndex);
                String args = (spaceIndex == -1) ? "" : input.substring(spaceIndex + 1).trim();

                switch (command) {
                case "list":
                    printList(ui);
                    break;

                case "mark":
                    handleMarkMessage(ui, args, true);
                    break;

                case "unmark":
                    handleMarkMessage(ui, args, false);
                    break;

                case "delete":
                    handleDeleteMessage(ui, args);
                    break;

                case "todo":
                case "deadline":
                case "event":
                    addTask(ui, input);
                    break;

                default:
                    throw new AlgoException(
                            "Invalid command. Try:\n" +
                                    "todo, deadline, event, mark, unmark, list, bye, delete"
                    );
                }
            } catch (AlgoException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }
    }

    private static void addTask(Ui ui, String input) throws AlgoException {
        Task task = createTask(input);
        tasks.add(task);
        storage.save(tasks);
        ui.printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.printLine();
    }

    private static Task createTask(String input) throws AlgoException {
        String lower = input.toLowerCase();

        if (lower.startsWith("deadline")) {
            return getDeadline(input.substring("deadline".length()).trim());
        }
        if (lower.startsWith("todo")) {
            return getTodo(input.substring("todo".length()).trim());
        }
        if (lower.startsWith("event")) {
            return getEvent(input.substring("event".length()).trim());
        }
        throw new AlgoException(
                "Invalid command. Try:\n" +
                        "todo, deadline, event, mark, unmark, list, bye, delete"
        );

    }

    private static Event getEvent(String args) throws AlgoException {
        if (args.isEmpty()) {
            throw new AlgoException("The description of an event cannot be empty.\n" +
                    "An event must include '/from <start>' and '/to <end>'.");
        }
        String[] parts = args.split("/from | /to", 3);
        if (parts.length < 3) {
            throw new AlgoException("An event must include '/from <start>' and '/to <end>'.");
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) {
            throw new AlgoException("The description of an event cannot be empty.");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new AlgoException("The start and end time of an event cannot be empty.");
        }

        return new Event(description, from, to);
    }

    private static Task getTodo(String args) throws AlgoException {
        if (args.isEmpty()) {
            throw new AlgoException("The description of a todo cannot be empty.\n" +
                    "Usage: todo <description>");
        }
        return new Todo(args);
    }

    private static Task getDeadline(String args) throws AlgoException {
        if (args.isEmpty()) {
            throw new AlgoException("Usage: deadline <description> /by <time>");
        }

        int byIndex = args.indexOf(" /by ");
        if (byIndex == -1) {
            throw new AlgoException("Usage: deadline <description> /by <time>");
        }

        String description = args.substring(0, byIndex).trim();
        String by = args.substring(byIndex + " /by ".length()).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new AlgoException("Usage: deadline <description> /by <time>");
        }

        return new Deadline(description, by);
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

    private static void handleMarkMessage(Ui ui, String args, boolean isMarkedAsDone) throws AlgoException {
        int index = parseTaskIndex(args);
        Task t = tasks.get(index);
        t.setDone(isMarkedAsDone);
        storage.save(tasks);

        ui.printLine();
        System.out.println(isMarkedAsDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println(t);
        ui.printLine();
    }

    private static void handleDeleteMessage(Ui ui, String args) throws AlgoException {
        int index = parseTaskIndex(args);
        Task removed = tasks.remove(index);
        storage.save(tasks);
        ui.printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        ui.printLine();
    }

    private static int parseTaskIndex(String numberPart) throws AlgoException {
        if (numberPart.isEmpty()) {
            throw new AlgoException("Please specify a task number.");
        }
        int index;
        try {
            index = Integer.parseInt(numberPart) - 1;
        } catch (NumberFormatException e) {
            throw new AlgoException("Task number must be a number.");
        }

        //Edge case 2: not a positive number or out of range
        if (index < 0 || index >= tasks.size()) {
            throw new AlgoException("Invalid task number.");
        }
        return index;
    }
}