package project.manager;

import org.junit.jupiter.api.Test;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static project.task.TaskStatus.IN_PROGRESS;
import static project.util.TaskTimeFormatter.ZERO_DATE;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    protected void init() {
        epic = new Epic("testName", "testDescription");
        subtask = new Subtask("testName", "testDescription"
                , 30, "30.06.2023 10:30", 1);
        task = new Task("testName", "testDescription", 30, "21.10.2023 15:30");
    }

    @Test
    public void addTaskTest() {
        taskManager.addTask(task);
        assertEquals(1, taskManager.getAllTasks().size());
        assertEquals(task, taskManager.getTask(1));
    }

    @Test
    public void addTaskWithEmptyStartTimeTest() {
        task = new Task("testName", "testDescription", 30, ZERO_DATE);
        taskManager.addTask(task);
        assertEquals(1, taskManager.getAllTasks().size());
        assertEquals(task, taskManager.getTask(1));
    }

    @Test
    public void addEpicTest() {
        taskManager.addEpic(epic);
        assertEquals(1, taskManager.getAllTasks().size());
        assertEquals(epic, taskManager.getTask(1));
    }

    @Test
    public void addSubtaskTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        assertEquals(2, taskManager.getAllTasks().size());
        assertEquals(subtask, taskManager.getTask(2));
    }

    @Test
    public void addSubtaskWithWrongEpicIdTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(new Subtask("testName", "testDescription"
                , 30, "30.06.2023 15:30", 2));
        assertEquals(1, taskManager.getAllTasks().size());
        assertEquals(epic, taskManager.getTask(1));
    }

    @Test
    public void getTaskWrongIdTest() {
        taskManager.addTask(task);
        Task returnedTask = taskManager.getTask(2);
        assertNull(returnedTask);
    }

    @Test
    public void getTaskWhenZeroTasksTest() {
        Task returnedTask = taskManager.getTask(1);
        assertNull(returnedTask);
    }

    @Test
    public void getAllTasksWhenEmptyTest() {
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void getAllTasksTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addTask(task);
        assertEquals(3, taskManager.getAllTasks().size());
    }

    @Test
    public void getSubtasksByEpicWhenNoEpicTest() {
        List<Task> list = taskManager.getSubtasksByEpic(1);
        assertEquals(0, list.size());
    }

    @Test
    public void getSubtasksByEpicWhenEmptyTest() {
        taskManager.addEpic(epic);
        List<Task> list = taskManager.getSubtasksByEpic(1);
        assertEquals(0, list.size());
    }

    @Test
    public void getSubtasksByEpicTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        List<Task> list = taskManager.getSubtasksByEpic(1);
        assertEquals(1, list.size());
        assertEquals(subtask, list.get(0));
    }

    @Test
    public void removeAllTasksTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addTask(task);
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void removeTaskWhenEmptyTest() {
        taskManager.removeTask(1);
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void removeTaskTest() {
        taskManager.addTask(task);
        taskManager.removeTask(1);
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void removeSubtaskTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.removeTask(2);
        assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    public void removeEpicTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.removeTask(1);
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void updateTaskTest() {
        taskManager.addTask(task);
        Task newTask = new Task("newTestName", "newTestDescription"
                , 20, "10.10.2023 15:30");
        taskManager.updateTask(1, IN_PROGRESS, newTask);
        assertEquals(newTask, taskManager.getTask(1));
    }

    @Test
    public void updateEpicWithoutSubtaskTest() {
        taskManager.addEpic(epic);
        Epic newEpic = new Epic("newTestName", "newTestDescription");
        taskManager.updateEpic(1, newEpic);
        assertEquals(newEpic, taskManager.getTask(1));
    }

    @Test
    public void updateEpicTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        Epic newEpic = new Epic("newTestName", "newTestDescription");
        taskManager.updateEpic(1, newEpic);
        assertEquals(newEpic, taskManager.getTask(1));
    }

    @Test
    public void updateSubtaskTest() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        Subtask newSubtask = new Subtask("newTestName", "newTestDescription"
                , 35, "30.10.2023 16:30", 1);
        taskManager.updateSubtask(2, IN_PROGRESS, newSubtask);
        assertEquals(newSubtask, taskManager.getTask(2));
    }
}
