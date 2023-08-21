package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksKeysList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtasksKeysList() {
        return subtasksKeysList;
    }

    public void setSubtasksKeysList(ArrayList<Integer> subtasksKeysList) {
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
