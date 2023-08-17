import java.util.HashMap;

public class Task {
    private String taskName;
    private String taskDescription;
    private int taskId;
    private TaskStatus taskStatus = TaskStatus.NEW;

    private HashMap<Integer, Task> tasksList = new HashMap<>();


    public Task(String taskName, String taskDescription, int taskId) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
    }
    public Task(String taskName, String taskDescription, int taskId, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Задача #" + taskId +
                "\nНазвание задачи: " + '\'' + taskName + '\'' +
                "\nОписание задачи: " + '\'' + taskDescription + '\'' +
                "\nСтатус: " + taskStatus.getStatus();
    }

    public HashMap<Integer, Task> getTasksList() {
        return tasksList;
    }

    void removeTask(Integer taskId){
        if (tasksList.get(taskId)!=null){
            tasksList.remove(taskId);
        }
    }
    Task getTask(int taskId){
        return tasksList.get(taskId);
    }






}
