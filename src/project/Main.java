package project;

import project.manager.Managers;
import project.task.*;

public class Main {
    public static void main(String[] args) {
        Managers.getDefault().addEpic(new Epic("Бег", "Подготовиться к беговым тренировкам"));
        Managers.getDefault().addSubtask(
                new Subtask("Расписание", "Составить расписание беговых тренировок", 1));
        Managers.getDefault().addEpic(new Epic("Построить дом", "План строительства дома"));
        Managers.getDefault().addSubtask(
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 3));
        Managers.getDefault().addSubtask(
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 3));
        Managers.getDefault().addTask(
                new Task("Новая работа", "Пройти курс по JAVA-разработке и найти новую работу"));
        Managers.getDefault().addTask(new Task("Похудеть", "Начать бегать и хорошо питаться"));

        /*System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getSubtasksByEpic(1));
        System.out.println(taskManager.getSubtasksByEpic(3));

        taskManager.updateSubtask(2, TaskStatus.DONE,
                new Subtask("Расписание", "Составить расписание беговых тренировок"));

        taskManager.updateSubtask(4, TaskStatus.DONE,
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка"));
        taskManager.updateSubtask(5, TaskStatus.IN_PROGRESS,
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента"));
        taskManager.updateTask(6, TaskStatus.IN_PROGRESS,
                new Task("Новая работа", "Пройти курс по JAVA-разработке и найти новую работу"));

        taskManager.removeTask(1);
        taskManager.removeTask(7);*/

        System.out.println(Managers.getDefault().getTask(1));
        System.out.println(Managers.getDefault().getTask(2));
        System.out.println(Managers.getDefault().getTask(3));

        System.out.println(Managers.getDefaultHistoryManager().getHistory());
    }
}