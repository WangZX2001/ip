package algo;

import algo.command.Command;

public class Algo {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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

    public static void main(String[] args) {
        new Algo("data/algo.txt").run();
    }
}

