package project.manager.util;

import project.manager.FileBackedTaskManager;
import project.manager.HistoryManager;
import project.manager.Managers;
import project.task.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Backup {
    public static String historyTostring(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        if (!manager.getHistory().isEmpty()) {
            for (Task task : manager.getHistory()) {
                history.append(task.getId() + ",");
            }
            return history.delete(history.length() - 1, history.length()).toString();
        }
        return history.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> historyAsArray = new ArrayList<>();
        try {
            for (String field : value.split(",")) {
                historyAsArray.add((parseInt(field)));
            }
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }
        return historyAsArray;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        Map<Integer, Task> tasksMap = new HashMap<>();
        Map<Integer, Epic> epicsMap = new HashMap<>();
        Map<Integer, Subtask> subtasksMap = new HashMap<>();
        int maxId = 0;
        List<Integer> historyIdList = new ArrayList<>();
        List<String> taskFieldsList = new ArrayList<>(taskFieldsFromFile(file));
        if (taskFieldsList.size() > 1) {
            if (taskFieldsList.get(taskFieldsList.size() - 2).isEmpty()) { //Проверка на наличие разделителя между задачами и историей
                historyIdList = historyFromString(taskFieldsList.get(taskFieldsList.size() - 1));
                taskFieldsList.remove(taskFieldsList.size() - 1);
                taskFieldsList.remove(taskFieldsList.size() - 1);
            }
            taskFieldsList.remove(0);
            for (String taskFields : taskFieldsList) {
                String[] field = taskFields.split(",");
                int id = parseInt(field[0]);
                TaskType type = TaskType.valueOf(field[1]);
                String name = field[2];
                TaskStatus status = TaskStatus.valueOf(field[3]);
                String description = field[4];
                if (id > maxId) {
                    maxId = id;
                }
                switch (type) {
                    case TASK:
                        Task task = new Task(name, description);
                        task.setId(id);
                        task.setTaskStatus(status);
                        tasksMap.put(id, task);
                        break;
                    case EPIC:
                        Epic epic = new Epic(name, description);
                        epic.setId(id);
                        epic.setTaskStatus(status);
                        epic.setSubtasksKeysList(new ArrayList<>());
                        epicsMap.put(id, epic);
                        break;
                    case SUBTASK:
                        int epicId = parseInt(field[5]);
                        Subtask subtask = new Subtask(name, description, epicId);
                        subtask.setId(id);
                        subtask.setTaskStatus(status);
                        epicsMap.get(epicId).getSubtasksKeysList().add(id);
                        subtasksMap.put(id, subtask);
                }
            }
            if (!historyIdList.isEmpty()) {
                CustomLinkedList<Task> historyTasksList = new CustomLinkedList<>();
                for (Integer id : historyIdList) {
                    if (tasksMap.containsKey(id)) {
                        historyTasksList.linkLast(tasksMap.get(id));
                    } else if (epicsMap.containsKey(id)) {
                        historyTasksList.linkLast(epicsMap.get(id));
                    } else if (subtasksMap.containsKey(id)) {
                        historyTasksList.linkLast(subtasksMap.get(id));
                    }
                }
                historyManager.setHistoryList(historyTasksList);
            }
            return new FileBackedTaskManager(tasksMap, epicsMap, subtasksMap, historyManager, ++maxId);
        } else {
            System.out.println("Список задач пуст");
            return new FileBackedTaskManager();
        }

    }

    private static List<String> taskFieldsFromFile(File file) {
        List<String> tasksFieldsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                tasksFieldsList.add(reader.readLine());
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return tasksFieldsList;
    }
}
