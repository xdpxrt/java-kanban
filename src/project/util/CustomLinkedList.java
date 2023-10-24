package project.util;

import java.util.*;

import project.task.Task;

public class CustomLinkedList<T extends Task> {
    private final Map<Integer, Node<Task>> tasksMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    public void linkLast(Task task) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else oldTail.setNext(newNode);
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
        if (head != null) {
            Task task = head.getData();
            while (task != null) {
                historyList.add(task);
                node = node.getNext();
                if (node != null) {
                    task = node.getData();
                } else task = null;
            }
        }
        return historyList;
    }

    public void removeNode(Node<Task> node) {
        if (node == null) return;
        Node<Task> prevNode = node.getPrev();
        Node<Task> nextNode = node.getNext();
        if (prevNode != null) {
            prevNode.setNext(nextNode);
        } else {
            head = nextNode;
        }
        if (nextNode != null) {
            nextNode.setPrev(prevNode);
        } else {
            tail = prevNode;
        }
    }

    public void removeAll() {
        head = null;
    }
}
