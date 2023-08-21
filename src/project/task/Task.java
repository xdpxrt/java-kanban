package project.task;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus taskStatus = TaskStatus.NEW;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    @Override
    public String toString() {
        return "\nЗадача #" + id +
                "\nНазвание задачи: " + '\'' + name + '\'' +
                "\nОписание задачи: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus.getStatus();
    }
}
