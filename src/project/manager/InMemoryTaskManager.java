package project.manager;

import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private HistoryManager historyManager = Managers.getDefaultHistoryManager();
    private Map<Integer, Task> tasksMap = new HashMap<>();
    private Map<Integer, Epic> epicsMap = new HashMap<>();
    private Map<Integer, Subtask> subtasksMap = new HashMap<>();
    private int id = 1;
    private Set<Task> sortedTasks = new TreeSet<>();

    public InMemoryTaskManager() {
    }

    protected InMemoryTaskManager(Map<Integer, Task> taskList, Map<Integer, Epic> epicList
            , Map<Integer, Subtask> subtaskList, HistoryManager historyManager, int id, Set<Task> sortedTasks) {
        this.tasksMap = taskList;
        this.epicsMap = epicList;
        this.subtasksMap = subtaskList;
        this.historyManager = historyManager;
        this.id = id;
        this.sortedTasks = sortedTasks;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public int getId() {
        return id;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(sortedTasks);
    }

    @Override
    public boolean addTask(Task task) {
        if (checkTaskStartTime(task)) {
            task.setId(id);
            tasksMap.put(id++, task);
            sortedTasks.add(task);
            System.out.println("\nСоздана: " + task);
            return true;
        } else System.out.println("На это время уже стоит задача!");
        return false;
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(id);
        epicsMap.put(id++, epic);
        System.out.println("\nСоздана: " + epic);
    }

    @Override
    public boolean addSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (epicsMap.containsKey(epicId)) {
            if (checkTaskStartTime(subtask)) {
                Epic epic = epicsMap.get(epicId);
                subtask.setId(id);
                epic.getSubtasksKeysList().add(id);
                subtasksMap.put(id++, subtask);
                updateEpicDateInfo(epic, subtask);
                sortedTasks.add(subtask);
                System.out.println("\nСоздана: " + subtask);
                return true;
            } else {
                System.out.println("\nНа это время уже стоит задача!");
                return false;
            }
        } else {
            System.out.println("\nТакого эпика нет");
            return false;
        }
    }

    @Override
    public List<Task> getAllTasks() {
        Map<Integer, Task> sortedMap = new TreeMap<>();
        sortedMap.putAll(tasksMap);
        sortedMap.putAll(epicsMap);
        sortedMap.putAll(subtasksMap);
        return new ArrayList<>(sortedMap.values());
    }

    @Override
    public Task getTask(Integer taskId) {
        if (tasksMap.containsKey(taskId)) {
            historyManager.addToHistory(tasksMap.get(taskId));
            return tasksMap.get(taskId);
        }
        if (epicsMap.containsKey(taskId)) {
            historyManager.addToHistory(epicsMap.get(taskId));
            return epicsMap.get(taskId);
        }
        if (subtasksMap.containsKey(taskId)) {
            historyManager.addToHistory(subtasksMap.get(taskId));
            return subtasksMap.get(taskId);
        } else {
            System.out.println("\nТакой задачи нет");
            return null;
        }
    }


    @Override
    public List<Task> getSubtasksByEpic(Integer epicId) {
        if (epicsMap.containsKey(epicId)) {
            List<Task> subtasks = new ArrayList<>();
            for (int taskId : epicsMap.get(epicId).getSubtasksKeysList()) {
                historyManager.addToHistory(subtasksMap.get(taskId));
                subtasks.add(subtasksMap.get(taskId));
            }
            return subtasks;
        } else {
            System.out.println("\nТакой задачи нет");
            return new ArrayList<>();
        }
    }

    @Override
    public void removeAllTasks() {
        tasksMap.clear();
        epicsMap.clear();
        subtasksMap.clear();
        sortedTasks.clear();
        historyManager.removeHistory();
        System.out.println("\nВсе задачи удалены");
    }

    @Override
    public void removeTask(Integer taskId) {
        if (tasksMap.containsKey(taskId)) {
            sortedTasks.remove(tasksMap.get(taskId));
            tasksMap.remove(taskId);
            historyManager.removeFromHistory(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else if (subtasksMap.containsKey(taskId)) {
            sortedTasks.remove(subtasksMap.get(taskId));
            subtasksMap.remove(taskId);
            historyManager.removeFromHistory(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else if (epicsMap.containsKey(taskId)) {
            for (Integer task : epicsMap.get(taskId).getSubtasksKeysList()) {
                subtasksMap.remove(task);
                historyManager.removeFromHistory(task);
            }
            epicsMap.remove(taskId);
            historyManager.removeFromHistory(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    @Override
    public boolean updateTask(int taskId, TaskStatus status, Task task) {
        if (!tasksMap.containsKey(taskId)) {
            System.out.println("\nТакой задачи нет");
            return false;
        }
        if (!checkTaskStartTime(task)) {
            System.out.println("На это время уже стоит задача");
            return false;
        }
        task.setId(taskId);
        task.setTaskStatus(status);
        tasksMap.put(taskId, task);
        System.out.println("\nЗадача #" + taskId + " обновлена");
        return true;
    }

    @Override
    public void updateEpic(int taskId, Epic epic) {
        if (epicsMap.containsKey(taskId)) {
            epic.setSubtasksKeysList(epicsMap.get(taskId).getSubtasksKeysList());
            epic.setId(taskId);
            epicsMap.put(taskId, epic);
            System.out.println("\nЗадача #" + taskId + " обновлена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    @Override
    public boolean updateSubtask(int taskId, TaskStatus status, Subtask subtask) {
        if (!subtasksMap.containsKey(taskId)) {
            System.out.println("\nТакой подзадачи нет");
            return false;
        }
        if (!checkTaskStartTime(subtask)) {
            System.out.println("На это время уже стоит задача");
            return false;
        }
        int epicId = subtasksMap.get(taskId).getEpicId();
        subtask.setEpicId(epicId);
        subtask.setTaskStatus(status);
        subtask.setId(taskId);
        subtasksMap.put(taskId, subtask);
        System.out.println("\nПодадача #" + subtask.getId() + " обновлена");
        checkEpicStatus(epicId);
        return true;
    }

    private void updateEpicDateInfo(Epic epic, Subtask subtask) {
        epic.setDuration(epic.getDuration() + subtask.getDuration());
        if (subtask.getStartTime() == null) return;
        if (epic.getStartTime() == null || epic.getStartTime().isAfter(subtask.getStartTime())) {
            epic.setStartTime(subtask.getStartTime());
        }
        if (epic.getEndTime() == null || epic.getEndTime().isBefore(subtask.getEndTime())) {
            epic.setEndTime(subtask.getEndTime());
        }
    }

    private void checkEpicStatus(int epicId) {
        int countNewTasks = 0;
        int countDoneTasks = 0;
        for (Integer subtaskId : epicsMap.get(epicId).getSubtasksKeysList()) {
            if (subtasksMap.get(subtaskId).getTaskStatus() == TaskStatus.NEW) {
                ++countNewTasks;
            }
            if (subtasksMap.get(subtaskId).getTaskStatus() == TaskStatus.DONE) {
                ++countDoneTasks;
            }
        }
        if (epicsMap.get(epicId).getSubtasksKeysList().isEmpty()
                || countNewTasks == epicsMap.get(epicId).getSubtasksKeysList().size()) {
            epicsMap.get(epicId).setTaskStatus(TaskStatus.NEW);
        } else if (countDoneTasks == epicsMap.get(epicId).getSubtasksKeysList().size()) {
            epicsMap.get(epicId).setTaskStatus(TaskStatus.DONE);
        } else {
            epicsMap.get(epicId).setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private boolean checkTaskStartTime(Task newTask) {
        int count = 0;
        if (sortedTasks.isEmpty() || newTask.getStartTime() == null) {
            return true;
        }
        for (Task task : sortedTasks) {
            if (task.getStartTime() == null) {
                count++;
                continue;
            }
            if (task.getId() == newTask.getId() && task.getStartTime().equals(newTask.getStartTime())) {
                count++;
                continue;
            }
            if (newTask.getEndTime().isBefore(task.getStartTime())
                    || newTask.getStartTime().isAfter(task.getEndTime())) {
                count++;
            }
        }
        return sortedTasks.size() == count;
    }
}
