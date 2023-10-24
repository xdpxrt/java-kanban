package project.task;

import static project.util.TaskTimeFormatter.DATE_TIME_FORMATTER;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Integer duration, String startTime, int epicId) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toStringForBack() {
        return String.join(",", String.valueOf(id), getTaskType().name(), name, taskStatus.name(), description
                , String.valueOf(duration), startTime.format(DATE_TIME_FORMATTER), String.valueOf(epicId));
    }

    @Override
    public String toString() {
        return "\n" + getTaskType() + " #" + id + " к эпику #" + epicId +
                "\nНазвание задачи: " + '\'' + name + '\'' +
                "\nОписание задачи: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus +
                "\nДата начала: " + startTime.format(DATE_TIME_FORMATTER) +
                "\nПродолжительность: " + getDurationToString();
    }
}
