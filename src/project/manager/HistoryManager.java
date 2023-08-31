package project.manager;

import project.task.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void addToHistoryList(Task task);

    ArrayList<Task> getHistory();
}

