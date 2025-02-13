package nightcoder.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import nightcoder.task.Deadline;
import nightcoder.task.Event;
import nightcoder.task.Task;
import nightcoder.task.ToDo;

/**
 * Handles the reading and writing of task data to a file.
 * This class manages loading, saving, updating, and deleting tasks
 * from a persistent storage file.
 *
 * @author ShamanBenny
 * @version 10
 */
public class Storage {
    private final String DATA_FOLDER;
    private final String TASKS_FILE;

    /**
     * Constructs a {@code Storage} instance for managing tasks in the specified folder and file.
     *
     * @param folder   The directory where task data is stored.
     * @param filename The name of the file containing task data.
     */
    public Storage(String folder, String filename) {
        this.DATA_FOLDER = folder;
        this.TASKS_FILE = filename;
    }

    /**
     * Reads all lines from the file and returns them as a list of Strings.
     *
     * @return An {@code ArrayList} containing all task entries from the file.
     *     Returns an empty list if the file does not exist or an error occurs.
     */
    public ArrayList<String> readLines() {
        File file = new File(this.DATA_FOLDER + "/" + this.TASKS_FILE);
        // Check if file exists, if not, simply return empty ArrayList<String>
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
            System.err.println("[ ERROR ] Error reading tasks file: " + e.getMessage());
            return new ArrayList<String>();
        }
    }

    /**
     * Loads tasks from the file and converts them into {@code Task} objects.
     *
     * @return An {@code ArrayList} of {@code Task} objects representing previously saved tasks.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<String> lines = this.readLines();
        ArrayList<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            try {
                tasks.add(parseStringToTask(line));
            } catch (IllegalArgumentException e) {
                System.err.println("[ ERROR ] " + e.getMessage());
            }
        }
        if (tasks.isEmpty()) {
            System.out.println("[ INFO ] No previous task data found. Starting fresh!");
        } else {
            System.out.println("[ INFO ] Tasks loaded successfully from previous sessions!");
        }
        return tasks;
    }

    /**
     * Parses a task entry from a formatted string and creates a {@code Task} object.
     *
     * @param line The string containing task details in the format: Type|isCompleted|Description|[Additional Data].
     * @return A {@code Task} object parsed from the string.
     * @throws IllegalArgumentException If the format is invalid or missing required fields.
     */
    public Task parseStringToTask(String line) throws IllegalArgumentException {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format detected: " + line);
        }

        String type = parts[0];
        if (!parts[1].equals("0") && !parts[1].equals("1")) {
            throw new IllegalArgumentException("Invalid task format detected: " + line);
        }
        boolean isCompleted = parts[1].equals("1");
        if (parts[2].trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid task format detected: " + line);
        }
        String description = parts[2];

        // "Incorrect" indentation by Style Checker due to the use of "return".
        // It should, in theory, be of the correct indentation.
        return switch (type) {
            case "T" -> new ToDo(description, isCompleted);
            case "D" -> {
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid task format detected: " + line);
                }
                if (parts[3].trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid task format detected: " + line);
                }
                yield new Deadline(description, isCompleted, parts[3]);
            }
            case "E" -> {
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Invalid task format detected: " + line);
                }
                if (parts[3].trim().isEmpty() || parts[4].trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid task format detected: " + line);
                }
                yield new Event(description, isCompleted, parts[3], parts[4]);
            }
            default -> throw new IllegalArgumentException("Invalid task format detected: " + line);
        };
    }

    /**
     * Deletes a task from the tasks file based on the given index.
     *
     * @param idx The 0-based index of the task to be deleted.
     * @throws IOException If an I/O error occurs while modifying the file or if the index is out of bounds.
     */
    public void deleteTask(int idx) throws IOException {
        ArrayList<String> lines = this.readLines();

        // Edge case: Ensure the index is within bounds
        if (idx < 0 || idx >= lines.size()) {
            throw new IOException("Task index out of bounds for file update");
        }

        // Remove the line containing the indexed task
        lines.remove(idx);

        this.writeLines(lines);
    }

    /**
     * Updates the completion status of a task in the tasks file.
     *
     * @param idx The 0-based index of the task to be updated.
     * @param isCompleted {@code true} if the task is completed, {@code false} otherwise.
     * @throws IOException If an I/O error occurs while updating the file.
     */
    public void setCompleted(int idx, boolean isCompleted) throws IOException {
        ArrayList<String> lines = this.readLines();

        // Edge case: Ensure the index is within bounds
        if (idx < 0 || idx >= lines.size()) {
            throw new IOException("Task index out of bounds for file update");
        }

        // Modify the target line
        String[] parts = lines.get(idx).split("\\|"); // Split into max 3 parts to keep description as-is
        if (parts.length < 3) {
            throw new IOException("Corrupted task entry in file");
        }

        parts[1] = isCompleted ? "1" : "0"; // Update completion status
        lines.set(idx, String.join("|", parts)); // Reconstruct the line

        this.writeLines(lines);
    }

    /**
     * Appends a task entry to the tasks file.
     *
     * @param dataLine The task entry in string format to be added.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void appendTask(String dataLine) throws IOException {
        // Ensure the directory exists
        Files.createDirectories(Paths.get(this.DATA_FOLDER));

        // Create the file if it does not exist
        File file = new File(this.DATA_FOLDER + "/" + this.TASKS_FILE);
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
     * Writes a list of task entries to the tasks file, overwriting its content.
     *
     * @param lines An {@code ArrayList} containing the updated task entries.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeLines(ArrayList<String> lines) throws IOException {
        // Ensure the directory exists
        Files.createDirectories(Paths.get(this.DATA_FOLDER));

        // Create the file if it does not exist
        File file = new File(this.DATA_FOLDER + "/" + this.TASKS_FILE);
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Failed to create tasks file");
        }

        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) { // Overwrite mode
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        }
    }
}
