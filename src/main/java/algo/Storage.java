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

    public void save(Task[] tasks, int taskCount) throws AlgoException {
        try {
            if (!Files.exists(FILE_PATH.getParent())) {
                Files.createDirectories(FILE_PATH.getParent());
            }

            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                lines.add(encodeTask(tasks[i]));
            }

            Files.write(FILE_PATH, lines);

        } catch (IOException e) {
            throw new AlgoException("Error saving tasks.");
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

        int fromIndex = content.indexOf("(from:");
        String desc = content.substring(0, fromIndex).trim();
        String timePart = content.substring(fromIndex + 6, content.length() - 1).trim();

        String[] times = timePart.split("\\s+to:\\s+", 2);
        return "E | " + done + " | " + desc + " | " + times[0].trim() + " | " + times[1].trim();
    }

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

        Task t;

        switch (type) {

        case "T":
            t = new Todo(desc);
            break;

        case "D":
            if (parts.length < 4) {
                throw new RuntimeException("Corrupted deadline");
            }
            t = new Deadline(desc, parts[3].trim());
            break;

        case "E":
            if (parts.length < 5) {
                throw new RuntimeException("Corrupted event");
            }
            t = new Event(desc, parts[3].trim(), parts[4].trim());
            break;

        default:
            throw new RuntimeException("Unknown task type");
        }
        t.setDone(isDone);
        return t;
    }
}