package service;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    @Override
    public void add(Task task) {                    // должен помечать задачи как просмотренные

    }

    @Override
    public ArrayList getHistory() {
        TaskManager manager = Managers.getDefault();
        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) manager;

        System.out.println("список вызывается из InMemoryHistoryManager, приходит пустой" + inMemoryTaskManager.historyArrList);
        System.out.println("вызов тестового значения, инициализированного в InMemoryTaskManager " + inMemoryTaskManager.testInt);

        ArrayList<Task> taskReservation = new ArrayList<>();
        for (int key : inMemoryTaskManager.historyArrList) {
            if (inMemoryTaskManager.tasksHashMap.containsKey(key)) {
                taskReservation.add(inMemoryTaskManager.tasksHashMap.get(key));
            } else if (inMemoryTaskManager.epicTasksHashMap.containsKey(key)) {
                taskReservation.add(inMemoryTaskManager.epicTasksHashMap.get(key));
            } else if (inMemoryTaskManager.subTasksHashMap.containsKey(key)) {
                taskReservation.add(inMemoryTaskManager.subTasksHashMap.get(key));
            } else {
                return null;
            }
        }
        return taskReservation;
    }


}
