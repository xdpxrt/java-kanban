package project.manager;

import project.manager.util.CustomLinkedList;
import project.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<Task> historyList = new CustomLinkedList<>();

    @Override
    public void addToHistory(Task task) {
        historyList.linkLast(task);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Task> getHistory() {
        System.out.println("\nПоследние просмотренные задачи: ");
        return new ArrayList<>(historyList.getTasks());
    }
}