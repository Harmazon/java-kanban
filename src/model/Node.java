package model;

public class Node {
    public Task task;
    public Node next;
    public Node prev;

    public Node(Task task) {
        this.task = task;
        next = null;
        prev = null;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return prev;
    }

    public void setPrevious(Node previous) {
        this.prev = previous;
    }
}