import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicsList = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksList = new HashMap<>();


    Task task = new Task();
    Epic epic = new Epic();
    Subtask subtask = new Subtask();
    int taskId = 1;

    void addTask(String taskName, String taskDescription) {
        Task task = new Task(taskName, taskDescription, taskId);
        tasksList.put(taskId++, task);
    }

    void addEpic(String taskName, String taskDescription) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = new Epic(taskName, taskDescription, taskId, subtasksList);
        epicsList.put(taskId++, epic);
    }

    void addSubtask(String taskName, String taskDescription, int epicId) {
        Subtask subtask = new Subtask(taskName, taskDescription, taskId, epicId);
        subtasksList.put(taskId, subtask);
    }

    ArrayList<Task> allTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int id : tasksList.keySet()) {
            allTasks.add(tasksList.get(id));
        }
        return allTasks;
    }

    void updateTask(int taskId, Task task) {
        tasksList.put(taskId, task);
    }

    void updateEpic(int taskId, Epic epic) {
        epicsList.put(taskId, epic);
    }

    void updateTask(int taskId, Subtask subtask) {
        subtasksList.put(taskId, subtask);
    }
}

