package project.task;

import project.util.TaskTimeFormatter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Task {
    protected String name;
    protected String description;
    protected int id;

    protected TaskStatus taskStatus = TaskStatus.NEW;
    protected int duration;

    protected LocalDateTime startTime;

    public Task(String name, String description, Integer duration, String startTime) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        try {
            this.startTime = LocalDateTime.parse(startTime, TaskTimeFormatter.DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            this.startTime = null;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(Duration.ofMinutes(duration));
    }

    public String toStringForBack() {
        return String.join(",", String.valueOf(id), getTaskType().name(), name, taskStatus.name(), description);
    }

    @Override
    public String toString() {
        return "\n" + getTaskType() + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus +
                "\nДата начала: " + startTime.format(TaskTimeFormatter.DATE_TIME_FORMATTER) +
                "\nПродолжительность: " + Duration.ofMinutes(duration).toHours()
                + ':' + Duration.ofMinutes(duration).toMinutesPart();
    }
}
