import java.util.ArrayList;

public class TaskManager {
    Task task = new Task();
    Epic epic = new Epic();
    Subtask subtask = new Subtask();
    int taskId = 1;

    void addTask(String taskName, String taskDescription) {
        Task task = new Task(taskName, taskDescription, taskId);
        task.getTasksList().put(taskId++, task);
        System.out.println(task);
    }

    void addEpic(String taskName, String taskDescription) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = new Epic(taskName, taskDescription, taskId, subtasksList);
        epic.getEpicsList().put(taskId++, epic);
    }

    void addSubtask(String taskName, String taskDescription, int epicId) {
        Subtask subtask = new Subtask(taskName, taskDescription, taskId, epicId);
        subtask.getSubtasksList().put(taskId, subtask);
        epic.getEpicsList().get(epicId).getSubtasks().add(subtask);
    }

    ArrayList<Task> allTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int id : task.getTasksList().keySet()) {
            allTasks.add(task.getTasksList().get(id));
        }
        return allTasks;
    }
}
