package model;

public class CustomList {
    private Node head;
    private Node tail;

    public CustomList() {
        head = null;
        tail = null;
    }

    private boolean isEmpty() {
        return head == null;
    }

    public void add(Task task) {
        Node temp = new Node(task);

        if (isEmpty()) {
            tail = temp;
        } else {
            head.prev = temp;
        }

        temp.next = head;
        head = temp;
    }

    public void removeFirst() {
        if (head.next == null) {
            tail = null;
        } else {
            head.next.prev = null;
        }
        head = head.next;
    }

    public void removeLast() {
        if (head.next == null) {
            head = null;
        } else {
            tail.prev.next = null;
        }
        tail = tail.prev;
    }

    public void remove(Task task) {
        Node cur = head;

        while (cur.task != task) {
            cur = cur.next;

            if (cur == null) {
                return;
            }
        }

        if (cur == head) {
            removeFirst();
        } else {
            cur.prev.next = cur.next;
        }
        if (cur == tail) {
            removeLast();
        } else {
            cur.next.prev = cur.prev;
        }
    }

    public void print() {
        Node temp = head;

        while (temp != null) {
            System.out.println(temp.task);
            temp = temp.next;
        }
    }
}