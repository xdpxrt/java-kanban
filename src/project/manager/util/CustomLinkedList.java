package project.manager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import project.task.Task;

public class CustomLinkedList<T extends Task> {
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    private final HashMap<Integer, Node<Task>> tasksMap = new HashMap<>();

    public void linkLast(Task task) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else oldTail.next = newNode;
        size++;
        if (tasksMap.containsKey(task.getId())) {
            removeNode(tasksMap.get(task.getId()));
        }
        tasksMap.put(task.getId(), newNode);
    }

    public Node<Task> getNode(int taskId) {
        return tasksMap.get(taskId);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> historyList = new ArrayList<>();
        Node<Task> node = head;
        Task task = head.data;
        while (task != null) {
            historyList.add(task);
            node = node.next;
            if (node != null) {
                task = node.data;
            } else task = null;
        }
        return historyList;
    }

    public void removeNode(Node<Task> node) {
        Node<Task> prevNode = node.prev;
        Node<Task> nextNode = node.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }
        if (nextNode != null){
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
    }
}
