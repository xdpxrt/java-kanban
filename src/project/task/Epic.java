package project.task;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
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

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public String toStringForBack() {
        return String.join(",", String.valueOf(id), getTaskType().name(), name, taskStatus.name(), description);
    }

    @Override
    public String toString() {
        return "\n" + getTaskType() + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nКоличество подзадач: " + subtasksKeysList.size() +
                "\nСтатус: " + taskStatus;
    }
}
