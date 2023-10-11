package project.manager;

import project.manager.util.Backup;
import project.manager.util.ManagerSaveException;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final static String BACKUP_PATH = "src\\project\\resources\\";
    private final static String FILE_NAME = "ManagerBackup.csv";

    public FileBackedTaskManager() {
    }

    public FileBackedTaskManager(Map<Integer, Task> taskMap, Map<Integer, Epic> epicMap, Map<Integer
            , Subtask> subtaskMap, HistoryManager historyManager, int id) {
        super(taskMap, epicMap, subtaskMap, historyManager, id);
    }

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

        taskManager.updateTask(1, TaskStatus.DONE, new Task("Бег", "Подготовиться к беговым тренировкам"));
        taskManager.updateSubtask(3, TaskStatus.DONE, new Subtask("Выровнять участок", "Заказать трактор для выравнивания участка"));
        taskManager.updateTask(6, TaskStatus.IN_PROGRESS, new Task("Отпуск", "Купить билеты на море"));

        taskManager.getTask(1);
        taskManager.getTask(5);
        taskManager.getTask(2);


//        Считывание данных  из файла
//        FileBackedTaskManager taskManager = Backup.loadFromFile(new File(BACKUP_PATH, FILE_NAME));

    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(BACKUP_PATH + FILE_NAME))) {
            List<Task> list = new ArrayList<>(getSortedList());
            fileWriter.write("id,type,name,status,description,epicId\n");
            if (!list.isEmpty()) {
                for (Task task : list) {
                    fileWriter.write(task.toStringForBack() + "\n");
                }
                fileWriter.write("\n");
                if (getHistoryManager() != null) {
                    fileWriter.write(Backup.historyTostring(getHistoryManager()));
                }
            }
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка сохранения файла: " + exp.getMessage());
        }
    }

    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeTask(Integer taskId) {
        super.removeTask(taskId);
        save();
    }

    @Override
    public void updateTask(int taskId, TaskStatus status, Task task) {
        super.updateTask(taskId, status, task);
        save();
    }

    @Override
    public void updateEpic(int taskId, Epic epic) {
        super.updateEpic(taskId, epic);
        save();
    }

    @Override
    public void updateSubtask(int taskId, TaskStatus status, Subtask subtask) {
        super.updateSubtask(taskId, status, subtask);
        save();
    }

    @Override
    public Task getTask(Integer taskId) {
        Task task = super.getTask(taskId);
        save();
        return task;
    }

    public List<Task> getSubtasksByEpic(Integer taskId) {
        List<Task> subtasksList = super.getSubtasksByEpic(taskId);
        save();
        return subtasksList;
    }
}




