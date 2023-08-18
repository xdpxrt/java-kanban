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
    }

    void addEpic(Epic epic) {
        epic.setTaskId(taskId);
        epicsList.put(taskId++, epic);
    }

    void addSubtask(Integer epicId, Subtask subtask) {
        if (epicsList.containsKey(epicId)) {
            if (!epicsList.get(epicId).taskStatus.equals(TaskStatus.DONE)) {
                subtask.setTaskId(taskId);
                subtask.setEpicId(epicId);
                epicsList.get(epicId).getSubtasksKeysList().add(taskId);
                subtasksList.put(taskId++, subtask);
            } else System.out.println("Задача уже выполнена");
        } else System.out.println("Такой задачи нет");
    }

    ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int id : tasksList.keySet()) {
            allTasks.add(tasksList.get(id));
        }
        for (int id : epicsList.keySet()) {
            allTasks.add(epicsList.get(id));
        }
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
            System.out.println("Такой задачи нет");
            return null;
        }
    }

    ArrayList<Task> getSubtasksByEpic(Integer epicId) {
        if (epicsList.containsKey(epicId)) {
            ArrayList<Task> subtasks = new ArrayList<>();
            for (int id : epicsList.get(epicId).getSubtasksKeysList()) {
                subtasks.add(tasksList.get(id));
            }

        }
        return subtasks;
    }

    void removeAllTasks() {
        tasksList.clear();
        epicsList.clear();
        subtasksList.clear();
        System.out.println("Все задачи удалены");
    }

    void removeTask(Integer taskId) {
        if (tasksList.containsKey(taskId)) {
            tasksList.remove(taskId);
        }
        if (subtasksList.containsKey(taskId)) {
            subtasksList.remove(taskId);
        }
        if (epicsList.containsKey(taskId)) {
            for (Integer task : epicsList.get(taskId).getSubtasksKeysList()) {
                subtasksList.remove(task);
            }
            epicsList.remove(taskId);
        } else System.out.println("Такой задачи нет");
    }


    void updateTask(int taskId, Task task) {
        if (tasksList.containsKey(taskId)) {
            task.setTaskId(taskId);
            tasksList.put(taskId, task);
        } else System.out.println("Такой задачи нет");
    }

    void updateEpic(int taskId, Epic epic) {
        if (epicsList.containsKey(taskId)) {
            epic.setSubtasksKeysList(epicsList.get(taskId).getSubtasksKeysList());
            epic.setTaskId(taskId);
            epicsList.put(taskId, epic);
        } else System.out.println("Такой задачи нет");
    }

    void updateSubtask(int taskId, Subtask subtask) {
        if (subtasksList.containsKey(taskId)) {
            int epicId = subtasksList.get(taskId).getEpicId();
            subtask.setEpicId(epicId);
            subtask.setTaskId(taskId);
            subtasksList.put(taskId, subtask);
            checkEpicStatus(epicId);
        } else System.out.println("Такой подзадачи нет");
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


