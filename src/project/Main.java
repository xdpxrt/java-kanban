package project;

//import project.manager.FileBackedTaskManager;

import project.manager.Managers;
import project.manager.TaskManager;
import project.task.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
        taskManager.addSubtask(
                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 180, "21.09.2023 15:00", 1));
        taskManager.addSubtask(new Subtask("Поставить забор", "Выбрать вид забора", 450, "11.08.2023 09:00", 1));
        taskManager.addSubtask(
                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 600, "09.10.2023 11:30", 1));
        taskManager.addTask(new Task("Бег", "Подготовиться к беговым тренировкам", 30, "21.06.2023 10:40"));
        taskManager.addTask(new Task("Отпуск", "Купить билеты", 40, "10.06.2023 10:40"));

        System.out.println(taskManager.getPrioritizedTasks());

//        FileBackedTaskManager taskManager = new FileBackedTaskManager();
//        taskManager.addTask(new Task("Бег", "Подготовиться к беговым тренировкам"));
//        taskManager.addEpic(new Epic("Построить дом", "План строительства дома"));
//        taskManager.addSubtask(
//                new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 2));
//        taskManager.addSubtask(new Subtask("Поставить забор", "Выбрать вид забора", 2));
//        taskManager.addSubtask(
//                new Subtask("Установить фундамент", "Вызвать бригаду по заливке фундамента", 2));
//        taskManager.addTask(new Task("Отпуск", "Купить билеты на море"));
//
//        taskManager.updateTask(1, TaskStatus.DONE
//                , new Task("Бег", "Подготовиться к беговым тренировкам"));
//        taskManager.updateSubtask(3, TaskStatus.DONE
//                , new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка", 2));
//        taskManager.updateTask(6, TaskStatus.IN_PROGRESS
//                , new Task("Отпуск", "Купить билеты на море"));
//
//        taskManager.getTask(1);
//        taskManager.getTask(5);
//        taskManager.getTask(2);
//
////        Считывание и формирование менеджера из файла
//        FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(new File(CSVTaskUtil.getBackupPath()
//                , CSVTaskUtil.getFileName()));
    }
}