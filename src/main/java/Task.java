public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone? "X": " ");
    }

    public void markAsCompleted() {
        isDone = true;
    }

    public void markAsNotCompleted() {
        isDone = false;
    }

    public boolean isCompleted() {
        return isDone;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
