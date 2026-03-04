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
import java.time.LocalDateTime;

/**
 * Handles persistent storage of tasks to a local save file.
 * Tasks are saved in a line-based format and reconstructed when the application starts.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a Storage instance that reads from and writes to the specified file path.
     *
     * @param filePath The path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Saves the given list of tasks to the save file.
     * The parent directory is created if it does not exist.
     *
     * @param tasks The list of tasks to be saved.
     * @throws AlgoException If an I/O error occurs during saving.
     */
    public void save(List<Task> tasks) throws AlgoException {
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            ArrayList<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(encodeTask(t));
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new AlgoException("Error saving tasks.");
        }
    }

    /**
     * Loads tasks from the save file.
     * If the file does not exist, an empty list is returned.
     *
     * @return A list of tasks loaded from the save file.
     * @throws AlgoException If the save file cannot be read or is corrupted.
     */
    public List<Task> load() throws AlgoException {
        ArrayList<Task> loaded = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return loaded;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                loaded.add(parseTaskLine(line)); // if corrupted, throws -> caught below
            }
            return loaded;

        } catch (IOException e) {
            throw new AlgoException("Could not read save file.");
        } catch (RuntimeException e) {
            throw new AlgoException("Save file is corrupted.");
        }
    }

    private String encodeTask(Task t) {
        String full = t.toString();
        int done = full.contains("[X]") ? 1 : 0;
        char type = full.charAt(1);
        String content = full.substring(6).trim();

        if (type == 'T') {
            return "T | " + done + " | " + content;
        }

        if (type == 'D') {
            Deadline d = (Deadline) t;

            return "D | " + done + " | " + t.getDescription()
                    + " | " + d.getBy().toString()
                    + " | " + (d.hasTime() ? "1" : "0");
        }
        Event e = (Event) t;

        return "E | " + done + " | " + t.getDescription()
                + " | " + e.getFrom().toString()
                + " | " + e.getTo().toString()
                + " | " + (e.fromHasTime() ? "1" : "0")
                + " | " + (e.toHasTime() ? "1" : "0");
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
                LocalDateTime by = LocalDateTime.parse(parts[3].trim());
                boolean hasTime = parts.length >= 5 && parts[4].trim().equals("1");
                yield new Deadline(desc, by, hasTime);
            }
            case "E" -> {
                if (parts.length < 7) {
                    throw new RuntimeException("Corrupted event");
                }

                LocalDateTime from = LocalDateTime.parse(parts[3].trim());
                LocalDateTime to = LocalDateTime.parse(parts[4].trim());

                boolean fromHasTime = "1".equals(parts[5].trim());
                boolean toHasTime = "1".equals(parts[6].trim());

                yield new Event(desc, from, fromHasTime, to, toHasTime);
            }
            default -> throw new RuntimeException("Unknown task type");
        };

        t.setDone(isDone);
        return t;
    }
}