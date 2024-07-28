package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final int max = 10;
    private final List<Task> historyArrList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return List.copyOf(historyArrList);
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyArrList.size() >= max) {
                historyArrList.remove(0);
            }
            historyArrList.add(task);
        }
    }
}
