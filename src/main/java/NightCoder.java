/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 0.0
 */
public class NightCoder {
    private static final String lineBreak = "______________________________________________________________________________________________";

    private static void printWelcome() {
        String logo = """
                 /$$   /$$ /$$           /$$         /$$      /$$$$$$                  /$$                   \s
                | $$$ | $$|__/          | $$        | $$     /$$__  $$                | $$                   \s
                | $$$$| $$ /$$  /$$$$$$ | $$$$$$$  /$$$$$$  | $$  \\__/  /$$$$$$   /$$$$$$$  /$$$$$$   /$$$$$$\s
                | $$ $$ $$| $$ /$$__  $$| $$__  $$|_  $$_/  | $$       /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$
                | $$  $$$$| $$| $$  \\ $$| $$  \\ $$  | $$    | $$      | $$  \\ $$| $$  | $$| $$$$$$$$| $$  \\__/
                | $$\\  $$$| $$| $$  | $$| $$  | $$  | $$ /$$| $$    $$| $$  | $$| $$  | $$| $$_____/| $$     \s
                | $$ \\  $$| $$|  $$$$$$$| $$  | $$  |  $$$$/|  $$$$$$/|  $$$$$$/|  $$$$$$$|  $$$$$$$| $$     \s
                |__/  \\__/|__/ \\____  $$|__/  |__/   \\___/   \\______/  \\______/  \\_______/ \\_______/|__/     \s
                               /$$  \\ $$                                                                     \s
                              |  $$$$$$/                                                                     \s
                               \\______/                                                                      \s
                """;
        System.out.println("\n" + NightCoder.lineBreak + "\n\n" + logo);
        System.out.println("\uD83C\uDF19 Booting Up... ☕\n");
        System.out.println("""
                Ah, there you are! The moon is bright, the code is flowing, and caffeine—wait, I mean
                motivation—fuels our mission tonight. Welcome back to Night Coder, your loyal (and slightly
                sleep-deprived) coding companion. Whether it’s wrangling deadlines, or organizing your todo
                list, I’m here to lend a hand.""");
        System.out.println("\nLet’s make some magic together. What’s on the docket tonight? \uD83C\uDF1F");
        System.out.println(NightCoder.lineBreak);
    }

    private static void printExit() {
        System.out.println("""
                
                Alright, signing off for now. Remember, even the brightest coders need some rest—yes, I’m
                looking at you! \uD83D\uDECC""");
        System.out.println("""
                If you need me, you know where to find me. Until next time, keep dreaming big, debugging smart,
                and chasing that moonlit inspiration.""");
        System.out.println("Goodnight, and happy coding! \uD83C\uDF0C✨");
        System.out.println("\n\uD83C\uDF19 Powering Down... ☕\uD83D\uDCA4");
        System.out.println(NightCoder.lineBreak);
    }

    public static void main(String[] args) {
        printWelcome();
        printExit();
    }
}
