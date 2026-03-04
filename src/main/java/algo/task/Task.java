package algo.task;

/**
 * Represents a generic task with a description and completion status.
 * This class serves as the base class for specific task types such as
 * {@code Todo}, {@code Deadline}, and {@code Event}.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a Task with the specified description.
     * The task is initially marked as not completed.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is completed.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone? "X": " ");
    }

    /**
     * Sets the completion status of the task.
     *
     * @param isDone {@code true} if the task is completed, {@code false} otherwise.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string representation of the task.
     * The output includes the completion status icon and the task description.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
