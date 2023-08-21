package project.task;

public class Subtask extends Task {
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

    @Override
    public String toString() {
        return "\nПодзадача #" + id + " к задаче #" + epicId +
                "\nНазвание задачи: " + '\'' + name + '\'' +
                "\nОписание задачи: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus.getStatus();
    }
}
