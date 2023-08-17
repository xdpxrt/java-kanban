public class Task {
    private String taskName;
    private String taskDescription;
    private int taskId;
    TaskStatus taskStatus = TaskStatus.NEW;


    public Task(String taskName, String taskDescription, int taskId) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
    }

    public Task(int taskId, String taskName, String taskDescription, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "\nЗадача #" + taskId +
                "\nНазвание задачи: " + '\'' + taskName + '\'' +
                "\nОписание задачи: " + '\'' + taskDescription + '\'' +
                "\nСтатус: " + taskStatus.getStatus();
    }
}
