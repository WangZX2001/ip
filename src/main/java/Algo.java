import java.util.Scanner;

public class Algo {

    private static final String BOT_NAME = "ALGO";
    private static final String LINE = "________________________________________________________";
    private static final String LOGO =
            """
                      ___    _      ____   ___ \s
                     / _ \\  | |    / ___| / _ \\\s
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
        String input = in.nextLine().trim();

        while (!input.equalsIgnoreCase("bye")) {

            if (input.equalsIgnoreCase("list")) {
                printList();
            } else if (input.startsWith("mark ")) {
                handleMarkMessage(input, true);
            } else if (input.startsWith("unmark ")) {
                handleMarkMessage(input, false);
            } else {
                addTask(input);
            }
            input = in.nextLine();
        }
        printByeMessage();
        in.close();
    }

    private static void printLine() { System.out.println(LINE); }

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

    private static void addTask(String input) {
        if (taskCount == tasks.length) {
            System.out.println("Task list is full");
            printLine();
            return;
        }

        Task task;
        if (input.startsWith("deadline ")) {
            String content = input.substring(9).trim();
            String[] parts = content.split(" /by ", 2);

            String desc = parts[0].trim();
            String by = parts[1].trim();
            task = new Deadline(desc, by);

        } else if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            task = new Todo(desc);
        } else if (input.startsWith("event ")){
            String content = input.substring(6).trim();
            String[] parts = content.split("/from | /to", 3);

            String desc = parts[0].trim();
            String from = parts[1].trim();
            String to = parts[2].trim();
            task = new Event(desc, from, to);
        } else {
            task = new Task(input);
        }
        tasks[taskCount++] = task;
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        printLine();
    }

    private static void printList() {
        printLine();
        if (taskCount == 0) {
            System.out.println("No tasks yet");
            printLine();
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