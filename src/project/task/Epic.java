package project.task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final TaskType type = TaskType.EPIC;
    private List<Integer> subtasksKeysList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Integer> getSubtasksKeysList() {
        return subtasksKeysList;
    }

    public void setSubtasksKeysList(List<Integer> subtasksKeysList) {
        this.subtasksKeysList = subtasksKeysList;
    }

    public String toStringForBack() {
        return String.join(",", String.valueOf(id), type.name(), name, taskStatus.name(), description);
    }

    @Override
    public String toString() {
        return "\n" + type + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nКоличество подзадач: " + subtasksKeysList.size() +
                "\nСтатус: " + taskStatus;
    }
}
