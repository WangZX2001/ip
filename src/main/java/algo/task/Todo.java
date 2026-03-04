package algo.task;

/**
 * Represents a simple task without any date or time constraints.
 * A {@code Todo} task only contains a description and a completion status.
 */
public class Todo extends Task {

    /**
     * Creates a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo task.
     * The output includes the task type, completion status, and description.
     *
     * @return A formatted string representing the todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
