import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Расписание", "Составить расписание силовых тренировок");
        taskManager.addTask(task1);

        Epic epic1 = new Epic("Построить дом", "План строительства дома");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка");
        taskManager.addSubtask(2, subtask1);

        //System.out.println(taskManager.getAllTasks());
        //taskManager.removeAllTasks();
        //System.out.println(taskManager.getAllTasks());*/
        //System.out.println(taskManager.getTask(3));
        Task task2 = new Task("Расписание", "Составить расписание силовых тренировок", TaskStatus.DONE);
        taskManager.updateTask(1, task2);
        Epic epic2 = new Epic("Построить дом", "План строительства загородного дома");
        taskManager.updateEpic(2, epic2);
        Subtask subtask3 = new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента");
        taskManager.addSubtask(2, subtask3);
        Subtask subtask2 = new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", TaskStatus.DONE);
        taskManager.updateSubtask(3, subtask2);
        //Subtask subtask4 = new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", TaskStatus.IN_PROGRESS);
        //taskManager.updateSubtask(4, subtask4);
        //Subtask subtask5 = new Subtask("Установить септик", "Заказать установку септика");
        //taskManager.addSubtask(2, subtask5);

        System.out.println(taskManager.getTask(2));
    }
}
