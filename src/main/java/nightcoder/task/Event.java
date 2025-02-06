package nightcoder.task;

/**
 * Represents a task that occurs during a specific time range.
 * Extends the base Task class.
 *
 * @author ShamanBenny
 * @version 8.1
 */
public class Event extends Task {
    private final String START_TIME;
    private final String END_TIME;

    /**
     * Constructs a new Event task with the specified description, completion status, start time, and end time.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     * @param startTime The start time of the event as a string.
     * @param endTime The end time of the event as a string.
     */
    public Event(String description, boolean isCompleted, String startTime, String endTime) {
        super(description, isCompleted);
        this.START_TIME = startTime;
        this.END_TIME = endTime;
    }

    public String getStartTime() {
        return this.START_TIME;
    }

    public String getEndTime() {
        return this.END_TIME;
    }

    /**
     * Returns a formatted string representation of the task.
     *
     * @return A string formatted as "T|completion_status|description|start_time|end_time".
     */
    public String getStringFormat() {
        return "E|" + (isCompleted() ? "1" : "0") + "|" + getDescription() + "|" + getStartTime() + "|" + getEndTime();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: " + this.START_TIME + ", To: " + this.END_TIME + ")";
    }
}
