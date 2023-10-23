//package project.manager;
//
//import project.manager.util.CSVTaskUtil;
//import project.manager.util.CustomLinkedList;
//import project.manager.util.ManagerSaveException;
//import project.task.*;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static java.lang.Integer.parseInt;
//import static project.manager.util.CSVTaskUtil.historyFromString;
//import static project.manager.util.CSVTaskUtil.taskFieldsFromFile;
//
//public class FileBackedTaskManager extends InMemoryTaskManager {
//    private final static String HEADER = "id,type,name,status,description,epicId\n";
//
//    public FileBackedTaskManager() {
//    }
//
//    public FileBackedTaskManager(Map<Integer, Task> taskMap, Map<Integer, Epic> epicMap, Map<Integer
//            , Subtask> subtaskMap, HistoryManager historyManager, int id) {
//        super(taskMap, epicMap, subtaskMap, historyManager, id);
//    }
//
//    public static FileBackedTaskManager loadFromFile(File file) {
//        HistoryManager historyManager = Managers.getDefaultHistoryManager();
//        Map<Integer, Task> tasksMap = new HashMap<>();
//        Map<Integer, Epic> epicsMap = new HashMap<>();
//        Map<Integer, Subtask> subtasksMap = new HashMap<>();
//        int maxId = 0;
//        List<Integer> historyIdList = new ArrayList<>();
//        List<String> taskFieldsList = new ArrayList<>(taskFieldsFromFile(file));
//        if (taskFieldsList.isEmpty()) {
//            System.out.println("Список задач пуст");
//            return new FileBackedTaskManager();
//        }
//        if (taskFieldsList.get(taskFieldsList.size() - 2).isEmpty()) {
//            historyIdList = historyFromString(taskFieldsList.get(taskFieldsList.size() - 1));
//            taskFieldsList.remove(taskFieldsList.size() - 1);
//            taskFieldsList.remove(taskFieldsList.size() - 1);
//        }
//        taskFieldsList.remove(0);
//        for (String taskFields : taskFieldsList) {
//            String[] field = taskFields.split(",");
//            int id = parseInt(field[0]);
//            TaskType type = TaskType.valueOf(field[1]);
//            String name = field[2];
//            TaskStatus status = TaskStatus.valueOf(field[3]);
//            String description = field[4];
//            if (id > maxId) {
//                maxId = id;
//            }
//            switch (type) {
//                case TASK:
//                    Task task = new Task(name, description);
//                    task.setId(id);
//                    task.setTaskStatus(status);
//                    tasksMap.put(id, task);
//                    break;
//                case EPIC:
//                    Epic epic = new Epic(name, description);
//                    epic.setId(id);
//                    epic.setTaskStatus(status);
//                    epic.setSubtasksKeysList(new ArrayList<>());
//                    epicsMap.put(id, epic);
//                    break;
//                case SUBTASK:
//                    int epicId = parseInt(field[5]);
//                    Subtask subtask = new Subtask(name, description, epicId);
//                    subtask.setId(id);
//                    subtask.setTaskStatus(status);
//                    epicsMap.get(epicId).getSubtasksKeysList().add(id);
//                    subtasksMap.put(id, subtask);
//            }
//        }
//        if (!historyIdList.isEmpty()) {
//            CustomLinkedList<Task> historyTasksList = new CustomLinkedList<>();
//            for (Integer id : historyIdList) {
//                if (tasksMap.containsKey(id)) {
//                    historyTasksList.linkLast(tasksMap.get(id));
//                } else if (epicsMap.containsKey(id)) {
//                    historyTasksList.linkLast(epicsMap.get(id));
//                } else if (subtasksMap.containsKey(id)) {
//                    historyTasksList.linkLast(subtasksMap.get(id));
//                }
//            }
//            historyManager.setHistoryList(historyTasksList);
//        }
//        return new FileBackedTaskManager(tasksMap, epicsMap, subtasksMap, historyManager, ++maxId);
//    }
//
//    public void save() {
//        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(CSVTaskUtil.getBackupPath()
//                + CSVTaskUtil.getFileName()))) {
//            List<Task> list = new ArrayList<>(getSortedList());
//            fileWriter.write(HEADER);
//            if (!list.isEmpty()) {
//                for (Task task : list) {
//                    fileWriter.write(task.toStringForBack() + "\n");
//                }
//                fileWriter.write("\n");
//                if (getHistoryManager() != null) {
//                    fileWriter.write(CSVTaskUtil.historyTostring(getHistoryManager()));
//                }
//            }
//        } catch (IOException e) {
//            throw new ManagerSaveException("Ошибка сохранения файла: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void addTask(Task task) {
//        super.addTask(task);
//        save();
//    }
//
//    @Override
//    public void addEpic(Epic epic) {
//        super.addEpic(epic);
//        save();
//    }
//
//    @Override
//    public void addSubtask(Subtask subtask) {
//        super.addSubtask(subtask);
//        save();
//    }
//
//    @Override
//    public void removeAllTasks() {
//        super.removeAllTasks();
//        save();
//    }
//
//    @Override
//    public void removeTask(Integer taskId) {
//        super.removeTask(taskId);
//        save();
//    }
//
//    @Override
//    public void updateTask(int taskId, TaskStatus status, Task task) {
//        super.updateTask(taskId, status, task);
//        save();
//    }
//
//    @Override
//    public void updateEpic(int taskId, Epic epic) {
//        super.updateEpic(taskId, epic);
//        save();
//    }
//
//    @Override
//    public void updateSubtask(int taskId, TaskStatus status, Subtask subtask) {
//        super.updateSubtask(taskId, status, subtask);
//        save();
//    }
//
//    @Override
//    public Task getTask(Integer taskId) {
//        Task task = super.getTask(taskId);
//        save();
//        return task;
//    }
//
//    @Override
//    public List<Task> getSubtasksByEpic(Integer taskId) {
//        List<Task> subtasksList = super.getSubtasksByEpic(taskId);
//        save();
//        return subtasksList;
//    }
//}
//
//
//
//
