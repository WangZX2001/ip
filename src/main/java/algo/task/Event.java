package algo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs during a specific time interval.
 * An {@code Event} task has a description, a start date/time, and an end date/time.
 */
public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;
    private final boolean fromHasTime;
    private final boolean toHasTime;

    private static final DateTimeFormatter DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Creates an Event task with the specified description and time interval.
     *
     * @param description The description of the event.
     * @param from The start date/time of the event.
     * @param fromHasTime Indicates whether the start time includes a specific time.
     * @param to The end date/time of the event.
     * @param toHasTime Indicates whether the end time includes a specific time.
     */
    public Event(String description, LocalDateTime from, boolean fromHasTime,
                 LocalDateTime to, boolean toHasTime) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromHasTime = fromHasTime;
        this.toHasTime = toHasTime;
    }

    /**
     * Returns the start date/time of the event.
     *
     * @return The start time as a {@code LocalDateTime}.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date/time of the event.
     *
     * @return The end time as a {@code LocalDateTime}.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Indicates whether the start time includes a specific time.
     *
     * @return {@code true} if the start time includes time, {@code false} if it only includes a date.
     */
    public boolean fromHasTime() {
        return fromHasTime;
    }

    /**
     * Indicates whether the end time includes a specific time.
     *
     * @return {@code true} if the end time includes time, {@code false} if it only includes a date.
     */
    public boolean toHasTime() {
        return toHasTime;
    }

    /**
     * Formats a {@code LocalDateTime} based on whether it contains time information.
     *
     * @param dt The date/time to format.
     * @param hasTime Indicates whether the date/time includes time.
     * @return A formatted string representation of the date/time.
     */
    private static String format(LocalDateTime dt, boolean hasTime) {
        return hasTime ? dt.format(DATE_TIME) : dt.toLocalDate().format(DATE_ONLY);
    }

    /**
     * Returns the string representation of the event task.
     * The output includes the task type, completion status, description,
     * and formatted start and end times.
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + format(from, fromHasTime)
                + " to: " + format(to, toHasTime) + ")";
    }
}