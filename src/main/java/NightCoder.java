import java.util.Scanner;
import java.util.ArrayList;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 4.0
 */
public class NightCoder {
    private static final String lineBreak = "\t______________________________________________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<>();

    private static void printWelcome() {
        String logo = """
                \t  /$$   /$$ /$$           /$$         /$$      /$$$$$$                  /$$                   \s
                \t | $$$ | $$|__/          | $$        | $$     /$$__  $$                | $$                   \s
                \t | $$$$| $$ /$$  /$$$$$$ | $$$$$$$  /$$$$$$  | $$  \\__/  /$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$\s
                \t | $$ $$ $$| $$ /$$__  $$| $$__  $$|_  $$_/  | $$       /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$
                \t | $$  $$$$| $$| $$  \\ $$| $$  \\ $$  | $$    | $$      | $$  \\ $$| $$  | $$| $$$$$$$$| $$  \\__/
                \t | $$\\  $$$| $$| $$  | $$| $$  | $$  | $$ /$$| $$    $$| $$  | $$| $$  | $$| $$_____/| $$     \s
                \t | $$ \\  $$| $$|  $$$$$$$| $$  | $$  |  $$$$/|  $$$$$$/|  $$$$$$/|  $$$$$$$|  $$$$$$$| $$     \s
                \t |__/  \\__/|__/ \\____  $$|__/  |__/   \\___/   \\______/  \\______/  \\_______/ \\_______/|__/     \s
                \t                /$$  \\ $$                                                                     \s
                \t               |  $$$$$$/                                                                     \s
                \t                \\______/                                                                      \s
                """;
        System.out.println("\n" + NightCoder.lineBreak + "\n\n" + logo);
        System.out.println("\t\uD83C\uDF19 Booting Up... ☕\n");
        System.out.println("""
                \tAh, there you are! The moon is bright, the code is flowing, and caffeine—wait, I mean
                \tmotivation—fuels our mission tonight. Welcome back to Night Coder, your loyal (and slightly
                \tsleep-deprived) coding companion. Whether it’s wrangling deadlines, or organizing your todo
                \tlist, I’m here to lend a hand.""");
        System.out.println("\n\tLet’s make some magic together. What’s on the docket tonight? \uD83C\uDF1F");
        System.out.println("\n\tIf you’re unsure about what I can do, just type \"help\", and I’ll get you sorted in no time!");
        System.out.println(NightCoder.lineBreak + "\n");
    }

    /**
     * Prints an error message for incorrect command usage.
     *
     * @param command The name of the command for which the usage was invalid.
     */
    private static void printInvalidUsage(String command) {
        System.out.println("""
                \t⚠️ Oops!
                \tIncorrect usage of""" + " \"" + command + "\"" + """
                . Type "help" to refer to its appropriate usage. Let’s get back on track! 🚀""");
    }

    private static void printHelp() {
        System.out.println("""
                \t🌙 Night Code Command Guide ☕️
                
                \tNeed a hand? No problem! Here’s what I can do for you:
                
                \t    help
                \t    - Prints this handy guide. Because even pros need reminders sometimes.
                
                \t    add <String>
                \t    - Adds a task to your to-do list. Just tell me what needs doing, and I’ll keep track.
                \t      Example: add Finish the project report.
                
                \t    list
                \t    - Shows all your tasks. Think of it as your personal task constellation.
                
                \t    mark <int>
                \t    - Marks a task as complete. Use the task number from the list. Example: mark 1.
                
                \t    unmark <int>
                \t    - Marks a task as incomplete. Sometimes things need a second look! Example: unmark 1.
                
                \t    bye
                \t    - Exits the program. But don’t be a stranger—I’ll be here when you need me again!
                
                \tGot it? Let’s get back to work! 🚀""");
    }

    private static void printExit() {
        System.out.println(NightCoder.lineBreak);
        System.out.println("""
                
                \tAlright, signing off for now. Remember, even the brightest coders need some rest—yes, I’m
                \tlooking at you! \uD83D\uDECC""");
        System.out.println("""
                \tIf you need me, you know where to find me. Until next time, keep dreaming big, debugging smart,
                \tand chasing that moonlit inspiration.""");
        System.out.println("\tGoodnight, and happy coding! \uD83C\uDF0C✨");
        System.out.println("\n\t\uD83C\uDF19 Powering Down... ☕\uD83D\uDCA4");
        System.out.println(NightCoder.lineBreak);
    }

    /**
     * Reads user input from the console and processes it as a command.
     * If the input is "bye" (case-insensitive), the method exits the loop by returning false.
     * Otherwise, it passes the input to {@link #parseCommand(String)} for further processing.
     *
     * @return {@code false} if the input is "bye", {@code true} otherwise.
     */
    private static boolean getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().trim(); // Trim to remove extra spaces

        if ("bye".equalsIgnoreCase(userInput)) {
            return false; // Return false if the input is "bye"
        }
        parseCommand(userInput);
        return true; // Return true for other inputs
    }

    /**
     * Parses the user input and executes the corresponding command.
     * [Contains SIMPLE attempt at detecting invalid command usage]
     *
     * @param input The full user input string to be parsed and processed.
     */
    private static void parseCommand(String input) {
        // TODO: Add Error Handling for Failing Integer Parsing for "mark" and "unmark"
        System.out.println(NightCoder.lineBreak);

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        switch (command) {
            case "help":
                printHelp();
                break;
            case "add":
                if (parts.length != 2) {
                    printInvalidUsage("add");
                    break;
                }
                String params = parts[1];
                addTask(params);
                break;
            case "list":
                listTasks();
                break;
            case "mark":
                if (parts.length != 2) {
                    printInvalidUsage("mark");
                    break;
                }
                int markId = Integer.parseInt(parts[1]);
                setCompleted(markId, true);
                break;
            case "unmark":
                if (parts.length != 2) {
                    printInvalidUsage("unmark");
                    break;
                }
                int unmarkId = Integer.parseInt(parts[1]);
                setCompleted(unmarkId, false);
                break;
            default:
                System.out.println("""
                        \t⚠️ Oops!
                        \tI didn’t catch that. Type "help" to see the list of commands I understand. Let’s get back on track! 🚀""");
        }

        System.out.println(NightCoder.lineBreak + "\n");
    }

    /**
     * Adds a task to the to-do list. By default, a newly added task is not completed.
     *
     * @param description The task to be added to the list.
     */
    private static void addTask(String description) {
        Task task = new Task(description, false);
        tasks.add(task);
        System.out.println("\t✅ Task #" + tasks.size() + " Added: " + description);
        System.out.println("\tGot it! I’ll keep this safe in your to-do list. Let me know what’s next! 🌟");
    }

    /**
     * Displays the list of tasks with their respective indices and completeness.
     * Iterates through the tasks ArrayList and prints each task with its index in a numbered format.
     * If the list is empty, it will print a message indicating no tasks are available.
     */
    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\t\uD83C\uDF0C Your To-Do List is Empty!");
            System.out.println("\tLooks like we’re starting with a clean slate. What shall we tackle first? \uD83C\uDF1F");
        } else {
            // Refactored from Functional Programming to maintain Readability...
            for (int idx = 0; idx < tasks.size(); idx++) {
                Task task = tasks.get(idx);
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
     * @param idx       The 1-based index of the task in the list to update.
     * @param completed A boolean value indicating the desired completion status:
     *                  {@code true} to mark the task as complete, or {@code false} to mark it as incomplete.
     */
    private static void setCompleted(int idx, boolean completed) {
        // Edge-Case ['idx' out of bounds]
        if (idx > tasks.size() || idx < 1) {
            System.out.println("""
                    \t⚠️ Invalid Task Number!
                    \tHmm, that number doesn’t match any tasks on your list. Double-check your task list with "list", and try again!""");
            return;
        }

        // idx is originally 1-indexed [Therefore minus 1 to access 0-indexed ListArray]
        Task task = tasks.get(idx - 1);
        if (task.isCompleted() == completed) {
            // Edge-Case ['task' is already set as complete/incomplete]
            if (completed) {
                System.out.println("\t⚠️ Task Already Complete!");
                System.out.println("\tLooks like task \"" + task.getDescription() + "\" is already marked as done. You’re ahead of the game! \uD83C\uDF89");
            } else {
                System.out.println("\t⚠️ Task Already Incomplete!");
                System.out.println("\tTask \"" + task.getDescription() + "\" is already on your to-do list. No need to unmark it again! \uD83C\uDF1F");
            }
        } else {
            task.setCompleted(completed);
            if (completed) {
                System.out.println("\t✅ Task Marked as Complete!");
                System.out.println("\tGreat job! Task \"" + task.getDescription() + "\" is now marked as done. On to the next one! 🎉");
            } else {
                System.out.println("\t🔄 Task Marked as Incomplete!");
                System.out.println("\tGot it! Task \"" + task.getDescription() + "\" is back on your to-do list. Let’s tackle it when you’re ready! 🌟");
            }
        }
    }

    /**
     * The main method, serving as the entry point for the application.
     * It displays a welcome message, enters a loop to process user input,
     * and exits when the user types "bye".
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        printWelcome();
        while (true) {
            if (!getUserInput()) {
                break;
            }
        }
        printExit();
    }
}
