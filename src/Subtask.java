public class Subtask extends Task {
    private int epicId;
     public Subtask(String taskName, String taskDescription, int taskId, int epicId) {
        super(taskName, taskDescription, taskId);
        this.epicId = epicId;
    }

    public Subtask(int taskId, String taskName, String taskDescription, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskId);
        this.taskStatus = taskStatus;
    }

    public Subtask() {
    }
}
