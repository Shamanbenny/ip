package nightcoder.task;

/**
 * Represents a task that has a specific deadline.
 * Extends the base Task class.
 *
 * @author ShamanBenny
 * @version 8.1
 */
public class Deadline extends Task {
    private final String dueBy;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     * @param dueBy The deadline for the task.
     */
    public Deadline(String description, boolean isCompleted, String dueBy) {
        super(description, isCompleted);
        this.dueBy = dueBy;
    }

    public String getDueBy() {
        return this.dueBy;
    }

    /**
     * Returns a formatted string representation of the task.
     *
     * @return A string formatted as "T|completion_status|description|due_by".
     */
    public String getStringFormat() {
        return "D|" + (isCompleted() ? "1" : "0") + "|" + getDescription() + "|" + getDueBy();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + this.dueBy + ")";
    }
}
