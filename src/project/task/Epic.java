package project.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public String getStringDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return ZERO_DATE;
        else return dateTime.format(DATE_TIME_FORMATTER);
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
                , startTime.format(DATE_TIME_FORMATTER), String.valueOf(duration), endTime.format(DATE_TIME_FORMATTER));
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
}
