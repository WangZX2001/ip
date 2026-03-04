package algo;

import algo.command.Command;

/**
 * Main entry point of the Algo application.
 * This class initializes the user interface, storage, and task list,
 * and manages the main program execution loop.
 */
public class Algo {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates an Algo application instance and initializes the required components.
     * Tasks are loaded from the specified storage file. If loading fails,
     * an empty task list is created instead.
     *
     * @param filePath The file path used for storing and loading tasks.
     */
    public Algo(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (AlgoException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop of the application.
     * The program continuously reads user commands, parses them,
     * executes the corresponding command, and displays results
     * until an exit command is issued.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (AlgoException e) {
                ui.showError(e.getMessage());
            } finally {
                if (!isExit) {
                    ui.printLine();
                }
            }
        }
        ui.showByeMessage();
        ui.close();
    }

    /**
     * Starts the Algo application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Algo("data/algo.txt").run();
    }
}

