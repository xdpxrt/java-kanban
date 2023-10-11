package project.manager;

import project.manager.util.CSVTaskUtil;
import project.manager.util.ManagerSaveException;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final static String HEADER = "id,type,name,status,description,epicId\n";

    //  Пустой конструктор не доступен по умолчанию, так как есть еще один конструктор ниже
    public FileBackedTaskManager() {
    }

    public FileBackedTaskManager(Map<Integer, Task> taskMap, Map<Integer, Epic> epicMap, Map<Integer
            , Subtask> subtaskMap, HistoryManager historyManager, int id) {
        super(taskMap, epicMap, subtaskMap, historyManager, id);
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(CSVTaskUtil.getBackupPath()
                + CSVTaskUtil.getFileName()))) {
            List<Task> list = new ArrayList<>(getSortedList());
            fileWriter.write(HEADER);
            if (!list.isEmpty()) {
                for (Task task : list) {
                    fileWriter.write(task.toStringForBack() + "\n");
                }
                fileWriter.write("\n");
                if (getHistoryManager() != null) {
                    fileWriter.write(CSVTaskUtil.historyTostring(getHistoryManager()));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
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

    @Override
    public List<Task> getSubtasksByEpic(Integer taskId) {
        List<Task> subtasksList = super.getSubtasksByEpic(taskId);
        save();
        return subtasksList;
    }
}




