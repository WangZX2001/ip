package algo;

import algo.task.Deadline;
import algo.task.Event;
import algo.task.Task;
import algo.task.Todo;

public class Parser {
    private static final String TODO_ERROR =
            "The description of a todo cannot be empty.\n"
                    + "Usage: todo <description>";

    private static final String DEADLINE_ERROR =
            "Usage: deadline <description> /by <time>";

    private static final String EVENT_ERROR =
            "Usage: event <description> /from <start> /to <end>";

    private static final String INDEX_EMPTY_ERROR =
            "Please specify a task number.";

    private static final String INDEX_NUMBER_ERROR =
            "Task number must be a number.";

    private static final String INDEX_RANGE_ERROR =
            "Invalid task number.";

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
            case "bye" -> new ByeCommand();
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
        if (args.isEmpty()) {
            throw new AlgoException(DEADLINE_ERROR);
        }

        int byIndex = args.indexOf(" /by ");
        if (byIndex == -1) {
            throw new AlgoException(DEADLINE_ERROR);
        }

        String description = args.substring(0, byIndex).trim();
        String by = args.substring(byIndex + " /by ".length()).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new AlgoException(DEADLINE_ERROR);
        }

        return new Deadline(description, by);
    }

    private static Event parseEvent(String args) throws AlgoException {
        if (args.isEmpty()) {
            throw new AlgoException(EVENT_ERROR);
        }

        String[] parts = args.split("/from | /to", 3);
        if (parts.length < 3) {
            throw new AlgoException(EVENT_ERROR);
        }

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new AlgoException(EVENT_ERROR);
        }

        return new Event(description, from, to);
    }
}
