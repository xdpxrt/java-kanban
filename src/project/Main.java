package project;

import project.manager.TaskManager;
import project.task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addEpic(new Epic("Бег", "Подготовиться к беговым тренировкам"));
        taskManager.addSubtask(
                new Subtask("Расписание", "Составить расписание беговых тренировок", 1));
        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
        taskManager.addSubtask(
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 3));
        taskManager.addSubtask(
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 3));
        taskManager.addTask(
                new Task("Новая работа", "Пройти курс по JAVA-разработке и найти новую работу"));
        taskManager.addTask(new Task("Похудеть", "Начать бегать и хорошо питаться"));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getSubtasksByEpic(1));
        System.out.println(taskManager.getSubtasksByEpic(3));

        taskManager.updateSubtask(TaskStatus.DONE,
                new Subtask(2, "Расписание", "Составить расписание беговых тренировок"));

        System.out.println(taskManager.getTask(2));

        taskManager.updateSubtask(TaskStatus.DONE,
                new Subtask(4, "Выровнять участок", "Заказать трактор для выравнивания участка"));
        taskManager.updateSubtask(TaskStatus.IN_PROGRESS,
                new Subtask(5, "Установить фундамент", "Вызвать бригаду по заливке фундамента"));
        taskManager.updateTask(6, TaskStatus.IN_PROGRESS,
                new Task("Новая работа", "Пройти курс по JAVA-разработке и найти новую работу"));

        System.out.println(taskManager.getTask(3));

        taskManager.removeTask(1);
        taskManager.removeTask(7);
    }
}
