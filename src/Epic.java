import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;
    private HashMap<Integer, Epic> epicsList = new HashMap<>();

    public Epic(String taskName, String taskDescription, int taskId, ArrayList<Subtask> subtasksList) {
        super(taskName, taskDescription, taskId);
        this.subtasks = subtasksList;
    }

    public Epic() {
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                '}';
    }

    public HashMap<Integer, Epic> getEpicsList() {
        return epicsList;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

}
