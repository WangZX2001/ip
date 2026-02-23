package algo;

import algo.command.AddCommand;
import algo.command.Command;
import algo.command.DeleteCommand;
import algo.command.ListCommand;
import algo.command.MarkCommand;
import algo.command.UnmarkCommand;
import algo.task.Deadline;
import algo.task.Event;
import algo.task.Task;
import algo.task.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final String TODO_ERROR =
            "The description of a todo cannot be empty.\n"
                    + "Usage: todo <description>";

    private static final String EVENT_ERROR =
            "Usage: event <description> /from <start> /to <end>";

    private static final String INDEX_EMPTY_ERROR =
            "Please specify a task number.";

    private static final String INDEX_NUMBER_ERROR =
            "Task number must be a number.";

    private static final String INDEX_RANGE_ERROR =
            "Invalid task number.";

    private static final DateTimeFormatter INPUT_DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter INPUT_DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static Command parse(String fullCommand) throws AlgoException {
        String trimmed = fullCommand.trim();
        String lower = trimmed.toLowerCase();

        int spaceIndex = lower.indexOf(" ");
        String command = (spaceIndex == -1)
                ? lower
                : lower.substring(0, spaceIndex);

        String args = (spaceIndex == -1)
                ? ""
                : trimmed.substring(spaceIndex + 1).trim();

        return switch (command) {
            case "bye" -> new AddCommand.ByeCommand();
            case "list" -> new ListCommand();
            case "todo", "deadline", "event" -> new AddCommand(trimmed);
            case "mark" -> new MarkCommand(args);
            case "unmark" -> new UnmarkCommand(args);
            case "delete" -> new DeleteCommand(args);
            default -> throw new AlgoException("Invalid command.");
        };
    }

    public static Task parseTask(String fullInput) throws AlgoException {
        String lower = fullInput.toLowerCase();

        if (lower.startsWith("todo")) {
            return parseTodo(fullInput.substring("todo".length()).trim());
        }
        if (lower.startsWith("deadline")) {
            return parseDeadline(fullInput.substring("deadline".length()).trim());
        }
        if (lower.startsWith("event")) {
            return parseEvent(fullInput.substring("event".length()).trim());
        }

        throw new AlgoException("Invalid task command.");
    }

    public static int parseIndex(String numberPart, int listSize)
            throws AlgoException {

        if (numberPart == null || numberPart.trim().isEmpty()) {
            throw new AlgoException(INDEX_EMPTY_ERROR);
        }

        int index;
        try {
            index = Integer.parseInt(numberPart.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new AlgoException(INDEX_NUMBER_ERROR);
        }

        if (index < 0 || index >= listSize) {
            throw new AlgoException(INDEX_RANGE_ERROR);
        }

        return index;
    }

    private static Task parseTodo(String args) throws AlgoException {
        if (args.isEmpty()) {
            throw new AlgoException(TODO_ERROR);
        }
        return new Todo(args);
    }

    private static Task parseDeadline(String args) throws AlgoException {
        String usage = "Usage: deadline <desc> /by yyyy-MM-dd [HHmm]";

        if (args.isEmpty()) {
            throw new AlgoException(usage);
        }

        int byIndex = args.indexOf(" /by ");
        if (byIndex == -1) {
            throw new AlgoException(usage);
        }

        String description = args.substring(0, byIndex).trim();
        String byStr = args.substring(byIndex + " /by ".length()).trim();

        if (description.isEmpty() || byStr.isEmpty()) {
            throw new AlgoException(usage);
        }

        try {
            LocalDateTime by = LocalDateTime.parse(byStr, INPUT_DATE_TIME);
            return new Deadline(description, by);
        } catch (DateTimeParseException ignored) {
            try {
                LocalDate date = LocalDate.parse(byStr, INPUT_DATE);
                return new Deadline(description, date.atStartOfDay());
            } catch (DateTimeParseException e) {
                throw new AlgoException("""
                        Invalid date format. Examples:
                        deadline return book /by 2019-10-15
                        deadline return book /by 2019-10-15 1800""");
            }
        }
    }

    private static Event parseEvent(String args) throws AlgoException {

        String usage = "Usage: event <desc> /from yyyy-MM-dd [HHmm] /to yyyy-MM-dd [HHmm]";

        if (args.isEmpty()) {
            throw new AlgoException(usage);
        }

        int fromIndex = args.indexOf(" /from ");
        int toIndex = args.indexOf(" /to ");

        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            throw new AlgoException(usage);
        }

        String description = args.substring(0, fromIndex).trim();
        String fromStr = args.substring(fromIndex + " /from ".length(), toIndex).trim();
        String toStr = args.substring(toIndex + " /to ".length()).trim();

        if (description.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
            throw new AlgoException(usage);
        }

        try {
            LocalDateTime from = parseDateTimeFlexible(fromStr);
            LocalDateTime to = parseDateTimeFlexible(toStr);

            return new Event(description, from, to);

        } catch (DateTimeParseException e) {
            throw new AlgoException(usage);
        }
    }

    private static LocalDateTime parseDateTimeFlexible(String input)
            throws DateTimeParseException {

        try {
            return LocalDateTime.parse(input, INPUT_DATE_TIME);
        } catch (DateTimeParseException ignored) {
            LocalDate date = LocalDate.parse(input, INPUT_DATE);
            return date.atStartOfDay();
        }
    }
}
