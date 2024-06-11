import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    int newId = 1;
    int doneTrigger = 0;
    HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    HashMap<Integer, Task> epicTasksHashMap = new HashMap<>();
    HashMap<Integer, Task> subTasksHashMap = new HashMap<>();
    // ID подзадач хранятся в ArrayList, которые связаны со своими эпиками одинаковыми ключами.
    // т.е. ID ArrayList'a == ID эпика.
    HashMap<Integer, ArrayList<Integer>> idDataHashMap = new HashMap<>();



    void putTask(Task task) {                                       // создание простой задачи
        task.setTaskId(newId);
        newId++;
        tasksHashMap.put(task.getTaskId(), task);
    }

    protected void changeTaskToEpic(int id) {                       // превращение задачи в эпик
        epicTasksHashMap.put(id, tasksHashMap.get(id));
        tasksHashMap.remove(id);
    }

    void putSubTask(int id, Subtask subtask) {                      // создание поздадачи
        if (!idDataHashMap.containsKey(id)) {
            ArrayList<Integer> subTasksIds = new ArrayList<>();
            changeTaskToEpic(id);
            subtask.setTaskId(newId);
            newId++;
            subtask.setSubTaskId(subtask.getTaskId());
            subTasksIds.add(subtask.getSubTaskId());
            idDataHashMap.put(id, subTasksIds);
            subTasksHashMap.put(subtask.getTaskId(), subtask);
        } else {
            ArrayList<Integer> subTasksIds = idDataHashMap.get(id);
            subtask.setTaskId(newId);
            newId++;
            subtask.setSubTaskId(subtask.getTaskId());
            subTasksIds.add(subtask.getSubTaskId());
            idDataHashMap.put(id, subTasksIds);
            subTasksHashMap.put(subtask.getTaskId(), subtask);
        }
    }

    Task showTask(int id) {                                     // вывести в консоль задачу, эпик или подзадачу
        if (tasksHashMap.containsKey(id)) {
            return tasksHashMap.get(id);                        // return задача
        } else if (epicTasksHashMap.containsKey(id)) {
            return epicTasksHashMap.get(id);                    // return эпик
        } else if (subTasksHashMap.containsKey(id)) {
            return subTasksHashMap.get(id);                     // return подзадача
        } else {
            System.out.println("Такого id нет");
            return null;
        }
    }

    protected HashMap<Integer, Task> showAllSimpleTasks() {
        return tasksHashMap;
    }

    protected HashMap<Integer, Task> showAllEpics() {
        return epicTasksHashMap;
    }

    void showSubTasks(int id) {                                 // вывод в консоль подзадач эпика
        if (epicTasksHashMap.containsKey(id) && idDataHashMap.containsKey(id)) {
            for (Integer idNum : idDataHashMap.get(id)) {
                int keyForSubtaskHashMap = idNum;
                System.out.println(subTasksHashMap.get(keyForSubtaskHashMap));
            }
        } else {
            System.out.println("Такого id нет");
        }
    }

    void removeTask(int id) {                                   // удалить определенную задачу или эпик
        if (tasksHashMap.containsKey(id)) {
            tasksHashMap.remove(id);
        } else if (epicTasksHashMap.containsKey(id)) {
            epicTasksHashMap.remove(id);
            subTasksHashMap.remove(id);
        } else {
            System.out.println("Такого id нет");

        }
    }

    void removeAllTasks() {                                     // удалить все задачи и эпики
        tasksHashMap.clear();
        epicTasksHashMap.clear();
        subTasksHashMap.clear();
    }

    void updateTask(int id, Task task) {                        // изменить название и описание задачи или эпика
        int idCopy = id;
        if (tasksHashMap.containsKey(id)) {
            tasksHashMap.remove(id);
            task.setTaskId(idCopy);
            task.setTaskStatus(Status.IN_PROGRESS);
            tasksHashMap.put(task.getTaskId(), task);
        } else if (epicTasksHashMap.containsKey(id)) {
            epicTasksHashMap.remove(id);
            task.setTaskId(idCopy);
            task.setTaskStatus(Status.IN_PROGRESS);
            epicTasksHashMap.put(task.getTaskId(), task);
        } else {
            System.out.println("Такого id нет");
        }
    }

    void changeStatusDone(int id) {                         // установка статуса DONE
        if (tasksHashMap.containsKey(id)) {
            showTask(id).setTaskStatus(Status.DONE);
        } else if (epicTasksHashMap.containsKey(id)) {
            showTask(id).setTaskStatus(Status.DONE);
        } else if (subTasksHashMap.containsKey(id)) {
            showTask(id).setTaskStatus(Status.DONE);
        } else {
            System.out.println("Такого id нет");
        }
    }

    void changeStatusInProgress(int id) {               // установка статуса IN_PROGRESS
        if (tasksHashMap.containsKey(id)) {
            showTask(id).setTaskStatus(Status.IN_PROGRESS);
        } else if (epicTasksHashMap.containsKey(id)) {
            showTask(id).setTaskStatus(Status.IN_PROGRESS);
        } else if (subTasksHashMap.containsKey(id)) {
            showTask(id).setTaskStatus(Status.IN_PROGRESS);
        } else {
            System.out.println("Такого id нет");
        }
    }

    Status changeAndCheckEpicStatus(int id) {
        ArrayList<Integer> arrayWithId = idDataHashMap.get(id);
        for (int i = 0; i < arrayWithId.size(); i++) {
            //arrayWithId.get(i);                                                           // получили ID
            if (subTasksHashMap.containsKey(arrayWithId.get(i))) {
                int bufer = arrayWithId.get(i);
                if (showTask(bufer).getTaskStatus().equals(Status.IN_PROGRESS)) {
                    showTask(id).setTaskStatus(Status.IN_PROGRESS);
                }
                else if (showTask(bufer).getTaskStatus().equals(Status.DONE)) {
                    showTask(id).setTaskStatus(Status.IN_PROGRESS);
                    doneTrigger++;
                }
                if (doneTrigger == arrayWithId.size()) {
                    showTask(id).setTaskStatus(Status.DONE);
                    doneTrigger = 0;
                }
            }
        }
        return showTask(id).getTaskStatus();
    }
}
