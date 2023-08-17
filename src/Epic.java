import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(String taskName, String taskDescription, int taskId, ArrayList<Subtask> subtasksList) {
        super(taskName, taskDescription, taskId);
        this.subtasks = subtasksList;
    }

    public Epic(int taskId, String taskName, String taskDescription) {
        super(taskName, taskDescription, taskId);
    }

    public Epic() {
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                '}';
    }
}
