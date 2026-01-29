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
    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printGreeting();
        String input = in.nextLine();

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

    private static void addTask(String description) {
        if (taskCount == tasks.length) {
            System.out.println("Task list is full");
            System.out.println(LINE);
            return;
        } else {
            tasks[taskCount++] = new Task(description);
            System.out.println("added: " + description);
            System.out.println(LINE);
        }
    }

    private static void printList() {
        printLine();

        if (taskCount == 0) {
            System.out.println("No tasks yet");
            printLine();
            return;
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                Task t = tasks[i];
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
        printLine();

        if (isMarkedAsDone) {
            t.markAsCompleted();
            System.out.println("Nice! I've marked this task as done:");
        } else {
            t.markAsNotCompleted();
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(t);
        printLine();
    }
}