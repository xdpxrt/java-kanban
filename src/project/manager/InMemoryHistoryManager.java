package project.manager;

import project.task.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int historyLimit = 10;
    private final static ArrayList<Task> historyList = new ArrayList<>(historyLimit);
    @Override
    public void addToHistoryList(Task task) {
        if (historyList.size() >= historyLimit) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        System.out.println("Последние просмотренные задачи: ");
        return historyList;
    }
}
