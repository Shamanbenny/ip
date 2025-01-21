import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 1.0
 */
public class NightCoder {
    private static final String lineBreak = "\t______________________________________________________________________________________________";
    private static ArrayList<String> tasks = new ArrayList<>();

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
     * Reads user input, checks if the command is "bye". Else, process the command.
     *
     * @return false if the input is "bye", true otherwise.
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
     * Parses the user input and processes the command accordingly.
     * [Contains SIMPLE attempt at detecting invalid command usage]
     *
     * @param input The full user input string to be parsed and processed.
     */
    private static void parseCommand(String input) {
        System.out.println(NightCoder.lineBreak);

        String[] parts = input.split(" ", 2);
        String command = parts[0];
        switch (command) {
            case "help":
                printHelp();
                break;
            case "add":
                if (parts.length != 2) {
                    System.out.println("""
                        \t⚠️ Oops!
                        \tIncorrect usage of "add". Type "help" to refer to its appropriate usage. Let’s get back on track! 🚀""");
                    break;
                }
                String params = parts[1];
                addTask(params);
                break;
            case "list":
                listTasks();
                break;
            default:
                System.out.println("""
                        \t⚠️ Oops!
                        \tI didn’t catch that. Type "help" to see the list of commands I understand. Let’s get back on track! 🚀""");
        }

        System.out.println(NightCoder.lineBreak + "\n");
    }

    /**
     * Adds a task to the to-do list.
     *
     * @param task The task to be added to the list.
     */
    private static void addTask(String task) {
        tasks.add(task);
        System.out.println("\t✅ Task #" + tasks.size() + " Added: " + task);
        System.out.println("\tGot it! I’ll keep this safe in your to-do list. Let me know what’s next! 🌟");
    }

    /**
     * Displays the list of tasks with their respective indices.
     * Iterates through the tasks ArrayList and prints each task with its index in a numbered format.
     * If the list is empty, it will print a message indicating no tasks are available.
     */
    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\t\uD83C\uDF0C Your To-Do List is Empty!");
            System.out.println("Looks like we’re starting with a clean slate. What shall we tackle first? \uD83C\uDF1F");
        } else {
            System.out.print(
                    // Using Functional Programming learnt from CS2030S to replace the need to use for-loops
                    Stream.iterate(0, i -> i + 1)
                            .limit(tasks.size())
                            .reduce("", (result, index) -> result + "\t" + (index + 1) + ". " + tasks.get(index) + "\n", String::concat)
            );
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
