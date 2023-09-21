package project;

import project.manager.*;
import project.task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.getHistoryManager();

        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
        taskManager.addSubtask(
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 1));
        taskManager.addSubtask(new Subtask("Поставить забор", "Выбрать вид забора", 1));
        taskManager.addSubtask(
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 1));

        taskManager.addEpic(new Epic("Бег", "Подготовиться к беговым тренировкам"));

        System.out.println(taskManager.getTask(1));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(5));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(2));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(3));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(5));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(1));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(1));
        System.out.println(historyManager.getHistory());

        System.out.println(taskManager.getTask(2));
        System.out.println(historyManager.getHistory());

        taskManager.removeTask(5);
        System.out.println(historyManager.getHistory());

        taskManager.removeTask(1);
        System.out.println(historyManager.getHistory());
    }
}