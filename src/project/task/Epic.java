package project.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static project.util.TaskTimeFormatter.*;

public class Epic extends Task {
    private List<Integer> subtasksKeysList = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, 0, ZERO_DATE);
    }

    public List<Integer> getSubtasksKeysList() {
        return subtasksKeysList;
    }

    public void setSubtasksKeysList(List<Integer> subtasksKeysList) {
        this.subtasksKeysList = subtasksKeysList;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    @Override
    public String toStringForBack() {
        return String.join(",", String.valueOf(id), getTaskType().name(), name, taskStatus.name(), description
                , String.valueOf(duration), getStringDateTime(startTime));
    }

    @Override
    public String toString() {
        return "\n" + getTaskType() + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nКоличество подзадач: " + subtasksKeysList.size() +
                "\nСтатус: " + taskStatus +
                "\nДата начала: " + getStringDateTime(startTime) +
                "\nПродолжительность: " + getDurationToString() +
                "\nДата окончания: " + getStringDateTime(endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksKeysList, epic.subtasksKeysList) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksKeysList, endTime);
    }

}
