package nightcoder.parser;

import java.io.IOException;

import nightcoder.storage.Storage;
import nightcoder.task.Task;
import nightcoder.task.TaskList;
import nightcoder.ui.Ui;

/**
 * Handles user input, processes commands, and manages task-related operations.
 *
 * @author ShamanBenny
 * @version 10
 */
public class Parser {
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a {@code Parser} with the necessary dependencies.
     *
     * @param storage The {@code Storage} instance to handle task persistence.
     * @param tasks   The {@code TaskList} containing the list of tasks.
     */
    public Parser(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param input The full user input string to be parsed and processed.
     * @return The String response of the parsed input command.
     */
    public String parseCommand(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        // The switch structure is retained for clarity and readability. Each case acts as a self-contained command
        // handler, making the code easy to navigate without needing separate function calls for every command.
        switch (command) {
        case "help":
            return Ui.getHelp();
        case "todo":
            if (parts.length != 2 || parts[1].trim().isEmpty()) {
                return Ui.getInvalidUsage("todo");
            }
            String todoParams = parts[1];
            return this.tasks.addToDo(todoParams.trim());
        case "deadline":
            return parseDeadline(parts);
        case "event":
            return parseEvent(parts);
        case "list":
            return this.tasks.listTasks();
        case "mark":
            if (parts.length != 2) {
                return Ui.getInvalidUsage("mark");
            }
            try {
                // Attempt to parse the task ID
                int markId = Integer.parseInt(parts[1]);
                return setCompleted(markId, true);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                return Ui.getInvalidNumberInput("mark");
            }
        case "unmark":
            if (parts.length != 2) {
                return Ui.getInvalidUsage("unmark");
            }
            try {
                // Attempt to parse the task ID
                int unmarkId = Integer.parseInt(parts[1]);
                return setCompleted(unmarkId, false);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                return Ui.getInvalidNumberInput("unmark");
            }
        case "delete":
            if (parts.length != 2) {
                return Ui.getInvalidUsage("delete");
            }
            try {
                // Attempt to parse the task ID
                int deleteId = Integer.parseInt(parts[1]);
                return deleteTask(deleteId);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                return Ui.getInvalidNumberInput("delete");
            }
        case "find":
            if (parts.length != 2) {
                return Ui.getInvalidUsage("find");
            }
            String findParams = parts[1];
            return this.tasks.listTasks(findParams);
        default:
            return """
                    [ Oops! ]
                    I didn't catch that. Type "help" to see the list of commands I understand.
                    Let's get back on track!""";
        }
    }

    /**
     * Parses and processes the command for adding a deadline task.
     * If the format is incorrect, it returns an error message.
     *
     * @param parts The command split into parts, where `parts[0]` is "deadline".
     * @return The String message indicating the result of parsing and adding the deadline task.
     */
    private String parseDeadline(String[] parts) {
        if (parts.length != 2) {
            return Ui.getInvalidUsage("deadline");
        }
        String deadlineParams = parts[1];
        if (!deadlineParams.contains(" /by ")) {
            // Missing "/by"
            return Ui.getInvalidUsage("deadline");
        }
        String[] deadlineParts = deadlineParams.split(" /by ", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            // Missing details for task description, "/by", or empty "/by" details.
            return Ui.getInvalidUsage("deadline");
        }
        // Correct Usage from here...
        String deadlineDescription = deadlineParts[0];
        String deadlineBy = deadlineParts[1];
        return this.tasks.addDeadline(deadlineDescription.trim(), deadlineBy.trim());
    }

    /**
     * Parses and processes the command for adding an event task.
     * If the format is incorrect, it returns an error message.
     *
     * @param parts The command split into parts, where `parts[0]` is "event".
     * @return The String message indicating the result of parsing and adding the event task.
     */
    private String parseEvent(String[] parts) {
        if (parts.length != 2) {
            return Ui.getInvalidUsage("event");
        }
        String eventParams = parts[1];
        if (!eventParams.contains(" /from ") || !eventParams.contains(" /to ")) {
            // Missing "/from" or "/to"
            return Ui.getInvalidUsage("event");
        }
        int fromIdx = eventParams.indexOf(" /from ");
        int toIdx = eventParams.indexOf(" /to ");
        String eventDescription = eventParams.split(" /from | /to ", 2)[0];
        String fromParams = "";
        String toParams = "";
        if (fromIdx < toIdx) {
            fromParams = eventParams.substring(fromIdx + 7, toIdx);
            toParams = eventParams.substring(toIdx + 5);
        } else {
            fromParams = eventParams.substring(fromIdx + 7);
            toParams = eventParams.substring(toIdx + 5, fromIdx);
        }
        if (eventDescription.trim().isEmpty() || fromParams.trim().isEmpty() || toParams.trim().isEmpty()) {
            return Ui.getInvalidUsage("event");
        }
        // Correct Usage from here...
        return this.tasks.addEvent(eventDescription.trim(), fromParams.trim(), toParams.trim());
    }

    /**
     * Updates the completion status of a task in the to-do list.
     * Marks a specified task as completed or incomplete based on the given parameters.
     * If the provided index is invalid (not in the range of the task list), it displays an error message.
     * If the task selected is already set as it should, it also displays an error message.
     *
     * @param idx The 1-based index of the task in the list to update.
     * @param isCompleted {@code true} if the task is completed, {@code false} otherwise.
     * @return The String message indicating the attempt at updating the completion status of a task.
     */
    private String setCompleted(int idx, boolean isCompleted) {
        // Edge-Case ['idx' out of bounds]
        if (idx > this.tasks.size() || idx < 1) {
            return """
                    [ Invalid Task Number! ]
                    Hmm, that number doesn't match any tasks on your list.
                    Double-check your task list with "list", and try again!""";
        }

        StringBuilder output = new StringBuilder();
        // idx is originally 1-indexed [Therefore minus 1 to access 0-indexed ListArray]
        Task task = this.tasks.get(idx - 1);
        if (task.isCompleted() == isCompleted) {
            output.append(getAlreadySetMessage(task, isCompleted));
        } else {
            output.append(updateTaskCompletion(task, idx, isCompleted));
        }
        return output.toString();
    }

    /**
     * Returns the message when the task is already set to the desired completion status.
     *
     * @param task The task whose status is being checked.
     * @param isCompleted {@code true} if checking for already completed status, {@code false} otherwise.
     * @return The String message indicating that the task is already in the desired state.
     */
    private String getAlreadySetMessage(Task task, boolean isCompleted) {
        if (isCompleted) {
            return "[ Task Already Complete! ]\nLooks like task \"" + task.getDescription()
                    + "\" is already marked as done. You're ahead of the game!";
        } else {
            return "[ Task Already Incomplete! ]\nTask \"" + task.getDescription()
                    + "\" is already on your to-do list. No need to unmark it again!";
        }
    }

    /**
     * Updates the task completion status and returns the corresponding message.
     * Attempts to persist the updated status to storage. If an error occurs,
     * it appends the error message.
     *
     * @param task The task to update.
     * @param idx The 1-based index of the task in the task list.
     * @param isCompleted {@code true} to mark as completed, {@code false} to mark as incomplete.
     * @return The String message indicating the result of updating the task completion status.
     */
    private String updateTaskCompletion(Task task, int idx, boolean isCompleted) {
        StringBuilder output = new StringBuilder();
        task.setCompleted(isCompleted);

        try {
            this.storage.setCompleted(idx - 1, isCompleted); // Convert to zero-based index
            output.append(isCompleted ? "[ Task Marked as Complete! ]\nGreat job! Task \""
                    : "[ Task Marked as Incomplete! ]\nGot it! Task \"");
            output.append(task.getDescription()).append(isCompleted ? "\" is now marked as done. On to the next one!"
                    : "\" is back on your to-do list. Let's tackle it when you're ready!");
        } catch (IOException e) {
            output.append(isCompleted ? "[ Task Marked as Complete! ]\n"
                    : "[ Task Marked as Incomplete! ]\n");
            output.append(Ui.getErrorUpdatingTasksFile(e));
        }

        return output.toString();
    }

    /**
     * Deletes a task from the to-do list based on its 1-based index.
     * This method removes a task from the task list if the specified index is valid.
     * If the index is out of bounds, an error message is displayed, and no task is deleted.
     *
     * @param idx The 1-based index of the task in the list to delete.
     * @return The String message indicating the attempt at deleting a task from the to-do list.
     */
    private String deleteTask(int idx) {
        // Edge-Case ['idx' out of bounds]
        if (idx > this.tasks.size() || idx < 1) {
            return """
                    [ Invalid Task Number! ]
                    Hmm, that number doesn't match any tasks on your list.
                    Double-check your task list with "list", and try again!""";
        }

        // idx is originally 1-indexed [Therefore minus 1 to access 0-indexed ListArray]
        Task task = this.tasks.remove(idx - 1);
        try {
            this.storage.deleteTask(idx - 1);
        } catch (IOException e) {
            return "[ Task Deleted! ]\n" + Ui.getErrorUpdatingTasksFile(e);
        }
        return "[ Task Deleted! ]\nTask \"" + task.getDescription() + "\" has been removed from your list. "
                + "Poof, it's gone! Let me know if there's anything else to tidy up.";
    }
}
