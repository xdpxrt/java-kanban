package project.manager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CustomLinkedList<Task> {
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
        tasksMap.put(task.getId, newNode);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> historyList = new ArrayList<>();
        for (Task task = head.data; task != null; task = head.next.data) {
            historyList.add(task);
        }
        return historyList;
    }

    public void removeNode(Node<Task> node) {
        Node<Task> prevNode = node.prev;
        Node<Task> newNode = node.next;
        prevNode.next = newNode;
        newNode.prev = prevNode;

    }
}
