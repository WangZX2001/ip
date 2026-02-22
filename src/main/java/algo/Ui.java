package algo;

import java.util.Scanner;
public class Ui {
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
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    public void showWelcomeMessage() {
        printLine();
        System.out.println(LOGO);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");
        printLine();
    }

    public void showByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    public void showErrorMessage(String message) {
        printLine();
        System.out.println(":( OH NO!!! " + message);
        printLine();
    }

    public void printLine() {
        System.out.println(LINE);
    }

    public void showLoadingError() {
        System.out.println("Warning: Could not load tasks from storage.");
    }
}
