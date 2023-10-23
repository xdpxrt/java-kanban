package project.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.manager.Managers;
import project.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    private TaskManager taskManager;
    private Subtask task1;
    private Subtask task2;

    private void createSubtasks() {
        task1 = new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка"
                , 180, "10.06.2024", 1);
        task2 = new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента"
                , 250, "12.06.2024", 1);
        taskManager.addSubtask(task1);
        taskManager.addSubtask(task2);
    }

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
    }

    @Test
    public void emptySubtasksListCheckStatusTest() {
        assertEquals(TaskStatus.NEW, taskManager.getTask(1).getTaskStatus());
    }

    @Test
    public void allSubtasksAreNewCheckStatusTest() {
        createSubtasks();
        assertEquals(TaskStatus.NEW, taskManager.getTask(1).getTaskStatus());
    }

    @Test
    public void subtasksInProgressAndNewCheckStatusTest() {
        createSubtasks();
        taskManager.updateSubtask(2, TaskStatus.IN_PROGRESS, task1);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTask(1).getTaskStatus());
    }

    @Test
    public void allSubtasksInProgressCheckStatusTest() {
        createSubtasks();
        taskManager.updateSubtask(2, TaskStatus.IN_PROGRESS, task1);
        taskManager.updateSubtask(3, TaskStatus.IN_PROGRESS, task2);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTask(1).getTaskStatus());
    }

    @Test
    public void subtasksDoneAndInProgressCheckStatusTest() {
        createSubtasks();
        taskManager.updateSubtask(2, TaskStatus.DONE, task1);
        taskManager.updateSubtask(3, TaskStatus.IN_PROGRESS, task2);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTask(1).getTaskStatus());
    }

    @Test
    public void subtasksDoneAndNewCheckStatusTest() {
        createSubtasks();
        taskManager.updateSubtask(2, TaskStatus.DONE, task1);
        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getTask(1).getTaskStatus());
    }

    @Test
    public void allSubtasksAreDoneCheckStatusTest() {
        createSubtasks();
        taskManager.updateSubtask(2, TaskStatus.DONE, task1);
        taskManager.updateSubtask(3, TaskStatus.DONE, task2);
        assertEquals(TaskStatus.DONE, taskManager.getTask(1).getTaskStatus());
    }
}