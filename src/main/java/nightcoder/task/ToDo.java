package nightcoder.task;

/**
 * Represents a task with no specific date or time attached.
 * Extends the base Task class.
 *
 * @author ShamanBenny
 * @version 10
 */
public class ToDo extends Task {

    /**
     * Constructs a new To Do task with the specified description.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     */
    public ToDo(String description, boolean isCompleted) {
        super(description, isCompleted);
    }

    /**
     * Returns a formatted string representation of the task.
     *
     * @return A string formatted as "T|completion_status|description".
     */
    public String getStringFormat() {
        return "T|" + (isCompleted() ? "1" : "0") + "|" + getDescription();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString(); // Prefix with [T] for To Do tasks
    }
}
