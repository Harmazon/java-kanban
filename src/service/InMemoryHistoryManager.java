package service;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int MAX_HISTORY_LIST = 10;
    public static InMemoryHistoryManager inMemoryHistoryManagerStatic = new InMemoryHistoryManager();
    private final ArrayList<Task> historyArrList = new ArrayList<>();

    @Override
    public ArrayList<Task> getHistory() {
        return historyArrList;
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyArrList.size() >= MAX_HISTORY_LIST) {
                historyArrList.remove(0);
            }
            historyArrList.add(task);
        }
    }
}
