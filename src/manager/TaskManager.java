package manager;

import java.util.ArrayList;
import java.util.HashMap;

import task.*;

public class TaskManager {
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicsList = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksList = new HashMap<>();
    private int id = 1;

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

    public void addSubtask(Integer epicId, Subtask subtask) {
        if (epicsList.containsKey(epicId)) {
            if (epicsList.get(epicId).getTaskStatus() != TaskStatus.DONE) {
                subtask.setId(id);
                subtask.setEpicId(epicId);
                epicsList.get(epicId).getSubtasksKeysList().add(id);
                subtasksList.put(id++, subtask);
                System.out.println("\nСоздана: " + subtask);
            } else System.out.println("\nЗадача уже выполнена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int id : tasksList.keySet()) {
            allTasks.add(tasksList.get(id));
        }
        for (int id : epicsList.keySet()) {
            allTasks.add(epicsList.get(id));
        }
        if (allTasks.isEmpty()) {
            System.out.println("\nЗадач нет");
            return null;
        } else {
            return allTasks;
        }
    }

    public Task getTask(Integer id) {
        if (tasksList.containsKey(id)) {
            return tasksList.get(id);
        }
        if (epicsList.containsKey(id)) {
            return epicsList.get(id);
        }
        if (subtasksList.containsKey(id)) {
            return subtasksList.get(id);
        } else {
            System.out.println("\nТакой задачи нет");
            return null;
        }
    }

    public ArrayList<Task> getSubtasksByEpic(Integer epicId) {
        if (epicsList.containsKey(epicId)) {
            ArrayList<Task> subtasks = new ArrayList<>();
            for (int id : epicsList.get(epicId).getSubtasksKeysList()) {
                subtasks.add(subtasksList.get(id));
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

    public void removeTask(Integer id) {
        if (tasksList.containsKey(id)) {
            tasksList.remove(id);
            System.out.println("\nЗадача #" + id + " удалена");
        } else if (subtasksList.containsKey(id)) {
            subtasksList.remove(id);
            System.out.println("\nЗадача #" + id + " удалена");
        } else if (epicsList.containsKey(id)) {
            for (Integer task : epicsList.get(id).getSubtasksKeysList()) {
                subtasksList.remove(task);
            }
            epicsList.remove(id);
            System.out.println("\nЗадача #" + id + " удалена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    public void updateTask(int id, TaskStatus status, Task task) {
        if (tasksList.containsKey(id)) {
            task.setId(id);
            task.setTaskStatus(status);
            tasksList.put(id, task);
            System.out.println("\nЗадача #" + id + " обновлена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    public void updateEpic(int id, Epic epic) {
        if (epicsList.containsKey(id)) {
            epic.setSubtasksKeysList(epicsList.get(id).getSubtasksKeysList());
            epic.setId(id);
            epicsList.put(id, epic);
            System.out.println("\nЗадача #" + id + " обновлена");
        } else {
            System.out.println("\nТакой задачи нет");
        }
    }

    public void updateSubtask(int id, TaskStatus status, Subtask subtask) {
        if (subtasksList.containsKey(id)) {
            int epicId = subtasksList.get(id).getEpicId();
            subtask.setEpicId(epicId);
            subtask.setId(id);
            subtask.setTaskStatus(status);
            subtasksList.put(id, subtask);
            System.out.println("\nПодадача #" + id + " обновлена");
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


