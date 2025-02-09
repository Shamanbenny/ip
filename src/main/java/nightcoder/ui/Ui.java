package nightcoder.ui;

import java.io.IOException;

/**
 * Handles all user interface interactions within the NightCoder application.
 * It provides methods to display messages, errors, and guides to the user in a
 * structured and readable format.
 * The class is designed as a utility class, hence it cannot be instantiated.
 *
 * @author ShamanBenny
 * @version 10
 */
public class Ui {
    /**
     * Represents the commands supported by the NightCoder application.
     * Each command is associated with a syntax and a description to provide help and guidance for users.
     */
    private enum CommandHelp {
        HELP("help", "Prints this handy guide. Because even pros need reminders sometimes."),
        TODO("todo <String>", "Adds a to-do task to your list. Just tell me what needs doing, "
                + "and I'll keep track.\n\tExample: todo Finish the project report"),
        DEADLINE("deadline <String> /by <String>", "Adds a task with a deadline. Perfect for those "
                + "time-sensitive missions!\n\tExample: deadline Submit assignment /by 2025-01-30 23:59"),
        EVENT("event <String> /from <String> /to <String>", "Adds an event with a start and "
                + "end time. Keep your schedule sharp!\n\tExample: event Team meeting /from 2025-01-21 3:00 PM "
                + "/to 2025-01-21 4:00 PM"),
        LIST("list", "Shows all your tasks. Think of it as your personal task constellation."),
        FIND("find <String>", "Find specific keyword amongst your list of tasks. I'll do the work of finding "
                + "it for you!\n\tExample: find homework"),
        MARK("mark <int>", "Marks a task as complete. Use the task number from the list.\n"
                + "\tExample: mark 1"),
        UNMARK("unmark <int>", "Marks a task as incomplete. Sometimes things need a second look!\n"
                + "\tExample: unmark 1"),
        DELETE("delete <int>", "Deletes a task from your to-do list. Use the task number from "
                + "the list.\n\tExample: delete 2");

        private final String SYNTAX;
        private final String DESCRIPTION;

        /**
         * Constructs a CommandHelp enum instance with the specified syntax and description.
         *
         * @param syntax      The syntax of the command.
         * @param description The description of the command.
         */
        CommandHelp(String syntax, String description) {
            this.SYNTAX = syntax;
            this.DESCRIPTION = description;
        }

        public String getSyntax() {
            return SYNTAX;
        }

        public String getDescription() {
            return DESCRIPTION;
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
     * Returns the welcome message without the ASCII art logo.
     *
     * @param part The index for the part of the welcome message to return.
     * @return The String containing the welcome message.
     */
    public static String getWelcomeString(int part) {
        switch (part) {
        case 1:
            return "Ah, there you are! The moon is bright, the code is flowing, and caffeine-wait, I mean "
                    + "motivation-fuels our mission tonight.\n\n"
                    + "Welcome back to Night Coder, your loyal (and slightly sleep-deprived) coding companion. "
                    + "Whether it's wrangling deadlines, or organizing your todo list, I'm here to lend a hand.";
        case 2:
            return "Let's make some magic together. What's on the docket tonight?\n\n"
                    + "If you're unsure about what I can do, just type \"help\", and I'll get you sorted in no time!";
        default:
            return "";
        }
    }

    /**
     * Returns the message indicating a task has been added.
     *
     * @param description The description of the newly added task.
     * @param idx The 1-based index of the newly added task.
     * @return The String message indicating a task has been successfully added.
     */
    public static String getTaskAdded(String description, int idx) {
        return "[ Task #" + idx + " Added: " + description + " ]\n"
                + "Got it! I'll keep this safe in your to-do list. Let me know what's next!";
    }

    /**
     * Returns the error message for incorrect command usage.
     *
     * @param command The name of the command for which the usage was invalid.
     * @return The String message indicating the error for incorrect command usage.
     */
    public static String getInvalidUsage(String command) {
        return "[ Oops! ]\n"
                + "Incorrect usage of \"" + command + "\". Type \"help\" to refer to its "
                + "appropriate usage. Let's get back on track!";
    }

    /**
     * Returns the error message for invalid input that resulted in Number Format Exception.
     *
     * @param command The name of the command for which the input was invalid.
     * @return The String message indicating the invalid input that resulted in NUmber Format Exception.
     */
    public static String getInvalidNumberInput(String command) {
        return "[ Invalid Usage! ]\n"
                + "Hmm, please enter a number that matches one of your tasks on the list.\n"
                + "Double-check your task list with \"list\", and try again!\n"
                + "Example: " + command + " 1";
    }

    /**
     * Returns the error message regarding the IOException that occurred during the updating of Tasks Files.
     *
     * @param e The IOException that occurred.
     * @return The String message indicating the error message regarding the IOException.
     */
    public static String getErrorUpdatingTasksFile(IOException e) {
        System.err.println("Error writing to tasks file: " + e.getMessage());
        return "[ ERROR ] It appears that the updating of the tasks data file has failed. "
                + "This may result in non-persistent task tracking if not addressed properly.\n"
                + "[ LOG ]   " + e.getMessage();
    }

    /**
     * Returns a detailed guide of all available commands in the NightCoder application.
     * The guide includes the syntax and description for each command, making it easy for users
     * to understand and use the application effectively.
     *
     * @return The String message for the printing of a detailed guide for all available commands.
     */
    public static String getHelp() {
        StringBuilder output = new StringBuilder("""
                [ Night Code Command Guide ]
                Need a hand? No problem! Here's what I can do for you:
                
                """);

        for (CommandHelp cmd : CommandHelp.values()) {
            output.append(cmd.getSyntax()).append("\n").append("- ").append(cmd.getDescription()).append("\n\n");
        }

        output.append("Got it? Let's get back to work!");
        return output.toString();
    }
}
