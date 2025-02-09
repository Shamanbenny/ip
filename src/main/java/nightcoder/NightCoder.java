package nightcoder;

import java.util.Scanner;

import nightcoder.parser.Parser;
import nightcoder.storage.Storage;
import nightcoder.task.TaskList;
import nightcoder.ui.Ui;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 8.1
 */
public class NightCoder {
    private static final String DATA_FOLDER = "data";
    private static final String TASKS_FILE = "tasks.txt";
    private static Parser parser;
    private static final Storage STORAGE = new Storage(NightCoder.DATA_FOLDER, NightCoder.TASKS_FILE);
    private static final TaskList TASKS = new TaskList(NightCoder.STORAGE);

    /**
     * Initializes the application, loads tasks from storage, and starts the user input loop until termination.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Ui.printWelcome();
        NightCoder.TASKS.loadTasks();
        try (Scanner scannerInstance = new Scanner(System.in)) {
            NightCoder.parser = new Parser(scannerInstance, NightCoder.STORAGE, NightCoder.TASKS);
            while (true) {
                if (!NightCoder.parser.getUserInput()) {
                    break;
                }
            }
        }
        NightCoder.TASKS.saveTasks();
        Ui.printExit();
    }

    public String getResponse(String input) {
        return "NightCoder heard: " + input;
    }
}
