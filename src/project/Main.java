package project;

import project.manager.FileBackedTaskManager;
import project.manager.TaskManager;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.util.CSVTaskUtil;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new FileBackedTaskManager();

        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
        taskManager.addSubtask(
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 0, "00.00.00 00:00", 1));
        taskManager.addSubtask(new Subtask("Поставить забор", "Выбрать вид забора", 450, "22.09.2023 16:00", 1));
        taskManager.addSubtask(
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 600, "09.10.2023 11:30", 1));


//        taskManager.addTask(new Task("Отпуск", "Купить билеты", 0, "00.00.00 00:00"));
//        taskManager.addTask(new Task("Бег", "Подготовиться к беговым тренировкам", 30, "21.06.2023 10:40"));

//        System.out.println(taskManager.getTask(1));

//        taskManager.getTask(4);
//        taskManager.getTask(4);
//        taskManager.getTask(5);

//        System.out.println(taskManager.getHistoryManager().getHistory());


//        Считывание и формирование менеджера из файла
       FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(new File(CSVTaskUtil.getBackupPath()
                , CSVTaskUtil.getFileName()));
        System.out.println(taskManagerFromFile.getTask(1));
    }
}