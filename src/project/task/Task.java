package project.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static project.util.TaskTimeFormatter.*;

public class Task implements Comparable<Task> {
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
            this.startTime = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
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

    public String getDurationToString() {
        return  String.valueOf(Duration.ofMinutes(duration).toHours()) + ':' + Duration.ofMinutes(duration).toMinutesPart();
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(Duration.ofMinutes(duration));
    }

    public String toStringForBack() {
        return String.join(",", String.valueOf(id), getTaskType().name(), name, taskStatus.name(), description
                , String.valueOf(duration), getStringDateTime(startTime));
    }

    public String getStringDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return ZERO_DATE;
        else return dateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "\n" + getTaskType() + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus +
                "\nДата начала: " + getStringDateTime(startTime) +
                "\nПродолжительность: " + getDurationToString();
    }

    @Override
    public int compareTo(Task task) {
        if (this.startTime == null) return 1;
        if (this.startTime.isAfter(task.startTime)) return 1;
        else if (this.startTime.isBefore(task.startTime)) return -1;
        else return 0;
    }
}
