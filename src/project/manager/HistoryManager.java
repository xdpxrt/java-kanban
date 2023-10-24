package project.manager;

import project.util.CustomLinkedList;
import project.task.Task;

import java.util.List;

public interface HistoryManager {
    void addToHistory(Task task);

    void setHistoryList(CustomLinkedList<Task> historyList);

    void removeFromHistory(int id);

    List<Task> getHistory();

    void removeHistory();
}

