package project.task;

import project.task.util.TaskTimeFormatter;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus taskStatus = TaskStatus.NEW;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String name, String description, Integer duration, String startTime) {
        this.name = name;
        this.description = description;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = LocalDateTime.parse(startTime, TaskTimeFormatter.TIME_FORMATTER);
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

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public String toStringForBack() {
        return String.join(",", String.valueOf(id), getTaskType().name(), name, taskStatus.name(), description);
    }

    @Override
    public String toString() {
        return "\n" + getTaskType() + " #" + id +
                "\nНазвание: " + '\'' + name + '\'' +
                "\nОписание: " + '\'' + description + '\'' +
                "\nСтатус: " + taskStatus;
    }
}
