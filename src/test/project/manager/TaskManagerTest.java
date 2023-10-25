package project.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task1;


    protected void init() {
        task1 = new Task("Отпуск", "Купить билеты", 30, "21.10.2023 15:30");
        task1.setId(1);
    }

    @Test
    public void addTaskTest() {
        taskManager.addTask(task1);
        assertEquals(task1, taskManager.getTask(1));
        assertEquals(1, taskManager.getAllTasks().size());
    }
}
