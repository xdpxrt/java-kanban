public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return "\nПодзадача #" + taskId + " к задаче #" + epicId +
                "\nНазвание задачи: " + '\'' + taskName + '\'' +
                "\nОписание задачи: " + '\'' + taskDescription + '\'' +
                "\nСтатус: " + taskStatus.getStatus();
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
