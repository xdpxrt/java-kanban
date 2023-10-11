package project.manager;

import project.manager.util.CustomLinkedList;
import project.manager.util.Node;
import project.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> historyList = new CustomLinkedList<>();

    @Override
    public void setHistoryList(CustomLinkedList<Task> historyList) {
        this.historyList = historyList;
    }

    @Override
    public void addToHistory(Task task) {
        historyList.linkLast(task);
    }

    @Override
    public void removeFromHistory(int id) {
        Node<Task> node = historyList.getNode(id);
        historyList.removeNode(node);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>(historyList.getTasks());
        if (!history.isEmpty()) {
            return history;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void removeHistory() {
        historyList.removeAll();
    }
}