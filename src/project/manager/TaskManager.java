package project.manager;

import project.task.*;

import java.util.ArrayList;

public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    ArrayList<Task> getAllTasks();

    Task getTask(Integer taskId);

    ArrayList<Task> getSubtasksByEpic(Integer epicId);

    void removeAllTasks();

    void removeTask(Integer taskId);

    void updateTask(int taskId, TaskStatus status, Task task);

    void updateEpic(int taskId, Epic epic);

    void updateSubtask(int taskId, TaskStatus status, Subtask subtask);
}



