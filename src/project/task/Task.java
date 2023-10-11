package project.task;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus taskStatus = TaskStatus.NEW;
    private final TaskType type = TaskType.TASK;

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

    public String toStringForBack() {
        return String.join(",", String.valueOf(id), type.name(), name, taskStatus.name(), description);
    }

    @Override
    public String toString() {
        return "\n" + type + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus;
    }
}
