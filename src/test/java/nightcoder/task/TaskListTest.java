package nightcoder.task;

import nightcoder.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    // Storage is instantiated but never used in the following tests
    private final TaskList taskList = new TaskList(new Storage("", ""));

    @Test
    public void parseDate_all20thOfEachMonth_returnsFormattedDateString() {
        String[] expectedDates = {
            "Jan 20 2025", "Feb 20 2025", "Mar 20 2025", "Apr 20 2025",
            "May 20 2025", "Jun 20 2025", "Jul 20 2025", "Aug 20 2025",
            "Sep 20 2025", "Oct 20 2025", "Nov 20 2025", "Dec 20 2025"
        };

        for (int month = 1; month <= 12; month++) {
            String inputDate = String.format("2025-%02d-20", month);
            assertEquals(expectedDates[month - 1], this.taskList.parseDate(inputDate),
                    "Failed at month: " + month);
        }
    }

    @Test
    public void parseDate_invalidDate_returnsInputString() {
        assertEquals("0000-00-00", this.taskList.parseDate("0000-00-00"));
        assertEquals("2025-03-20 15:00", this.taskList.parseDate("2025-03-20 15:00"));
        assertEquals("Tonight", this.taskList.parseDate("Tonight"));
    }
}
