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
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

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
            int byIndex = content.indexOf("(by:");
            String desc = content.substring(0, byIndex).trim();
            String by = content.substring(byIndex + 4, content.length() - 1).trim();
            return "D | " + done + " | " + desc + " | " + by;
        }

        // Event
        int fromIndex = content.indexOf("(from:");
        String desc = content.substring(0, fromIndex).trim();
        String timePart = content.substring(fromIndex + 6, content.length() - 1).trim();

        String[] times = timePart.split("\\s+to:\\s+", 2);
        return "E | " + done + " | " + desc + " | " + times[0].trim() + " | " + times[1].trim();
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