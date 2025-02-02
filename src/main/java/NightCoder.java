import java.io.IOException;
import java.util.Scanner;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 8
 */
public class NightCoder {
    private static final String DATA_FOLDER = "data";
    private static final String TASKS_FILE = "tasks.txt";
    private static Scanner SCANNER;
    private static final Storage STORAGE = new Storage(NightCoder.DATA_FOLDER, NightCoder.TASKS_FILE);
    private static final TaskList TASKS = new TaskList(NightCoder.STORAGE);

    /**
     * Reads user input from the console and processes it as a command.
     * If the input is "bye" (case-insensitive), the method exits the loop by returning false.
     * Otherwise, it passes the input to {@link #parseCommand(String)} for further processing.
     *
     * @return {@code false} if the input is "bye", {@code true} otherwise.
     */
    private static boolean getUserInput() {
        String userInput = NightCoder.SCANNER.nextLine().trim(); // Trim to remove extra spaces

        if ("bye".equalsIgnoreCase(userInput)) {
            return false; // Return false if the input is "bye"
        }
        parseCommand(userInput);
        return true; // Return true for other inputs
    }

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param input The full user input string to be parsed and processed.
     */
    private static void parseCommand(String input) {
        System.out.println(Ui.LINE_BREAK);

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        // The switch structure is retained for clarity and readability. Each case acts as a self-contained command
        // handler, making the code easy to navigate without needing separate function calls for every command.
        switch (command) {
        case "help":
            Ui.printHelp();
            break;
        case "todo":
            if (parts.length != 2) {
                Ui.printInvalidUsage("todo");
                break;
            }
            String todoParams = parts[1];
            NightCoder.TASKS.addToDo(todoParams);
            break;
        case "deadline":
            if (parts.length != 2) {
                Ui.printInvalidUsage("deadline");
                break;
            }
            String deadlineParams = parts[1];
            if (!deadlineParams.contains(" /by ")) {
                // Missing "/by"
                Ui.printInvalidUsage("deadline");
                break;
            }
            String[] deadlineParts = deadlineParams.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[1].isEmpty()) {
                // Missing details for task description, "/by", or empty "/by" details.
                Ui.printInvalidUsage("deadline");
                break;
            }
            // Correct Usage from here...
            String deadlineDescription = deadlineParts[0];
            String deadlineBy = deadlineParts[1];
            NightCoder.TASKS.addDeadline(deadlineDescription, deadlineBy);
            break;
        case "event":
            if (parts.length != 2) {
                Ui.printInvalidUsage("event");
                break;
            }
            String eventParams = parts[1];
            if (!eventParams.contains(" /from ") || !eventParams.contains(" /to ")) {
                // Missing "/from" or "/to"
                Ui.printInvalidUsage("event");
                break;
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
            if (eventDescription.isEmpty() || fromParams.isEmpty() || toParams.isEmpty()) {
                Ui.printInvalidUsage("event");
                break;
            }
            // Correct Usage from here...
            NightCoder.TASKS.addEvent(eventDescription, fromParams, toParams);
            break;
        case "list":
            NightCoder.TASKS.listTasks();
            break;
        case "mark":
            if (parts.length != 2) {
                Ui.printInvalidUsage("mark");
                break;
            }
            try {
                // Attempt to parse the task ID
                int markId = Integer.parseInt(parts[1]);
                setCompleted(markId, true);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                Ui.printInvalidNumberInput("mark");
            }
            break;
        case "unmark":
            if (parts.length != 2) {
                Ui.printInvalidUsage("unmark");
                break;
            }
            try {
                // Attempt to parse the task ID
                int unmarkId = Integer.parseInt(parts[1]);
                setCompleted(unmarkId, false);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                Ui.printInvalidNumberInput("unmark");
            }
            break;
        case "delete":
            if (parts.length != 2) {
                Ui.printInvalidUsage("delete");
                break;
            }
            try {
                // Attempt to parse the task ID
                int deleteId = Integer.parseInt(parts[1]);
                deleteTask(deleteId);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                Ui.printInvalidNumberInput("delete");
            }
            break;
        default:
            System.out.println("""
                    \t[ Oops! ]
                    \tI didn't catch that. Type "help" to see the list of commands I understand.
                    \tLet's get back on track!""");
        }

        System.out.println(Ui.LINE_BREAK + "\n");
    }

    /**
     * Updates the completion status of a task in the to-do list.
     * Marks a specified task as completed or incomplete based on the given parameters.
     * If the provided index is invalid (not in the range of the task list), it displays an error message.
     * If the task selected is already set as it should, it also displays an error message.
     *
     * @param idx The 1-based index of the task in the list to update.
     * @param completed {@code true} if the task is completed, {@code false} otherwise.
     */
    private static void setCompleted(int idx, boolean completed) {
        // Edge-Case ['idx' out of bounds]
        if (idx > NightCoder.TASKS.size() || idx < 1) {
            System.out.println("""
                    \t[ Invalid Task Number! ]
                    \tHmm, that number doesn't match any tasks on your list.
                    \tDouble-check your task list with "list", and try again!""");
            return;
        }

        // idx is originally 1-indexed [Therefore minus 1 to access 0-indexed ListArray]
        Task task = NightCoder.TASKS.get(idx - 1);
        if (task.isCompleted() == completed) {
            // Edge-Case ['task' is already set as complete/incomplete]
            if (completed) {
                System.out.println("\t[ Task Already Complete! ]");
                System.out.println("\tLooks like task \"" + task.getDescription() + "\" is already marked as done. " +
                        "You're ahead of the game!");
            } else {
                System.out.println("\t[ Task Already Incomplete ]!");
                System.out.println("\tTask \"" + task.getDescription() + "\" is already on your to-do list. " +
                        "No need to unmark it again!");
            }
        } else {
            task.setCompleted(completed);
            try {
                NightCoder.STORAGE.setCompleted(idx - 1, completed); // Convert to zero-based index
                if (completed) {
                    System.out.println("\t[ Task Marked as Complete! ]");
                    System.out.println("\tGreat job! Task \"" + task.getDescription() + "\" is now marked as done. " +
                            "On to the next one!");
                } else {
                    System.out.println("\t[ Task Marked as Incomplete! ]");
                    System.out.println("\tGot it! Task \"" + task.getDescription() + "\" is back on your to-do list. " +
                            "Let's tackle it when you're ready!");
                }
            } catch (IOException e) {
                if (completed) {
                    System.out.println("\t[ Task Marked as Complete! ]");
                } else {
                    System.out.println("\t[ Task Marked as Incomplete! ]");
                }
                Ui.printErrorUpdatingTasksFile(e);
            }
        }
    }

    /**
     * Deletes a task from the to-do list based on its 1-based index.
     * This method removes a task from the task list if the specified index is valid.
     * If the index is out of bounds, an error message is displayed, and no task is deleted.
     *
     * @param idx The 1-based index of the task in the list to delete.
     */
    private static void deleteTask(int idx) {
        // Edge-Case ['idx' out of bounds]
        if (idx > NightCoder.TASKS.size() || idx < 1) {
            System.out.println("""
                    \t[ Invalid Task Number! ]
                    \tHmm, that number doesn't match any tasks on your list.
                    \tDouble-check your task list with "list", and try again!""");
            return;
        }

        // idx is originally 1-indexed [Therefore minus 1 to access 0-indexed ListArray]
        Task task = NightCoder.TASKS.remove(idx - 1);
        try {
            NightCoder.STORAGE.deleteTask(idx - 1);
        } catch (IOException e) {
            System.out.println("\t[ Task Deleted! ]");
            Ui.printErrorUpdatingTasksFile(e);
        }
        System.out.println("\t[ Task Deleted! ]");
        System.out.println("\tTask \"" + task.getDescription() + "\" has been removed from your list. " +
                "Poof, it's gone! Let me know if there's anything else to tidy up.");
    }

    /**
     * The main entry point of the program. Initializes the application, loads tasks from storage,
     * and starts the user input loop until termination.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Ui.printWelcome();
        NightCoder.TASKS.loadTasks();
        try (Scanner scannerInstance = new Scanner(System.in)) {
            NightCoder.SCANNER = scannerInstance;
            while (true) {
                if (!getUserInput()) {
                    break;
                }
            }
        }
        NightCoder.TASKS.saveTasks();
        Ui.printExit();
    }
}
