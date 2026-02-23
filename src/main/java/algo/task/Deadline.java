package algo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private final LocalDateTime by;
    private final boolean hasTime;

    private static final DateTimeFormatter DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Deadline(String description, LocalDateTime by, boolean hasTime) {
        super(description);
        this.by = by;
        this.hasTime = hasTime;
    }

    public LocalDateTime getBy() {
        return by;
    }

    public boolean hasTime() {
        return hasTime;
    }

    @Override
    public String toString() {
        String formatted = hasTime
                ? by.format(DATE_TIME)
                : by.toLocalDate().format(DATE_ONLY);

        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }
}