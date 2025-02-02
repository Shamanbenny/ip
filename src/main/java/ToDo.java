/**
 * Represents a task with no specific date or time attached.
 * Extends the base Task class.
 *
 * @author ShamanBenny
 * @version 7
 */
public class ToDo extends Task {

    /**
     * Constructs a new ToDo task with the specified description.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     */
    public ToDo(String description, boolean isCompleted) {
        super(description, isCompleted);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString(); // Prefix with [T] for ToDo tasks
    }
}