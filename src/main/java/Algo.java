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
    private static int taskCount = 0;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        printGreeting();
        String input = in.nextLine();

        while (!input.equalsIgnoreCase("bye")) {

            if (input.equalsIgnoreCase("list")) {
                printList();
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
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println(LINE);
    }
}