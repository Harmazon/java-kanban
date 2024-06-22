package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int MAX_HISTORY_LIST = 10;
    private final List<Task> historyArrList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        List<Task> copiedList = List.copyOf(historyArrList);
        return copiedList;
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
