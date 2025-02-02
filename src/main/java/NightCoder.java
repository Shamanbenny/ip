import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 7
 */
public class NightCoder {
    private static final String DATA_FOLDER = "data";
    private static final String TASKS_FILE = "tasks.txt";
    private static final String LINE_BREAK =
            "\t______________________________________________________________________________________________";
    private static Scanner SCANNER;
    private static final ArrayList<Task> TASKS = new ArrayList<>();

    /**
     * Represents the commands supported by the NightCoder application.
     * Each command is associated with a syntax and a description to provide
     * help and guidance for users.
     */
    private enum CommandHelp {
        HELP("help", "Prints this handy guide. Because even pros need reminders sometimes."),
        TODO("todo <String>", "Adds a to-do task to your list. Just tell me what needs doing, " +
                "and I'll keep track.\n\t      Example: todo Finish the project report"),
        DEADLINE("deadline <String> /by <String>", "Adds a task with a deadline. Perfect for those " +
                "time-sensitive missions!\n\t      Example: deadline Submit assignment /by 2025-01-30 23:59"),
        EVENT("event <String> /from <String> /to <String>", "Adds an event with a start and end time. " +
                "Keep your schedule sharp!\n\t      Example: event Team meeting /from 2025-01-21 3:00 PM " +
                "/to 2025-01-21 4:00 PM"),
        LIST("list", "Shows all your tasks. Think of it as your personal task constellation."),
        MARK("mark <int>", "Marks a task as complete. Use the task number from the list.\n" +
                "\t      Example: mark 1"),
        UNMARK("unmark <int>", "Marks a task as incomplete. Sometimes things need a second look!\n" +
                "\t      tExample: unmark 1"),
        DELETE("delete <int>", "Deletes a task from your to-do list. Use the task number from the list.\n" +
                "\t      Example: delete 2"),
        BYE("bye", "Exits the program. But don't be a stranger-I'll be here when you need me again!");

        private final String syntax;
        private final String description;

        /**
         * Constructs a CommandHelp enum instance with the specified syntax and description.
         *
         * @param syntax      The syntax of the command.
         * @param description The description of the command.
         */
        CommandHelp(String syntax, String description) {
            this.syntax = syntax;
            this.description = description;
        }

        public String getSyntax() {
            return syntax;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Prints the welcome message along with an ASCII art logo.
     */
    private static void printWelcome() {
        String logo = """
                \t  /$$   /$$ /$$           /$$         /$$      /$$$$$$                  /$$                   \s
                \t | $$$ | $$|__/          | $$        | $$     /$$__  $$                | $$                   \s
                \t | $$$$| $$ /$$  /$$$$$$ | $$$$$$$  /$$$$$$  | $$  \\__/  /$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$\s
                \t | $$ $$ $$| $$ /$$__  $$| $$__  $$|_  $$_/  | $$       /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$
                \t | $$  $$$$| $$| $$  \\ $$| $$  \\ $$  | $$    | $$      | $$  \\ $$| $$  | $$| $$$$$$$$| $$  \\__/
                \t | $$\\  $$$| $$| $$  | $$| $$  | $$  | $$ /$$| $$    $$| $$  | $$| $$  | $$| $$_____/| $$     \s
                \t | $$ \\  $$| $$|  $$$$$$$| $$  | $$  |  $$$$/|  $$$$$$/|  $$$$$$/|  $$$$$$$|  $$$$$$$| $$     \s
                \t |__/  \\__/|__/ \\____  $$|__/  |__/   \\___/   \\______/  \\______/  \\_______/ \\_______/|__/    \s
                \t                /$$  \\ $$                                                                     \s
                \t               |  $$$$$$/                                                                     \s
                \t                \\______/                                                                      \s
                """;
        System.out.println("\n" + NightCoder.LINE_BREAK + "\n\n" + logo);
        System.out.println("\tBooting Up...\n");
        System.out.println("""
                \tAh, there you are! The moon is bright, the code is flowing, and caffeine-wait, I mean
                \tmotivation-fuels our mission tonight. Welcome back to Night Coder, your loyal (and slightly
                \tsleep-deprived) coding companion. Whether it's wrangling deadlines, or organizing your todo
                \tlist, I'm here to lend a hand.""");
        System.out.println("\n\tLet's make some magic together. What's on the docket tonight?");
        System.out.println("\n\tIf you're unsure about what I can do, just type \"help\", and I'll get you " +
                "sorted in no time!");
        System.out.println(NightCoder.LINE_BREAK + "\n");
    }

    /**
     * Prints a message indicating a task has been added.
     *
     * @param description The description of the newly added task.
     */
    private static void printTaskAdded(String description) {
        System.out.println("\t[ Task #" + NightCoder.TASKS.size() + " Added: " + description + " ]");
        System.out.println("\tGot it! I'll keep this safe in your to-do list. Let me know what's next!");
    }

    /**
     * Prints an error message for incorrect command usage.
     *
     * @param command The name of the command for which the usage was invalid.
     */
    private static void printInvalidUsage(String command) {
        System.out.println("""
                \t[ Oops! ]
                \tIncorrect usage of""" + " \"" + command + "\"" + """
                . Type "help" to refer to its appropriate usage. Let's get back on track!""");
    }

    /**
     * Prints an error message for invalid input that resulted in Number Format Exception.
     *
     * @param command The name of the command for which the input was invalid.
     */
    private static void printInvalidNumberInput(String command) {
        System.out.println("""
                \t[ Invalid Usage! ]
                \tHmm, please enter a number that matches one of your tasks on the list.
                \tDouble-check your task list with "list", and try again!""");
        System.out.println("\tExample: " + command + " 1");
    }

    /**
     * Prints an error message regarding the IOException that occurred during the updating of Tasks Files.
     *
     * @param e The IOException that occurred.
     */
    private static void printErrorUpdatingTasksFile(IOException e) {
        System.err.println("Error writing to tasks file: " + e.getMessage());
        System.out.println("\t[ ERROR ] It appears that the updating of the tasks data file has failed.");
        System.out.println("\t          This may result in non-persistent task tracking if not addressed properly.");
        System.out.println("\t[ LOG ]   " + e.getMessage());
    }

    /**
     * Prints a detailed guide of all available commands in the NightCoder application.
     * The guide includes the syntax and description for each command, making it easy for users
     * to understand and use the application effectively.
     */
    private static void printHelp() {
        System.out.println("\t[ Night Code Command Guide ]");
        System.out.println("\tNeed a hand? No problem! Here's what I can do for you:\n");

        for (CommandHelp cmd : CommandHelp.values()) {
            System.out.println("\t    " + cmd.getSyntax());
            System.out.println("\t    - " + cmd.getDescription() + "\n");
        }

        System.out.println("\tGot it? Let's get back to work!");
    }

    /**
     * Displays a farewell message before exiting the application
     */
    private static void printExit() {
        System.out.println(NightCoder.LINE_BREAK);
        System.out.println("""
                
                \tAlright, signing off for now. Remember, even the brightest coders need some rest-yes, I'm
                \tlooking at you! ( 0 w 0 )""");
        System.out.println("""
                \tIf you need me, you know where to find me. Until next time, keep dreaming big, debugging smart,
                \tand chasing that moonlit inspiration.""");
        System.out.println("\tGoodnight, and happy coding!");
        System.out.println("\n\tPowering Down...");
        System.out.println(NightCoder.LINE_BREAK);
    }

    /**
     * Loads saved tasks from a file and eventually restores them into {@code NightCoder.TASKS}
     */
    private static void loadTasksFromData() {
        ArrayList<String> lines = readLinesFromTasksFile();
        for (String line : lines) {
            parseAndAddTask(line);
        }
        if (NightCoder.TASKS.isEmpty()) {
            System.out.println("\t[ INFO ] No previous task data found. Starting fresh!");
        } else {
            System.out.println("\t[ INFO ] Tasks loaded successfully from previous sessions!");
        }
        System.out.println(NightCoder.LINE_BREAK + "\n");
    }

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
        System.out.println(NightCoder.LINE_BREAK);

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        // The switch structure is retained for clarity and readability. Each case acts as a self-contained command
        // handler, making the code easy to navigate without needing separate function calls for every command.
        switch (command) {
        case "help":
            printHelp();
            break;
        case "todo":
            if (parts.length != 2) {
                printInvalidUsage("todo");
                break;
            }
            String todoParams = parts[1];
            addToDo(todoParams);
            break;
        case "deadline":
            if (parts.length != 2) {
                printInvalidUsage("deadline");
                break;
            }
            String deadlineParams = parts[1];
            if (!deadlineParams.contains(" /by ")) {
                // Missing "/by"
                printInvalidUsage("deadline");
                break;
            }
            String[] deadlineParts = deadlineParams.split(" /by ", 2);
            if (deadlineParts.length < 2 || deadlineParts[1].isEmpty()) {
                // Missing details for task description, "/by", or empty "/by" details.
                printInvalidUsage("deadline");
                break;
            }
            // Correct Usage from here...
            String deadlineDescription = deadlineParts[0];
            String deadlineBy = deadlineParts[1];
            addDeadline(deadlineDescription, deadlineBy);
            break;
        case "event":
            if (parts.length != 2) {
                printInvalidUsage("event");
                break;
            }
            String eventParams = parts[1];
            if (!eventParams.contains(" /from ") || !eventParams.contains(" /to ")) {
                // Missing "/from" or "/to"
                printInvalidUsage("event");
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
                printInvalidUsage("event");
                break;
            }
            // Correct Usage from here...
            addEvent(eventDescription, fromParams, toParams);
            break;
        case "list":
            listTasks();
            break;
        case "mark":
            if (parts.length != 2) {
                printInvalidUsage("mark");
                break;
            }
            try {
                // Attempt to parse the task ID
                int markId = Integer.parseInt(parts[1]);
                setCompleted(markId, true);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                printInvalidNumberInput("mark");
            }
            break;
        case "unmark":
            if (parts.length != 2) {
                printInvalidUsage("unmark");
                break;
            }
            try {
                // Attempt to parse the task ID
                int unmarkId = Integer.parseInt(parts[1]);
                setCompleted(unmarkId, false);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                printInvalidNumberInput("unmark");
            }
            break;
        case "delete":
            if (parts.length != 2) {
                printInvalidUsage("delete");
                break;
            }
            try {
                // Attempt to parse the task ID
                int deleteId = Integer.parseInt(parts[1]);
                deleteTask(deleteId);
            } catch (NumberFormatException e) {
                // Handle invalid input for task ID
                printInvalidNumberInput("delete");
            }
            break;
        default:
            System.out.println("""
                    \t[ Oops! ]
                    \tI didn't catch that. Type "help" to see the list of commands I understand.
                    \tLet's get back on track!""");
        }

        System.out.println(NightCoder.LINE_BREAK + "\n");
    }

    /**
     * Adds a To Do to the list of tasks. By default, a newly added To Do is not completed.
     *
     * @param description The description of the task to be added to the list.
     */
    private static void addToDo(String description) {
        Task task = new ToDo(description, false);
        NightCoder.TASKS.add(task);
        try {
            addTaskToTasksFile("T|0|" + description);
            printTaskAdded(description);
        } catch (IOException e) {
            System.out.println("\t[ Task #" + NightCoder.TASKS.size() + " Added: " + description + " ]");
            printErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Adds a Deadline to the list of tasks. By default, a newly added deadline is not completed.
     * Requires information on due date.
     *
     * @param description The description of the task to be added to the list.
     * @param dueBy A string detailing when the task is due by.
     */
    private static void addDeadline(String description, String dueBy) {
        Task task = new Deadline(description, false, dueBy);
        NightCoder.TASKS.add(task);
        try {
            addTaskToTasksFile("D|0|" + description + "|" + dueBy);
            printTaskAdded(description);
        } catch (IOException e) {
            System.out.println("\t[ Task #" + NightCoder.TASKS.size() + " Added: " + description + " ]");
            printErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Adds a Deadline to the list of tasks. By default, a newly added deadline is not completed.
     * Requires information on start and end time.
     *
     * @param description The description of the task to be added to the list.
     * @param startTime A string detailing when the event starts.
     * @param endTime A string detailing when the event ends.
     */
    private static void addEvent(String description, String startTime, String endTime) {
        Task task = new Event(description, false, startTime, endTime);
        NightCoder.TASKS.add(task);
        try {
            addTaskToTasksFile("E|0|" + description + "|" + startTime + "|" + endTime);
            printTaskAdded(description);
        } catch (IOException e) {
            System.out.println("\t[ Task #" + NightCoder.TASKS.size() + " Added: " + description + " ]");
            printErrorUpdatingTasksFile(e);
        }
    }

    /**
     * Displays the list of tasks currently stored with their indices.
     * If the list is empty, it will print a message indicating no tasks are available.
     */
    private static void listTasks() {
        if (NightCoder.TASKS.isEmpty()) {
            System.out.println("\t[ Your To-Do List is Empty! ]");
            System.out.println("\tLooks like we're starting with a clean slate. What shall we tackle first?");
        } else {
            for (int idx = 0; idx < NightCoder.TASKS.size(); idx++) {
                Task task = NightCoder.TASKS.get(idx);
                System.out.println("\t" + (idx+1) + "." + task);
            }
        }
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
                setCompletedInTasksFile(idx - 1, completed); // Convert to zero-based index
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
                printErrorUpdatingTasksFile(e);
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
            deleteTaskFromTasksFile(idx - 1);
        } catch (IOException e) {
            System.out.println("\t[ Task Deleted! ]");
            printErrorUpdatingTasksFile(e);
        }
        System.out.println("\t[ Task Deleted! ]");
        System.out.println("\tTask \"" + task.getDescription() + "\" has been removed from your list. " +
                "Poof, it's gone! Let me know if there's anything else to tidy up.");
    }

    /**
     * Appends a task entry to the tasks file.
     *
     * @param dataLine The task entry in string format to be added.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private static void addTaskToTasksFile(String dataLine) throws IOException {
        // Ensure the directory exists
        Files.createDirectories(Paths.get(NightCoder.DATA_FOLDER));

        // Create the file if it does not exist
        File file = new File(NightCoder.DATA_FOLDER + "/" + NightCoder.TASKS_FILE);
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Failed to create tasks file");
        }

        // Append the data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(dataLine);
            writer.newLine(); // Ensure the new line is properly added
        }
    }

    /**
     * Updates the completion status of a task in the tasks file.
     *
     * @param idx The 0-based index of the task to be updated.
     * @param completed {@code true} if the task is completed, {@code false} otherwise.
     * @throws IOException If an I/O error occurs while updating the file.
     */
    private static void setCompletedInTasksFile(int idx, boolean completed) throws IOException {
        // Read all lines into an ArrayList
        ArrayList<String> lines = readLinesFromTasksFile();

        // Edge case: Ensure the index is within bounds
        if (idx < 0 || idx >= lines.size()) {
            System.err.println("Error: Task index out of bounds for file update.");
            return;
        }

        // Modify the target line
        String[] parts = lines.get(idx).split("\\|"); // Split into max 3 parts to keep description as-is
        if (parts.length < 3) {
            System.err.println("Error: Corrupted task entry in file.");
            return;
        }

        parts[1] = completed ? "1" : "0"; // Update completion status
        lines.set(idx, String.join("|", parts)); // Reconstruct the line

        writeLinesToTasksFile(lines);
    }

    /**
     * Deletes a task from the tasks file based on the given index.
     *
     * @param idx The 0-based index of the task to be deleted.
     * @throws IOException If an I/O error occurs while modifying the file or if the index is out of bounds.
     */
    private static void deleteTaskFromTasksFile(int idx) throws IOException {
        ArrayList<String> lines = readLinesFromTasksFile();

        // Edge case: Ensure the index is within bounds
        if (idx < 0 || idx >= lines.size()) {
            throw new IOException("Task index out of bounds for file update.");
        }

        // Remove the line containing the indexed task
        lines.remove(idx);

        writeLinesToTasksFile(lines);
    }

    /**
     * Reads all lines from the tasks file and returns them as a list.
     *
     * @return An {@code ArrayList} containing all task entries from the file.
     * Returns an empty list if the file does not exist or an error occurs.
     */
    private static ArrayList<String> readLinesFromTasksFile() {
        File file = new File(NightCoder.DATA_FOLDER + "/" + NightCoder.TASKS_FILE);
        // Check if file exists, if not, simply return
        if (!file.exists()) {
            return new ArrayList<String>();
        }
        // Read all lines into an ArrayList
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            System.out.println("\t[ERROR] Error reading tasks file: " + e.getMessage());
            return new ArrayList<String>();
        }
    }

    /**
     * Writes a list of task entries to the tasks file, overwriting its content.
     *
     * @param lines An {@code ArrayList} containing the updated task entries.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private static void writeLinesToTasksFile(ArrayList<String> lines) throws IOException {
        // Ensure the directory exists
        Files.createDirectories(Paths.get(NightCoder.DATA_FOLDER));

        // Create the file if it does not exist
        File file = new File(NightCoder.DATA_FOLDER + "/" + NightCoder.TASKS_FILE);
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Failed to create tasks file");
        }

        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) { // Overwrite mode
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating tasks file: " + e.getMessage());
        }
    }

    /**
     * Parses a task entry from a string and adds it to the corresponding task list.
     *
     * @param line The task entry in string format.
     */
    private static void parseAndAddTask(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            System.out.println("\t[ ERROR ] Invalid task format detected: " + line);
            return;
        }

        String type = parts[0];
        boolean isCompleted = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            NightCoder.TASKS.add(new ToDo(description, isCompleted));
            break;
        case "D":
            if (parts.length < 4) {
                System.out.println("\t[ ERROR ] Deadline task missing due date: " + line);
                return;
            }
            NightCoder.TASKS.add(new Deadline(description, isCompleted, parts[3]));
            break;
        case "E":
            if (parts.length < 5) {
                System.out.println("\t[ ERROR ] Event task missing start or end time: " + line);
                return;
            }
            NightCoder.TASKS.add(new Event(description, isCompleted, parts[3], parts[4]));
            break;
        default:
            System.out.println("\t[ ERROR ] Unknown task type: " + line);
        }
    }

    /**
     * The main entry point of the program. Initializes the application, loads tasks from storage,
     * and starts the user input loop until termination.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        printWelcome();
        loadTasksFromData();
        try (Scanner scannerInstance = new Scanner(System.in)) {
            NightCoder.SCANNER = scannerInstance;
            while (true) {
                if (!getUserInput()) {
                    break;
                }
            }
        }
        printExit();
    }
}
