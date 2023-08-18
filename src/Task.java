public class Task {
    protected String taskName;
    protected String taskDescription;
    protected int taskId;
    TaskStatus taskStatus = TaskStatus.NEW;

    public Task() {
    }

    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }
    public Task(String taskName, String taskDescription, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "\nЗадача #" + taskId +
                "\nНазвание задачи: " + '\'' + taskName + '\'' +
                "\nОписание задачи: " + '\'' + taskDescription + '\'' +
                "\nСтатус: " + taskStatus.getStatus();
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
