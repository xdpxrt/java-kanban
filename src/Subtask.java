import java.util.HashMap;

public class Subtask extends Task {
    private int epicID;

     private HashMap<Integer, Subtask> subtasksList = new HashMap<>();

    public Subtask(String taskName, String taskDescription, int taskId, int epicID) {
        super(taskName, taskDescription, taskId);
        this.epicID = epicID;
    }
    public Subtask() {
    }
        public HashMap<Integer, Subtask> getSubtasksList() {
        return subtasksList;
    }
}
