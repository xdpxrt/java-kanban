package project.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.server.KVServer;
import project.task.Task;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.manager.HttpTaskManager.load;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    private TaskManager newTaskManager;
    private KVServer kvServer;

    @BeforeEach
    public void startServer() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = Managers.getDefault();
        init();
    }

    @AfterEach
    public void shutDownServer() {
        kvServer.stop();
    }

    @Test
    public void saveAndLoadZeroTasksTest() {
        taskManager.save();
        newTaskManager = load();
        assertEquals(0, newTaskManager.getAllTasks().size());
    }

    @Test
    public void saveAndLoadEmptyEpicTest() {
        taskManager.addEpic(epic);
        newTaskManager = load();
        assertEquals(1, newTaskManager.getAllTasks().size());
    }

    @Test
    public void saveAndLoadTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addTask(task);
        taskManager.getTask(3);
        taskManager.getTask(2);
        List<Task> history = taskManager.getHistoryManager().getHistory();
        newTaskManager = load();
        List<Task> newHistory = newTaskManager.getHistoryManager().getHistory();
        assertEquals(epic, newTaskManager.getTask(1));
        assertEquals(subtask, newTaskManager.getTask(2));
        assertEquals(task, newTaskManager.getTask(3));
        Assertions.assertArrayEquals(history.toArray(), newHistory.toArray());

    }

}
