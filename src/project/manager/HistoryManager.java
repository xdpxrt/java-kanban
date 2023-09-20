package project.manager;

import project.task.Task;

import java.util.List;

public interface HistoryManager {
    void addToHistory(Task task);

    void remove(int id);

    List<Task> getHistory();
}

