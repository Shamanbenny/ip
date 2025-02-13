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
    private static final Storage storage = new Storage(NightCoder.DATA_FOLDER, NightCoder.TASKS_FILE);
    private static final TaskList tasks = new TaskList(NightCoder.storage);


    public NightCoder() {
        NightCoder.parser = new Parser(NightCoder.storage, NightCoder.tasks);
        NightCoder.tasks.loadTasks();
    }

    public void saveTasksOnClose() {
        NightCoder.tasks.saveTasks();
    }

    public String getResponse(String input) {
        return NightCoder.parser.parseCommand(input);
    }
}
