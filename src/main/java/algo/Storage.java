package algo;

import algo.task.Deadline;
import algo.task.Event;
import algo.task.Task;
import algo.task.Todo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path FILE_PATH = Paths.get("data", "algo.txt");

    public List<Task> load() {
        ArrayList<Task> loaded = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) {
            return loaded;
        }

        try {
            List<String> lines = Files.readAllLines(FILE_PATH);

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    loaded.add(parseTaskLine(line));
                } catch (Exception corruptedLine) {
                    // Implement skip corrupted lines
                }
            }

        } catch (IOException e) {
            // If file cannot be read, just start empty
        }

        return loaded;
    }

    private Task parseTaskLine(String line) {

        String[] parts = line.split("\\s*\\|\\s*");

        if (parts.length < 3) {
            throw new RuntimeException("Corrupted line");
        }

        String type = parts[0].trim();
        String doneStr = parts[1].trim();
        String desc = parts[2].trim();

        boolean isDone = "1".equals(doneStr);

        Task t = switch (type) {
            case "T" -> new Todo(desc);
            case "D" -> {
                if (parts.length < 4) {
                    throw new RuntimeException("Corrupted deadline");
                }
                yield new Deadline(desc, parts[3].trim());
            }
            case "E" -> {
                if (parts.length < 5) {
                    throw new RuntimeException("Corrupted event");
                }
                yield new Event(desc, parts[3].trim(), parts[4].trim());
            }
            default -> throw new RuntimeException("Unknown task type");
        };

        t.setDone(isDone);
        return t;
    }
}