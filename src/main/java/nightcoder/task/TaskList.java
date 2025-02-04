package nightcoder.task;

import nightcoder.storage.Storage;
import nightcoder.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Represents the TaskList class that manages a collection of tasks, including To-Do tasks, Deadlines, and Events.
 * It supports adding, listing, and formatting tasks with associated dates.
 * The class interacts with storage object and UI class to persist and display tasks respectively.
 *
 * @author ShamanBenny
 * @version 8.1
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private final Storage STORAGE;
    private final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs a TaskList with a given storage.
     *
     * @param storage The storage handler for tasks.
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.STORAGE = storage;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * @param index Index of the element to return
     * @return The element at the specified position in this list
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index The index of the element to be removed
     * @return The element that was removed from the list
     */
    public Task remove(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Loads tasks from the storage file and converts them into {@code ArrayList<Task>}.
     */
    public void loadTasks() {
        this.tasks = this.STORAGE.loadTasks();
    }

    /**
     * Parses a date string in the format "yyyy-MM-dd" and returns it in ISO format.
     * If the input cannot be parsed, the original input string is returned unchanged.
     *
     * @param input The date string to be parsed, expected in "yyyy-MM-dd" format.
     * @return A string representation of the parsed date in ISO format, or the original input string if parsing fails.
     */
    public String parseDate(String input) {
        LocalDate output;

        try {
            output = LocalDate.parse(input, INPUT_DATE_FORMAT);
            return output.format(OUTPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return input;
        }
    }

    /**
     * Adds a To Do to the list of tasks. By default, a newly added To Do is not completed.
     *
     * @param description The description of the task to be added to the list.
     */
    public void addToDo(String description) {
        Task task = new ToDo(description, false);
        this.tasks.add(task);
        try {
            this.STORAGE.appendTask("T|0|" + description);
            Ui.printTaskAdded(description, this.size());
        } catch (IOException e) {
            Ui.printIndentedLine("[ Task #" + this.size() + " Added: " + description + " ]");
            Ui.printErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Adds a Deadline to the list of tasks. By default, a newly added deadline is not completed.
     * Requires information on due date.
     *
     * @param description The description of the task to be added to the list.
     * @param dueBy A string detailing when the task is due by.
     */
    public void addDeadline(String description, String dueBy) {
        String parsedDueBy = parseDate(dueBy);

        Task task = new Deadline(description, false, parsedDueBy);
        this.tasks.add(task);
        try {
            this.STORAGE.appendTask("D|0|" + description + "|" + parsedDueBy);
            Ui.printTaskAdded(description, this.size());
        } catch (IOException e) {
            Ui.printIndentedLine("[ Task #" + this.size() + " Added: " + description + " ]");
            Ui.printErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Adds an Event to the list of tasks. By default, a newly added event is not completed.
     * Requires information on start and end time.
     *
     * @param description The description of the task to be added to the list.
     * @param startTime A string detailing when the event starts.
     * @param endTime A string detailing when the event ends.
     */
    public void addEvent(String description, String startTime, String endTime) {
        String parsedStartTime = parseDate(startTime);
        String parsedEndTime = parseDate(endTime);

        Task task = new Event(description, false, parsedStartTime, parsedEndTime);
        this.tasks.add(task);
        try {
            this.STORAGE.appendTask("E|0|" + description + "|" + parsedStartTime + "|" + parsedEndTime);
            Ui.printTaskAdded(description, this.size());
        } catch (IOException e) {
            Ui.printIndentedLine("[ Task #" + this.size() + " Added: " + description + " ]");
            Ui.printErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Displays the list of tasks currently stored along with their indices.
     * If the list is empty, a message indicating no tasks are available is shown.
     */
    public void listTasks() {
        if (this.tasks.isEmpty()) {
            Ui.printIndentedLine("[ Your To-Do List is Empty! ]");
            Ui.printIndentedLine("Looks like we're starting with a clean slate. What shall we tackle first?");
        } else {
            for (int idx = 0; idx < this.size(); idx++) {
                Task task = this.tasks.get(idx);
                Ui.printIndentedLine((idx+1) + "." + task);
            }
        }
    }

    /**
     * Saves the current list of tasks to the designated storage file.
     * This method should be called at the end of the application to ensure that all tasks are properly backed up.
     */
    public void saveTasks() {
        ArrayList<String> lines = new ArrayList<>();
        for (Task task : this.tasks) {
            lines.add(task.getStringFormat());
        }
        try {
            this.STORAGE.writeLines(lines);
        } catch (IOException e) {
            Ui.printErrorUpdatingTasksFile(e);
        }
    }
}
