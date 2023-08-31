package project.manager;

import project.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;
    private final static List<Task> historyList = new ArrayList<>(HISTORY_LIMIT);

    @Override
    public void addToHistoryList(Task task) {
        if (historyList.size() >= HISTORY_LIMIT) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        System.out.println("Последние просмотренные задачи: ");
        return historyList;
    }
}
