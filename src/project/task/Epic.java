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
    public String toString() {
        return "\nЗадача #" + id +
                "\nНазвание задачи: " + '\'' + name + '\'' +
                "\nОписание задачи: " + '\'' + description + '\'' +
                "\nКоличество подзадач: " + subtasksKeysList.size() +
                "\nСтатус: " + taskStatus.getStatus();
    }
}
