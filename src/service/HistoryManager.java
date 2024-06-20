package service;

import model.Task;

import java.util.ArrayList;

public interface HistoryManager {
    public void add(Task task);             // должен помечать задачи как просмотренные

    public ArrayList getHistory();               // возвращает список задач
}
