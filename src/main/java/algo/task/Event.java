package algo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;
    private final boolean fromHasTime;
    private final boolean toHasTime;

    private static final DateTimeFormatter DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String description, LocalDateTime from, boolean fromHasTime,
                 LocalDateTime to, boolean toHasTime) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromHasTime = fromHasTime;
        this.toHasTime = toHasTime;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public boolean fromHasTime() {
        return fromHasTime;
    }

    public boolean toHasTime() {
        return toHasTime;
    }

    private static String format(LocalDateTime dt, boolean hasTime) {
        return hasTime ? dt.format(DATE_TIME) : dt.toLocalDate().format(DATE_ONLY);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + format(from, fromHasTime)
                + " to: " + format(to, toHasTime) + ")";
    }
}