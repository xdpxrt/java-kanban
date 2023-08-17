import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask("Расписание", "Составить расписание беговых тренировок");
        System.out.println(taskManager.allTasks().toString());
        System.out.println(taskManager.task.getTask(1));
    }
}
