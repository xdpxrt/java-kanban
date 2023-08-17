import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask("Расписание", "Составить расписание беговых тренировок");
        Task task = new Task(1,"Расписание", "Составить расписание силовых тренировок", TaskStatus.IN_PROGRESS);
        taskManager.updateTask(1, task);
        taskManager.allTasks();
    }
}
