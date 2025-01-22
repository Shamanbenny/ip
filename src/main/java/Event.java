/**
 * Represents a task that occurs during a specific time range.
 * Extends the base Task class.
 *
 * @author ShamanBenny
 * @version 4.5
 */
public class Event extends Task {
    private final String startTime;
    private final String endTime;

    /**
     * Constructs a new Event task with the specified description, completion status, start time, and end time.
     *
     * @param description A brief description of the task.
     * @param isCompleted The initial completion status of the task (true if completed, false otherwise).
     * @param startTime   The start time of the event as a string.
     * @param endTime     The end time of the event as a string.
     */
    public Event(String description, boolean isCompleted, String startTime, String endTime) {
        super(description, isCompleted);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: " + this.startTime + ", To: " + this.endTime + ")";
    }
}