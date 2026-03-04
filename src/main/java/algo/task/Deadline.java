package algo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 * A {@code Deadline} task has a description and a date/time by which it must be completed.
 */
public class Deadline extends Task {

    private final LocalDateTime by;
    private final boolean hasTime;

    private static final DateTimeFormatter DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Creates a Deadline task with the specified description and deadline.
     *
     * @param description The description of the task.
     * @param by The date and time by which the task should be completed.
     * @param hasTime Indicates whether the deadline includes a specific time.
     */
    public Deadline(String description, LocalDateTime by, boolean hasTime) {
        super(description);
        this.by = by;
        this.hasTime = hasTime;
    }

    /**
     * Returns the deadline date and time of the task.
     *
     * @return The deadline as a {@code LocalDateTime}.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Indicates whether the deadline includes a specific time.
     *
     * @return {@code true} if the deadline includes time, {@code false} if it only includes a date.
     */
    public boolean hasTime() {
        return hasTime;
    }

    /**
     * Returns the string representation of the deadline task.
     * The output includes the task type, completion status, description,
     * and formatted deadline.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        String formatted = hasTime
                ? by.format(DATE_TIME)
                : by.toLocalDate().format(DATE_ONLY);

        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }
}