package nightcoder.task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nightcoder.storage.Storage;
import nightcoder.ui.Ui;

/**
 * Represents the TaskList class that manages a collection of tasks, including To-Do tasks, Deadlines, and Events.
 * It supports adding, listing, and formatting tasks with associated dates.
 * The class interacts with storage object and UI class to persist and display tasks respectively.
 *
 * @author ShamanBenny
 * @version 10
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private HashMap<String, Task> taskLookup; // HashMap for quick lookup for duplicate tasks
    private final Storage storage;
    private final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs a TaskList with a given storage.
     *
     * @param storage The storage handler for tasks.
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.taskLookup = new HashMap<>();
        this.storage = storage;
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
        Task removedTask = this.tasks.remove(index);
        this.taskLookup.remove(removedTask.getDescription().toLowerCase());
        return removedTask;
    }

    /**
     * Loads tasks from the storage file and converts them into {@code ArrayList<Task>}.
     */
    public void loadTasks() {
        this.tasks = this.storage.loadTasks();
        for (Task task : this.tasks) {
            this.taskLookup.put(task.getDescription().toLowerCase(), task); // Populate HashMap
        }
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
     * @return The String message indicating the attempt of adding the To Do task.
     */
    public String addToDo(String description) {
        if (this.taskLookup.containsKey(description.toLowerCase())) {
            return "[ Duplicate Task ]\nTask already exists: " + description;
        }

        Task task = new ToDo(description, false);
        this.tasks.add(task);
        this.taskLookup.put(description.toLowerCase(), task);
        try {
            this.storage.appendTask("T|0|" + description);
            return Ui.getTaskAdded(description, this.size());
        } catch (IOException e) {
            return "[ Task #" + this.size() + " Added: " + description + " ]\n" + Ui.getErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Adds a Deadline to the list of tasks. By default, a newly added deadline is not completed.
     * Requires information on due date.
     *
     * @param description The description of the task to be added to the list.
     * @param dueBy A string detailing when the task is due by.
     * @return The String message indicating the attempt of adding the Deadline task.
     */
    public String addDeadline(String description, String dueBy) {
        if (this.taskLookup.containsKey(description.toLowerCase())) {
            return "[ Duplicate Task ]\nTask already exists: " + description;
        }

        String parsedDueBy = parseDate(dueBy);
        Task task = new Deadline(description, false, parsedDueBy);
        this.tasks.add(task);
        this.taskLookup.put(description.toLowerCase(), task);
        try {
            this.storage.appendTask("D|0|" + description + "|" + parsedDueBy);
            return Ui.getTaskAdded(description, this.size());
        } catch (IOException e) {
            return "[ Task #" + this.size() + " Added: " + description + " ]\n" + Ui.getErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Adds an Event to the list of tasks. By default, a newly added event is not completed.
     * Requires information on start and end time.
     *
     * @param description The description of the task to be added to the list.
     * @param startTime A string detailing when the event starts.
     * @param endTime A string detailing when the event ends.
     * @return The String message indicating the attempt of adding the Event task.
     */
    public String addEvent(String description, String startTime, String endTime) {
        if (this.taskLookup.containsKey(description.toLowerCase())) {
            return "[ Duplicate Task ]\nTask already exists: " + description;
        }

        String parsedStartTime = parseDate(startTime);
        String parsedEndTime = parseDate(endTime);
        Task task = new Event(description, false, parsedStartTime, parsedEndTime);
        this.tasks.add(task);
        this.taskLookup.put(description.toLowerCase(), task);
        try {
            this.storage.appendTask("E|0|" + description + "|" + parsedStartTime + "|" + parsedEndTime);
            return Ui.getTaskAdded(description, this.size());
        } catch (IOException e) {
            return "[ Task #" + this.size() + " Added: " + description + " ]\n" + Ui.getErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Displays the list of tasks currently stored along with their indices.
     * If the list is empty, a message indicating no tasks are available is shown.
     *
     * @return The String message of the list of tasks along with their indices.
     */
    public String listTasks() {
        if (this.tasks.isEmpty()) {
            return "[ Your To-Do List is Empty! ]\n"
                    + "Looks like we're starting with a clean slate. What shall we tackle first?";
        }
        // Functional Programming using Streams learnt from CS2030S
        return IntStream.range(0, tasks.size())
                .mapToObj(idx -> (idx + 1) + "." + tasks.get(idx))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Displays the list of tasks currently stored along with their indices containing the specific keyword.
     * If the list is empty, a message indicating no tasks are available is shown.
     *
     * @return The String message of the list of tasks along with their indices.
     */
    public String listTasks(String keyword) {
        if (this.tasks.isEmpty()) {
            return "[ Your To-Do List is Empty! ]\n"
                    + "Looks like we're starting with a clean slate. What shall we tackle first?";
        }
        // Functional Programming using Streams learnt from CS2030S
        String result = IntStream.range(0, tasks.size())
                .filter(idx -> tasks.get(idx).getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .mapToObj(idx -> (idx + 1) + "." + tasks.get(idx))
                .collect(Collectors.joining("\n"));
        return result.isEmpty()
                ? "[ No match found! ]\nLooks like the tasks you're trying to find doesn't exist. Anything else?"
                : result;
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
            this.storage.writeLines(lines);
        } catch (IOException e) {
            Ui.getErrorUpdatingTasksFile(e);
        }
    }
}
