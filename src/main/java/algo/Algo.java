package algo;

import algo.task.Deadline;
import algo.task.Event;
import algo.task.Task;
import algo.task.Todo;

import java.util.Scanner;
import java.util.ArrayList;

public class Algo {

    private static final String BOT_NAME = "ALGO";
    private static final String LINE = "________________________________________________________";
    private static final String LOGO =
            """
                      ___    _      ____   ___
                     / _ \\  | |    / ___| / _ \\
                    / /_\\ \\ | |   | |  _ | | | |
                    |  _  | | |___| |_| || |_| |
                    |_| |_| |_____\\____/  \\___/
                    """;
    private static final ArrayList<Task> tasks = new ArrayList<>();


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printGreeting();
        executeCommand(in);
        printByeMessage();

        in.close();
    }

    private static void executeCommand(Scanner in) {
        while (in.hasNextLine()) {
            String input = in.nextLine().trim();
            String lower = input.toLowerCase();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    break;
                }
                if (input.equalsIgnoreCase("list")) {
                    printList();
                    continue;
                }
                if (lower.equals("mark") || lower.startsWith("mark ")) {
                    handleMarkMessage(input, true);
                    continue;
                }
                if (lower.equals("unmark") || lower.startsWith("unmark ")) {
                    handleMarkMessage(input, false);
                    continue;
                }
                if (lower.equals("delete") || lower.startsWith("delete ")) {
                    handleDeleteMessage(input);
                    continue;
                }
                addTask(input);
            } catch (AlgoException e) {
                printLine();
                System.out.println(":( OH NO!!! " + e.getMessage());
                printLine();
            }
        }
    }

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printGreeting() {
        printLine();
        System.out.println(LOGO);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");
        printLine();
    }

    private static void printByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    private static void addTask(String input) throws AlgoException {
        Task task = createTask(input);
        tasks.add(task);
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        printLine();
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

    private static void printList() {
        printLine();
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        printLine();
    }

    private static void handleMarkMessage(String input, boolean isMarkedAsDone) throws AlgoException {

        String prefix = isMarkedAsDone ? "mark" : "unmark";
        String numberPart = input.substring(prefix.length()).trim();

        int index = parseTaskIndex(numberPart);
        Task t = tasks.get(index);
        t.setDone(isMarkedAsDone);

        printLine();

        if (isMarkedAsDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(t);
        printLine();
    }

    private static void handleDeleteMessage(String input) throws AlgoException {
        String numberPart = input.substring("delete".length()).trim();
        int index = parseTaskIndex(numberPart);
        Task removed = tasks.remove(index);
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        printLine();
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