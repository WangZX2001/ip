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
    private static final String[] tasks = new String[100];
    private static final boolean[] isTaskCompleted = new boolean[100];
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

    private static void printByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);

    }
    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println(LOGO);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }
    private static void addTask(String task) {
        if (taskCount == tasks.length) {
            System.out.println("Task list is full");
            System.out.println(LINE);
        } else {
            tasks[taskCount] = task;
            isTaskCompleted[taskCount] = false;
            taskCount++;
            System.out.println(LINE);
            System.out.println( "add: " + task);
            System.out.println(LINE);
        }
    }
    private static void printList() {
        System.out.println(LINE);
        if (taskCount == 0) {
            System.out.println("No tasks yet");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                String status;

                if (isTaskCompleted[i]) {
                    status = "[X]";
                } else {
                    status = "[ ]";
                }
                System.out.println((i + 1) + "." + status + " " + tasks[i]);
            }
        }
        System.out.println(LINE);
    }
    private static void handleMarkMessage(String input, boolean isDone) {

        String prefix;

        if (isDone) {
            prefix = "mark ";
        } else {
            prefix = "unmark ";
        }

        String numberPart = input.substring(prefix.length());

        //Edge case 1: Number is missing
        if (numberPart.isEmpty()) {
            System.out.println("Please specify a task number.");
            System.out.println(LINE);
            return;
        }

        int taskNumber = Integer.parseInt(numberPart);
        //Edge case 2: not a positive number
        if (taskNumber < 1) {
            System.out.println("Task number must be at least 1.");
            System.out.println(LINE);
            return;
        }

        int index = taskNumber - 1;
        //Edge case: out of range
        if (index >= taskCount) {
            System.out.println("Invalid task number. You only can have " + taskCount + " tasks");
            System.out.println(LINE);
            return;
        }

        isTaskCompleted[index] = isDone;

        System.out.println(LINE);

        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + "[X]" + tasks[index]);
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + "[ ]" + tasks[index]);
        }
        System.out.println(LINE);
    }

}