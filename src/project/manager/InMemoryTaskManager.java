package project.manager;

import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private HistoryManager historyManager = Managers.getDefaultHistoryManager();
    private Map<Integer, Task> tasksList = new HashMap<>();
    private Map<Integer, Epic> epicsList = new HashMap<>();
    private Map<Integer, Subtask> subtasksList = new HashMap<>();
    private int id = 1;
    private final Set<Task> sortedTasks = new TreeSet<>();

    public InMemoryTaskManager() {
    }

    public InMemoryTaskManager(Map<Integer, Task> taskList, Map<Integer, Epic> epicList
            , Map<Integer, Subtask> subtaskList, HistoryManager historyManager, int id) {
        this.tasksList = taskList;
        this.epicsList = epicList;
        this.subtasksList = subtaskList;
        this.historyManager = historyManager;
        this.id = id;
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public void addTask(Task task) {
        if (checkTaskStartTime(task)) {
            task.setId(id);
            tasksList.put(id++, task);
            sortedTasks.add(task);
            System.out.println("\nСоздана: " + task);
        } else System.out.println("На это время уже стоит задача!");
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(id);
        epicsList.put(id++, epic);
        System.out.println("\nСоздана: " + epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (epicsList.containsKey(epicId)) {
            if (checkTaskStartTime(subtask)) {
                Epic epic = epicsList.get(epicId);
                subtask.setId(id);
                epic.getSubtasksKeysList().add(id);
                subtasksList.put(id++, subtask);
                updateEpicDateInfo(epic, subtask);
                checkTaskStartTime(subtask);
                sortedTasks.add(subtask);
                System.out.println("\nСоздана: " + subtask);
            } else System.out.println("На это время уже стоит задача!");
        } else {
            System.out.println("\nТакого эпика нет");
        }
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        for (int taskId : tasksList.keySet()) {
            allTasks.add(tasksList.get(taskId));
        }
        for (int taskId : epicsList.keySet()) {
            allTasks.add(epicsList.get(taskId));
        }
        for (int taskId : subtasksList.keySet()) {
            allTasks.add(subtasksList.get(taskId));
        }
        if (allTasks.isEmpty()) {
            System.out.println("\nЗадач нет");
            return null;
        } else {
            return allTasks;
        }
    }

    @Override
    public Task getTask(Integer taskId) {
        if (tasksList.containsKey(taskId)) {
            historyManager.addToHistory(tasksList.get(taskId));
            return tasksList.get(taskId);
        }
        if (epicsList.containsKey(taskId)) {
            historyManager.addToHistory(epicsList.get(taskId));
            return epicsList.get(taskId);
        }
        if (subtasksList.containsKey(taskId)) {
            historyManager.addToHistory(subtasksList.get(taskId));
            return subtasksList.get(taskId);
        } else {
            System.out.println("\nТакой задачи нет");
            return null;
        }
    }


    @Override
    public List<Task> getSubtasksByEpic(Integer epicId) {
        if (epicsList.containsKey(epicId)) {
            List<Task> subtasks = new ArrayList<>();
            for (int taskId : epicsList.get(epicId).getSubtasksKeysList()) {
                historyManager.addToHistory(subtasksList.get(taskId));
                subtasks.add(subtasksList.get(taskId));
            }
            return subtasks;
        } else {
            System.out.println("\nТакой задачи нет");
            return new ArrayList<>();
        }
    }

    @Override
    public void removeAllTasks() {
        tasksList.clear();
        epicsList.clear();
        subtasksList.clear();
        historyManager.removeHistory();
        System.out.println("\nВсе задачи удалены");
    }

    @Override
    public void removeTask(Integer taskId) {
        if (tasksList.containsKey(taskId)) {
            tasksList.remove(taskId);
            historyManager.removeFromHistory(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else if (subtasksList.containsKey(taskId)) {
            subtasksList.remove(taskId);
            historyManager.removeFromHistory(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else if (epicsList.containsKey(taskId)) {
            for (Integer task : epicsList.get(taskId).getSubtasksKeysList()) {
                subtasksList.remove(task);
                historyManager.removeFromHistory(task);
            }
            epicsList.remove(taskId);
            historyManager.removeFromHistory(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    @Override
    public void updateTask(int taskId, TaskStatus status, Task task) {
        if (tasksList.containsKey(taskId)) {
            task.setId(taskId);
            task.setTaskStatus(status);
            tasksList.put(taskId, task);
            System.out.println("\nЗадача #" + taskId + " обновлена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    @Override
    public void updateEpic(int taskId, Epic epic) {
        if (epicsList.containsKey(taskId)) {
            epic.setSubtasksKeysList(epicsList.get(taskId).getSubtasksKeysList());
            epic.setId(taskId);
            epicsList.put(taskId, epic);
            System.out.println("\nЗадача #" + taskId + " обновлена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    @Override
    public void updateSubtask(int taskId, TaskStatus status, Subtask subtask) {
        if (subtasksList.containsKey(taskId)) {
            int epicId = subtasksList.get(taskId).getEpicId();
            subtask.setEpicId(epicId);
            subtask.setTaskStatus(status);
            subtask.setId(taskId);
            subtasksList.put(taskId, subtask);
            System.out.println("\nПодадача #" + subtask.getId() + " обновлена");
            checkEpicStatus(epicId);
        } else {
            System.out.println("\nТакой подзадачи нет");
        }
    }

    public List<Task> getSortedList() {
        Map<Integer, Task> sortedMap = new TreeMap<>();
        sortedMap.putAll(tasksList);
        sortedMap.putAll(epicsList);
        sortedMap.putAll(subtasksList);
        return new ArrayList<>(sortedMap.values());
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(sortedTasks);
    }

    private void checkEpicStatus(int epicId) {
        int countNewTasks = 0;
        int countDoneTasks = 0;
        for (Integer subtaskId : epicsList.get(epicId).getSubtasksKeysList()) {
            if (subtasksList.get(subtaskId).getTaskStatus() == TaskStatus.NEW) {
                ++countNewTasks;
            }
            if (subtasksList.get(subtaskId).getTaskStatus() == TaskStatus.DONE) {
                ++countDoneTasks;
            }
        }
        if (epicsList.get(epicId).getSubtasksKeysList().isEmpty()
                || countNewTasks == epicsList.get(epicId).getSubtasksKeysList().size()) {
            epicsList.get(epicId).setTaskStatus(TaskStatus.NEW);
        } else if (countDoneTasks == epicsList.get(epicId).getSubtasksKeysList().size()) {
            epicsList.get(epicId).setTaskStatus(TaskStatus.DONE);
        } else {
            epicsList.get(epicId).setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private void updateEpicDateInfo(Epic epic, Subtask subtask) {
        epic.setDuration(epic.getDuration() + subtask.getDuration());
        if (epic.getStartTime() == null || epic.getStartTime().isAfter(subtask.getStartTime())) {
            epic.setStartTime(subtask.getStartTime());
        }
        if (epic.getEndTime() == null || epic.getEndTime().isBefore(subtask.getEndTime())) {
            epic.setEndTime(subtask.getEndTime());
        }
    }

    private boolean checkTaskStartTime(Task newTask) {
        boolean isFree = false;
        if (sortedTasks.isEmpty()) {
            return true;
        }
        for (Task task : sortedTasks) {
            isFree = newTask.getStartTime().isBefore(task.getStartTime())
                    || newTask.getStartTime().isAfter(task.getEndTime());
        }
        return isFree;
    }
}
