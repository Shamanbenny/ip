import java.util.Scanner;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 1.0
 */
public class NightCoder {
    private static final String lineBreak = "\t______________________________________________________________________________________________";

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
        System.out.println(NightCoder.lineBreak + "\n");
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
        echoCommand(userInput);
        return true; // Return true for other inputs
    }

    /**
     * Echos the user input back.
     *
     * @param input The full user input string to be echo-ed back.
     */
    private static void echoCommand(String input) {
        System.out.println(NightCoder.lineBreak);
        System.out.println("\t" + input);
        System.out.println(NightCoder.lineBreak + "\n");
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
