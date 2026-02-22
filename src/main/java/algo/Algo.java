package algo;

import algo.task.Task;
import java.util.List;

public class Algo {

    private static TaskList tasks;
    private static final String FILE_PATH = "data/algo.txt";
    private static final Storage storage = new Storage(FILE_PATH);


    public static void main(String[] args) {
        Ui ui = new Ui();
        try {
            List<Task> loaded = storage.load();
            tasks = new TaskList(loaded);
        } catch (AlgoException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
        ui.showWelcomeMessage();
        executeCommand(ui);
        ui.showByeMessage();
        ui.close();
    }

    private static void executeCommand(Ui ui) {
        while (true) {
            String input = ui.readCommand();

            try {
                ParsedCommand parsed = Parser.parseCommand(input);

                switch (parsed.command) {

                case "bye":
                    return;

                case "list":
                    ui.printLine();
                    System.out.println(tasks.listTasks());
                    ui.printLine();
                    break;

                case "todo":
                case "deadline":
                case "event": {
                    String msg = tasks.addTask(parsed.fullInput);
                    storage.save(tasks.getAll());
                    ui.printLine();
                    System.out.println(msg);
                    ui.printLine();
                    break;
                }

                case "mark": {
                    String msg = tasks.setDone(parsed.args, true);
                    storage.save(tasks.getAll());
                    ui.printLine();
                    System.out.println(msg);
                    ui.printLine();
                    break;
                }

                case "unmark": {
                    String msg = tasks.setDone(parsed.args, false);
                    storage.save(tasks.getAll());
                    ui.printLine();
                    System.out.println(msg);
                    ui.printLine();
                    break;
                }

                case "delete": {
                    String msg = tasks.deleteTask(parsed.args);
                    storage.save(tasks.getAll());
                    ui.printLine();
                    System.out.println(msg);
                    ui.printLine();
                    break;
                }

                default:
                    throw new AlgoException("Invalid command.");
                }

            } catch (AlgoException e) {
                ui.showErrorMessage(e.getMessage());
            }
        }
    }
}

