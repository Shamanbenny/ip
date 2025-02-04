package nightcoder.storage;

import nightcoder.task.Deadline;
import nightcoder.task.Event;
import nightcoder.task.Task;
import nightcoder.task.ToDo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageTest {
    private final Storage STORAGE = new Storage("", "");

    @Test
    public void parseStringToTask_validToDoUsage_returnsTask() {
        // Scenario 1 - Incomplete Task
        Task task1 = new ToDo("Running", false);
        Task task1Test = this.STORAGE.parseStringToTask("T|0|Running");
        assertEquals(task1.getDescription(), task1Test.getDescription());
        assertEquals(task1.isCompleted(), task1Test.isCompleted());

        // Scenario 2 - Complete Task
        Task task2 = new ToDo("Jogging Home", true);
        Task task2Test = this.STORAGE.parseStringToTask("T|1|Jogging Home");
        assertEquals(task2.getDescription(), task2Test.getDescription());
        assertEquals(task2.isCompleted(), task2Test.isCompleted());
    }

    @Test
    public void parseStringToTask_validDeadlineUsage_returnsTask() {
        // Scenario 1 - Incomplete Task
        Deadline task1 = new Deadline("Submit Report", false, "2025-02-10");
        Task task1Test = this.STORAGE.parseStringToTask("D|0|Submit Report|2025-02-10");
        assertEquals(task1.getDescription(), task1Test.getDescription());
        assertEquals(task1.isCompleted(), task1Test.isCompleted());
        assertEquals(task1.getDueBy(), ((Deadline) task1Test).getDueBy());

        // Scenario 2 - Complete Task
        Deadline task2 = new Deadline("Finish Homework", true, "2025-02-15");
        Task task2Test = this.STORAGE.parseStringToTask("D|1|Finish Homework|2025-02-15");
        assertEquals(task2.getDescription(), task2Test.getDescription());
        assertEquals(task2.isCompleted(), task2Test.isCompleted());
        assertEquals(task2.getDueBy(), ((Deadline) task2Test).getDueBy());
    }

    @Test
    public void parseStringToTask_validEventUsage_returnsTask() {
        // Scenario 1 - Incomplete Task
        Event task1 = new Event("Team Meeting", false, "2025-02-12 13:00", "2025-02-12 14:00");
        Task task1Test = this.STORAGE.parseStringToTask("E|0|Team Meeting|2025-02-12 13:00|2025-02-12 14:00");
        assertEquals(task1.getDescription(), task1Test.getDescription());
        assertEquals(task1.isCompleted(), task1Test.isCompleted());
        assertEquals(task1.getStartTime(), ((Event) task1Test).getStartTime());
        assertEquals(task1.getEndTime(), ((Event) task1Test).getEndTime());

        // Scenario 2 - Complete Task
        Event task2 = new Event("Conference", true, "2025-03-01 09:00", "2025-03-01 10:00");
        Task task2Test = this.STORAGE.parseStringToTask("E|1|Conference|2025-03-01 09:00|2025-03-01 10:00");
        assertEquals(task2.getDescription(), task2Test.getDescription());
        assertEquals(task2.isCompleted(), task2Test.isCompleted());
        assertEquals(task2.getStartTime(), ((Event) task2Test).getStartTime());
        assertEquals(task2.getEndTime(), ((Event) task2Test).getEndTime());
    }

    @Test
    public void parseStringToTask_invalidFormat_throwsException() {
        // Missing task type
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("|1|Todo without type"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("|1|Task without " +
                "type|Tonight"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("|1|Task without " +
                "type|2025-01-01 10:00|2025-01-01 12:00"));

        // Missing completion status
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("T||Missing completion"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D||Missing completion" +
                "|Tonight"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E||Missing completion" +
                "|2:00|4:00"));

        // Missing description
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("T|0|"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|0||Tonight"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|0||2:00|4:00"));

        // Completely empty string
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask(""));

        // Only type provided
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("T"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E"));
    }

    @Test
    public void parseStringToTask_invalidTaskType_throwsException() {
        // Unrecognized task type
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("X|1|Unknown Type Task"));
    }

    @Test
    public void parseStringToTask_invalidDeadlineFormat_throwsException() {
        // Missing deadline date
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|0|Missing Date"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|0|Missing Date|"));

        // Only type and status provided
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|1"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|1||"));
    }

    @Test
    public void parseStringToTask_invalidEventFormat_throwsException() {
        // Missing start and/or end time
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|0|Incomplete Event"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|0|Incomplete Event|"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|0|Incomplete Event|2:00"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|0|Incomplete Event|2:00|"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|0|Incomplete Event||"));

        // Only type and status provided
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|1"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|1|||"));
    }

    @Test
    public void parseStringToTask_invalidCompletionStatus_throwsException() {
        // Non-numeric completion status
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("T|X|Invalid Completion"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|X|Invalid Completion|2:00"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|X|Invalid Completion|" +
                "2:00|4:00"));

        // Negative number as completion status
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("T|-1|Negative Completion"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("D|-1|Negative Completion|2:00"));
        assertThrows(IllegalArgumentException.class, () -> STORAGE.parseStringToTask("E|-1|Negative Completion|" +
                "2:00|4:00"));
    }
}
