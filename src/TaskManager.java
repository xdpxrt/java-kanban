import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasksList = new HashMap<>();
    private HashMap<Integer, Epic> epicsList = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksList = new HashMap<>();
    int taskId = 1;

    void addTask(Task task) {
        task.setTaskId(taskId);
        tasksList.put(taskId++, task);
        System.out.println("\nСоздана: " + task);
    }

    void addEpic(Epic epic) {
        epic.setTaskId(taskId);
        epicsList.put(taskId++, epic);
        System.out.println("\nСоздана: " + epic);
    }

    void addSubtask(Integer epicId, Subtask subtask) {
        if (epicsList.containsKey(epicId)) {
            if (!epicsList.get(epicId).taskStatus.equals(TaskStatus.DONE)) {
                subtask.setTaskId(taskId);
                subtask.setEpicId(epicId);
                epicsList.get(epicId).getSubtasksKeysList().add(taskId);
                subtasksList.put(taskId++, subtask);
                System.out.println("\nСоздана: " + subtask);
            } else System.out.println("\nЗадача уже выполнена");
        } else System.out.println("\nТакой задачи нет");
    }

    ArrayList<Task> getAllTasks() {
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
        } else
            return allTasks;
    }

    Task getTask(Integer taskId) {
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

    ArrayList<Task> getSubtasksByEpic(Integer epicId) {
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

    void removeAllTasks() {
        tasksList.clear();
        epicsList.clear();
        subtasksList.clear();
        System.out.println("\nВсе задачи удалены");
    }

    void removeTask(Integer taskId) {
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
        } else System.out.println("\nТакой задачи нет");
    }

    void updateTask(int taskId, TaskStatus taskStatus, Task task) {
        if (tasksList.containsKey(taskId)) {
            task.setTaskId(taskId);
            task.setTaskStatus(taskStatus);
            tasksList.put(taskId, task);
            System.out.println("\nЗадача #" + taskId + " обновлена");
        } else System.out.println("\nТакой задачи нет");
    }

    void updateEpic(int taskId, Epic epic) {
        if (epicsList.containsKey(taskId)) {
            epic.setSubtasksKeysList(epicsList.get(taskId).getSubtasksKeysList());
            epic.setTaskId(taskId);
            epicsList.put(taskId, epic);
            System.out.println("\nЗадача #" + taskId + " обновлена");
        } else System.out.println("\nТакой задачи нет");
    }

    void updateSubtask(int taskId, TaskStatus taskStatus, Subtask subtask) {
        if (subtasksList.containsKey(taskId)) {
            int epicId = subtasksList.get(taskId).getEpicId();
            subtask.setEpicId(epicId);
            subtask.setTaskId(taskId);
            subtask.setTaskStatus(taskStatus);
            subtasksList.put(taskId, subtask);
            System.out.println("\nПодадача #" + taskId + " обновлена");
            checkEpicStatus(epicId);
        } else System.out.println("\nТакой подзадачи нет");
    }

    private void checkEpicStatus(int epicId) {
        int countNewTasks = 0;
        int countDoneTasks = 0;
        for (Integer subtaskId : epicsList.get(epicId).getSubtasksKeysList()) {
            if (subtasksList.get(subtaskId).taskStatus.equals(TaskStatus.NEW)) {
                ++countNewTasks;
            }
            if (subtasksList.get(subtaskId).taskStatus.equals(TaskStatus.DONE)) {
                ++countDoneTasks;
            }
        }
        if (epicsList.get(epicId).getSubtasksKeysList().isEmpty()
                || countNewTasks == epicsList.get(epicId).getSubtasksKeysList().size()) {
            epicsList.get(epicId).taskStatus = TaskStatus.NEW;
        } else if (countDoneTasks == epicsList.get(epicId).getSubtasksKeysList().size()) {
            epicsList.get(epicId).taskStatus = TaskStatus.DONE;
        } else epicsList.get(epicId).taskStatus = TaskStatus.IN_PROGRESS;
    }
}


