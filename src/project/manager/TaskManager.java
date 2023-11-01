package project.manager;

import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.util.List;

public interface TaskManager {
    void addTask(Task task); //POST /tasks/task

    void addEpic(Epic epic); //POST /tasks/epic

    void addSubtask(Subtask subtask); //POST /tasks/subtask/?id=

    List<Task> getAllTasks(); //GET /tasks/task

    Task getTask(Integer taskId); // GET /tasks/?id=

    List<Task> getSubtasksByEpic(Integer epicId); //GET /tasks/epic/subtasks

    void removeAllTasks(); // DELETE /tasks/task

    void removeTask(Integer taskId); //DELETE /tasks/?id=

    void updateTask(int taskId, TaskStatus status, Task task); //POST /tasks/task/?id=&status=

    void updateEpic(int taskId, Epic epic); //POST /tasks/epic/?id=

    void updateSubtask(int taskId, TaskStatus status, Subtask subtask); //POST /tasks/task/?id=&status=

    HistoryManager getHistoryManager(); //GET /tasks/history

    List<Task> getPrioritizedTasks(); //GET /tasks
}



