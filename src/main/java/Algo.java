import java.util.Scanner;

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
    public static final int MAX_NUMBER_OF_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_NUMBER_OF_TASKS];
    private static int taskCount = 0;


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

            try {
                if (input.equalsIgnoreCase("bye")) {
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    printList();
                } else if (input.startsWith("mark ")) {
                    handleMarkMessage(input, true);
                } else if (input.startsWith("unmark ")) {
                    handleMarkMessage(input, false);
                } else {
                    addTask(input);
                }
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
        if (taskCount == tasks.length) {
            throw new AlgoException("Task list is full.");
        }
        Task task = createTask(input);
        tasks[taskCount++] = task;
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    private static Task createTask(String input) throws AlgoException {
        String lower = input.toLowerCase();

        if (lower.startsWith("deadline")) {
            return createDeadline(input.substring("deadline".length()).trim());
        }
        if (lower.startsWith("todo")) {
            return createTodo(input.substring("todo".length()).trim());
        }
        if (input.startsWith("event")) {
            return createEvent(input.substring("event".length()).trim());
        }
        throw new AlgoException("Invalid command.");
    }

    private static Event createEvent(String args) throws AlgoException {
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

    private static Task createTodo(String args) throws AlgoException {
        if (args.isEmpty()) {
            throw new AlgoException("The description of a todo cannot be empty.\n" +
                    "Usage: todo <description>");
        }
        return new Todo(args);
    }

    private static Task createDeadline(String args) throws AlgoException {
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
        if (taskCount == 0) {
            System.out.println("No tasks yet");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + "." + tasks[i]);
            }
        }
        printLine();
    }

    private static void handleMarkMessage(String input, boolean isMarkedAsDone) {

        String prefix = isMarkedAsDone ? "mark " : "unmark ";
        String numberPart = input.substring(prefix.length()).trim();

        //Edge case 1: Number is missing
        if (numberPart.isEmpty()) {
            System.out.println("Please specify a task number.");
            printLine();
            return;
        }

        int index = Integer.parseInt(numberPart) - 1;
        //Edge case 2: not a positive number or out of range
        if (index < 0 || index >= taskCount) {
            System.out.println("Invalid task number.");
            printLine();
            return;
        }

        Task t = tasks[index];
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
}