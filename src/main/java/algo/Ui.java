package algo;

import algo.task.Task;
import java.util.List;
import java.util.Scanner;

/**
 * Handles all user interaction for the Algo application.
 * This class is responsible for displaying messages and
 * reading user input from the console.
 */
public class Ui {
    private static final String BOT_NAME = "ALGO";
    private static final String LINE = "______________________________________________________________";
    private static final String LOGO =
            """
                      ___    _      ____   ___
                     / _ \\  | |    / ___| / _ \\
                    / /_\\ \\ | |   | |  _ | | | |
                    |  _  | | |___| |_| || |_| |
                    |_| |_| |_____\\____/  \\___/
                    """;
    private final Scanner scanner;

    /**
     * Creates a Ui instance that reads user input from the console.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command entered by the user.
     *
     * @return The trimmed command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the input scanner used by the UI.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Displays the welcome message and application logo.
     */
    public void showWelcomeMessage() {
        printLine();
        System.out.println(LOGO);
        System.out.println("Hello! I'm " + BOT_NAME);
        System.out.println("What can I do for you?");
        printLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showErrorMessage(String message) {
        System.out.println(":( OH NO!!! " + message);
    }

    /**
     * Prints a separator line used to visually separate UI sections.
     */
    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a warning message if tasks cannot be loaded from storage.
     */
    public void showLoadingError() {
        System.out.println("Warning: Could not load tasks from storage.");
    }

    /**
     * Displays the application's welcome screen.
     */
    public void showWelcome() {
        showWelcomeMessage();
    }

    /**
     * Displays an error message using the UI format.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showErrorMessage(message);
    }

    /**
     * Displays a list of tasks to the user.
     *
     * @param tasks The list of tasks to display.
     */
    public void showTaskList(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays a generic message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
