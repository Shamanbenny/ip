/**
 * This class encapsulates a task's details, including its description and
 * whether it has been completed, providing methods to manage and retrieve
 * task information.
 *
 * @author ShamanBenny
 * @version 5.0
 */
public class Task {
    private final String description;
    private boolean isCompleted;

    /**
     * Constructs a new Task with the specified description and completion status.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     */
    public Task(String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param isCompleted true to mark the task as completed, false to mark it as incomplete.
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Returns a string representation of the task, including its status and description.
     * The format is:
     * - `[X] <description>` for completed tasks.
     * - `[ ] <description>` for incomplete tasks.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return (this.isCompleted ? "[X] ":"[ ] ") + this.description;
    }
}