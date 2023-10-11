package project.task;

public class Subtask extends Task {
    private final TaskType type = TaskType.SUBTASK;
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description) {
        super(name, description);
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
    public String toStringForBack() {
        return String.join(",", String.valueOf(id), type.name(), name, taskStatus.name(), description, String.valueOf(epicId));
    }

    @Override
    public String toString() {
        return "\n" + type + " #" + id + " к эпику #" + epicId +
                "\nНазвание задачи: " + '\'' + name + '\'' +
                "\nОписание задачи: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus;
    }
}
