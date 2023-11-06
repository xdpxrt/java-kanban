package project.manager;

import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.util.List;

public interface TaskManager {
    boolean addTask(Task task); //POST /tasks/task

    void addEpic(Epic epic); //POST /tasks/epic

    boolean addSubtask(Subtask subtask); //POST /tasks/subtask/?id=

    List<Task> getAllTasks(); //GET /tasks

    Task getTask(Integer taskId); // GET /tasks/?id=

    List<Task> getSubtasksByEpic(Integer epicId); //GET /tasks/epic/getSubtasks

    void removeAllTasks(); // DELETE /tasks

    void removeTask(Integer taskId); //DELETE /tasks/?id=

    boolean updateTask(int taskId, TaskStatus status, Task task); //POST /tasks/task/?id=&status=

    void updateEpic(int taskId, Epic epic); //POST /tasks/epic/?id=

    boolean updateSubtask(int taskId, TaskStatus status, Subtask subtask); //POST /tasks/subtask/?id=&status=

    HistoryManager getHistoryManager(); //GET /tasks/history

    List<Task> getPrioritizedTasks(); //GET /tasks/priority
}



