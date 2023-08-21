package project.manager;

import java.util.ArrayList;
import java.util.HashMap;

import project.task.*;

public class TaskManager {
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicsList = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksList = new HashMap<>();
    private static int id = 1;

    public void addTask(Task task) {
        task.setId(id);
        tasksList.put(id++, task);
        System.out.println("\nСоздана: " + task);
    }

    public void addEpic(Epic epic) {
        epic.setId(id);
        epicsList.put(id++, epic);
        System.out.println("\nСоздана: " + epic);
    }

    public void addSubtask(Subtask subtask) {
        if (epicsList.containsKey(subtask.getEpicId())) {
            if (epicsList.get(subtask.getEpicId()).getTaskStatus() != TaskStatus.DONE) {
                subtask.setId(id);
                subtask.setEpicId(subtask.getEpicId());
                epicsList.get(subtask.getEpicId()).getSubtasksKeysList().add(id);
                subtasksList.put(id++, subtask);
                System.out.println("\nСоздана: " + subtask);
            } else System.out.println("\nЗадача уже выполнена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int taskId : tasksList.keySet()) {
            allTasks.add(tasksList.get(taskId));
        }
        for (int taskId : epicsList.keySet()) {
            allTasks.add(epicsList.get(taskId));
        }
        if (allTasks.isEmpty()) {
            System.out.println("\nЗадач нет");
            return null;
        } else {
            return allTasks;
        }
    }

    public Task getTask(Integer taskId) {
        if (tasksList.containsKey(taskId)) {
            return tasksList.get(taskId);
        }
        if (epicsList.containsKey(taskId)) {
            return epicsList.get(taskId);
        }
        if (subtasksList.containsKey(taskId)) {
            return subtasksList.get(taskId);
        } else {
            System.out.println("\nТакой задачи нет");
            return null;
        }
    }

    public ArrayList<Task> getSubtasksByEpic(Integer epicId) {
        if (epicsList.containsKey(epicId)) {
            ArrayList<Task> subtasks = new ArrayList<>();
            for (int taskId : epicsList.get(epicId).getSubtasksKeysList()) {
                subtasks.add(subtasksList.get(taskId));
            }
            return subtasks;
        } else {
            System.out.println("\nТакой задачи нет");
            return null;
        }
    }

    public void removeAllTasks() {
        tasksList.clear();
        epicsList.clear();
        subtasksList.clear();
        System.out.println("\nВсе задачи удалены");
    }

    public void removeTask(Integer taskId) {
        if (tasksList.containsKey(taskId)) {
            tasksList.remove(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else if (subtasksList.containsKey(taskId)) {
            subtasksList.remove(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else if (epicsList.containsKey(taskId)) {
            for (Integer task : epicsList.get(taskId).getSubtasksKeysList()) {
                subtasksList.remove(task);
            }
            epicsList.remove(taskId);
            System.out.println("\nЗадача #" + taskId + " удалена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

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

    public void updateSubtask(int taskId, TaskStatus status, Subtask subtask) {
        if (subtasksList.containsKey(taskId)) {
            int epicId = subtasksList.get(taskId).getEpicId();
            subtask.setEpicId(epicId);
            subtask.setTaskStatus(status);
            subtasksList.put(taskId, subtask);
            System.out.println("\nПодадача #" + subtask.getId() + " обновлена");
            checkEpicStatus(epicId);
        } else {
            System.out.println("\nТакой подзадачи нет");
        }
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
}


