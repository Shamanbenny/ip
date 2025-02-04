package nightcoder.ui;

import java.io.IOException;

/**
 * Handles all user interface interactions within the NightCoder application.
 * It provides methods to display messages, errors, and guides to the user in a
 * structured and readable format.
 * The class is designed as a utility class, hence it cannot be instantiated.
 *
 * @author ShamanBenny
 * @version 8.1
 */
public class Ui {
    public static final String LINE_BREAK =
            "\t______________________________________________________________________________________________";
    public static final String LOGO = """
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
            \t                \\______/                                                                      \s""";

    /**
     * Represents the commands supported by the NightCoder application.
     * Each command is associated with a syntax and a description to provide help and guidance for users.
     */
    private enum CommandHelp {
        HELP("help", "Prints this handy guide. Because even pros need reminders sometimes."),
        TODO("todo <String>", "Adds a to-do task to your list. Just tell me what needs doing, " +
                "and I'll keep track.\n\t      Example: todo Finish the project report"),
        DEADLINE("deadline <String> /by <String>", "Adds a task with a deadline. Perfect for those " +
                "time-sensitive missions!\n\t      Example: deadline Submit assignment /by 2025-01-30 23:59"),
        EVENT("event <String> /from <String> /to <String>", "Adds an event with a start and " +
                "end time. Keep your schedule sharp!\n\t      Example: event Team meeting /from 2025-01-21 3:00 PM " +
                "/to 2025-01-21 4:00 PM"),
        LIST("list", "Shows all your tasks. Think of it as your personal task constellation."),
        MARK("mark <int>", "Marks a task as complete. Use the task number from the list.\n" +
                "\t      Example: mark 1"),
        UNMARK("unmark <int>", "Marks a task as incomplete. Sometimes things need a second look!\n" +
                "\t      tExample: unmark 1"),
        DELETE("delete <int>", "Deletes a task from your to-do list. Use the task number from " +
                "the list.\n\t      Example: delete 2"),
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
     * Prevents the instantiation of the {@code Ui} class.
     *
     * @throws UnsupportedOperationException If an attempt is made to instantiate this class.
     */
    private Ui() {
        throw new UnsupportedOperationException("nightcoder.ui.Ui class should not be instantiated.");
    }

    /**
     * Prints the line with a specific indent, mainly for NightCoder's response.
     *
     * @param line The string to be printed with an indent.
     */
    public static void printIndentedLine(String line) {
        System.out.println("\t" + line);
    }

    /**
     * Prints the welcome message along with an ASCII art logo.
     */
    public static void printWelcome() {
        System.out.println("\n" + Ui.LINE_BREAK + "\n\n" + Ui.LOGO);
        Ui.printIndentedLine("Booting Up...\n");
        System.out.println("""
                \tAh, there you are! The moon is bright, the code is flowing, and caffeine-wait, I mean
                \tmotivation-fuels our mission tonight. Welcome back to Night Coder, your loyal (and slightly
                \tsleep-deprived) coding companion. Whether it's wrangling deadlines, or organizing your todo
                \tlist, I'm here to lend a hand.""");
        System.out.println(" ");
        Ui.printIndentedLine("Let's make some magic together. What's on the docket tonight?");
        System.out.println(" ");
        Ui.printIndentedLine("If you're unsure about what I can do, just type \"help\", and I'll get you " +
                "sorted in no time!");
        System.out.println(Ui.LINE_BREAK + "\n");
    }

    /**
     * Prints a message indicating a task has been added.
     *
     * @param description The description of the newly added task.
     * @param idx The 1-based index of the newly added task.
     */
    public static void printTaskAdded(String description, int idx) {
        System.out.println("\t[ Task #" + idx + " Added: " + description + " ]");
        System.out.println("\tGot it! I'll keep this safe in your to-do list. Let me know what's next!");
    }

    /**
     * Prints an error message for incorrect command usage.
     *
     * @param command The name of the command for which the usage was invalid.
     */
    public static void printInvalidUsage(String command) {
        Ui.printIndentedLine("[ Oops! ]");
        Ui.printIndentedLine("Incorrect usage of \"" + command + "\". Type \"help\" to refer to its " +
                "appropriate usage. Let's get back on track!");
    }

    /**
     * Prints an error message for invalid input that resulted in Number Format Exception.
     *
     * @param command The name of the command for which the input was invalid.
     */
    public static void printInvalidNumberInput(String command) {
        Ui.printIndentedLine("[ Invalid Usage! ]");
        Ui.printIndentedLine("Hmm, please enter a number that matches one of your tasks on the list.");
        Ui.printIndentedLine("Double-check your task list with \"list\", and try again!");
        Ui.printIndentedLine("Example: " + command + " 1");
    }

    /**
     * Prints an error message regarding the IOException that occurred during the updating of Tasks Files.
     *
     * @param e The IOException that occurred.
     */
    public static void printErrorUpdatingTasksFile(IOException e) {
        System.err.println("Error writing to tasks file: " + e.getMessage());
        Ui.printIndentedLine("[ ERROR ] It appears that the updating of the tasks data file has failed.");
        Ui.printIndentedLine("          This may result in non-persistent task tracking if not addressed properly.");
        Ui.printIndentedLine("[ LOG ]   " + e.getMessage());
    }

    /**
     * Prints a detailed guide of all available commands in the NightCoder application.
     * The guide includes the syntax and description for each command, making it easy for users
     * to understand and use the application effectively.
     */
    public static void printHelp() {
        System.out.println("\t[ Night Code Command Guide ]");
        System.out.println("\tNeed a hand? No problem! Here's what I can do for you:\n");

        for (Ui.CommandHelp cmd : Ui.CommandHelp.values()) {
            System.out.println("\t    " + cmd.getSyntax());
            System.out.println("\t    - " + cmd.getDescription() + "\n");
        }

        System.out.println("\tGot it? Let's get back to work!");
    }

    /**
     * Displays a farewell message before exiting the application
     */
    public static void printExit() {
        System.out.println(Ui.LINE_BREAK);
        System.out.println("""
                
                \tAlright, signing off for now. Remember, even the brightest coders need some rest-yes, I'm
                \tlooking at you! ( 0 w 0 )""");
        System.out.println("""
                \tIf you need me, you know where to find me. Until next time, keep dreaming big, debugging smart,
                \tand chasing that moonlit inspiration.""");
        Ui.printIndentedLine("Goodnight, and happy coding!");
        System.out.println(" ");
        Ui.printIndentedLine("Powering Down...");
        System.out.println(Ui.LINE_BREAK);
    }
}