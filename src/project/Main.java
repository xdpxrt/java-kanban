package project;

import project.manager.*;
import project.task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.getHistoryManager();

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

        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(3));

        System.out.println(historyManager.getHistory());
    }
}