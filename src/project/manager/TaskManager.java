package project.manager;

import project.task.*;

import java.util.List;

public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    List<Task> getAllTasks();

    Task getTask(Integer taskId);

    List<Task> getSubtasksByEpic(Integer epicId);

    void removeAllTasks();

    void removeTask(Integer taskId);

    void updateTask(int taskId, TaskStatus status, Task task);

    void updateEpic(int taskId, Epic epic);

    void updateSubtask(int taskId, TaskStatus status, Subtask subtask);

    HistoryManager getHistoryManager();
}



