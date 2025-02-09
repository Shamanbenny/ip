package nightcoder;

import nightcoder.parser.Parser;
import nightcoder.storage.Storage;
import nightcoder.task.TaskList;

/**
 * A playful and motivational chatbot assistant for late-night coding sessions.
 *
 * @author ShamanBenny
 * @version 10
 */
public class NightCoder {
    private static final String DATA_FOLDER = "data";
    private static final String TASKS_FILE = "tasks.txt";
    private static Parser parser;
    private static final Storage STORAGE = new Storage(NightCoder.DATA_FOLDER, NightCoder.TASKS_FILE);
    private static final TaskList TASKS = new TaskList(NightCoder.STORAGE);


    public NightCoder() {
        NightCoder.parser = new Parser(NightCoder.STORAGE, NightCoder.TASKS);
        NightCoder.TASKS.loadTasks();
    }

    public void saveTasksOnClose() {
        NightCoder.TASKS.saveTasks();
    }

    public String getResponse(String input) {
        return NightCoder.parser.parseCommand(input);
    }
}
