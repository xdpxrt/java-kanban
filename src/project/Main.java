package project;

import project.manager.FileBackedTaskManager;
import project.manager.util.CSVTaskUtil;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager();
        taskManager.addTask(new Task("Бег", "Подготовиться к беговым тренировкам"));
        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
        taskManager.addSubtask(
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 2));
        taskManager.addSubtask(new Subtask("Поставить забор", "Выбрать вид забора", 2));
        taskManager.addSubtask(
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 2));
        taskManager.addTask(new Task("Отпуск", "Купить билеты на море"));

        taskManager.updateTask(1, TaskStatus.DONE
                , new Task("Бег", "Подготовиться к беговым тренировкам"));
        taskManager.updateSubtask(3, TaskStatus.DONE
                , new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 2));
        taskManager.updateTask(6, TaskStatus.IN_PROGRESS
                , new Task("Отпуск", "Купить билеты на море"));

        taskManager.getTask(1);
        taskManager.getTask(5);
        taskManager.getTask(2);

//        Считывание и формирование менеджера из файла
        FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(new File(CSVTaskUtil.getBackupPath()
                , CSVTaskUtil.getFileName()));
    }
}