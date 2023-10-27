package project.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.task.Task;
import project.util.CSVTaskUtil;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.manager.FileBackedTaskManager.loadFromFile;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    private TaskManager newTaskManager;

    @BeforeEach
    public void beforeEachFileBacked() {
        taskManager = Managers.getDefaultFileBackedTaskManager();
        init();
    }

    @Test
    public void saveAndLoadZeroTasksTest() {
        taskManager.save();
        newTaskManager = loadFromFile(new File(CSVTaskUtil.getBackupPath()
                , CSVTaskUtil.getFileName()));
        assertEquals(0, newTaskManager.getAllTasks().size());
    }

    @Test
    public void saveAndLoadEmptyEpicTest() {
        taskManager.addEpic(epic);
        newTaskManager = loadFromFile(new File(CSVTaskUtil.getBackupPath()
                , CSVTaskUtil.getFileName()));
        assertEquals(epic, newTaskManager.getTask(1));
    }

    @Test
    public void saveAndLoadTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addTask(task);
        taskManager.getTask(3);
        taskManager.getTask(2);
        List<Task> history = taskManager.getHistoryManager().getHistory();

        newTaskManager = loadFromFile(new File(CSVTaskUtil.getBackupPath()
                , CSVTaskUtil.getFileName()));
        List<Task> newHistory = newTaskManager.getHistoryManager().getHistory();
        assertEquals(epic, newTaskManager.getTask(1));
        assertEquals(subtask, newTaskManager.getTask(2));
        assertEquals(task, newTaskManager.getTask(3));
        Assertions.assertArrayEquals(history.toArray(), newHistory.toArray());
    }
}
