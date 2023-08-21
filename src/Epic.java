import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksKeysList = new ArrayList<>();

    public Epic(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return "\nЗадача #" + taskId +
                "\nНазвание задачи: " + '\'' + taskName + '\'' +
                "\nОписание задачи: " + '\'' + taskDescription + '\'' +
                "\nКоличество подзадач: " + subtasksKeysList.size() +
                "\nСтатус: " + taskStatus.getStatus();
    }

    public ArrayList<Integer> getSubtasksKeysList() {
        return subtasksKeysList;
    }

    public void setSubtasksKeysList(ArrayList<Integer> subtasksKeysList) {
        this.subtasksKeysList = subtasksKeysList;
    }
}
