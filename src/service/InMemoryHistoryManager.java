package service;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public ArrayList<Task> historyArrList = new ArrayList<>();

    @Override
    public ArrayList getHistory() {
        return historyArrList;
    }

    @Override
    public void add(Task task) {                    // должен помечать задачи как просмотренные

    }

}
