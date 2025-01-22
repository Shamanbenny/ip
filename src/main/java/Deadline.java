/**
 * Represents a task that has a specific deadline.
 * Extends the base Task class.
 *
 * @author ShamanBenny
 * @version 5.0
 */
public class Deadline extends Task {
    private final String dueBy;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     */
    public Deadline(String description, boolean isCompleted, String dueBy) {
        super(description, isCompleted);
        this.dueBy = dueBy;
    }

    public String getDueBy() {
        return this.dueBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + this.dueBy + ")";
    }
}
