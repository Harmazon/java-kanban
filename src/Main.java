import model.EpicTask;
import model.Status;
import model.Subtask;
import model.Task;
import service.*;

public class Main {
    public static void main(String[] args) {
        testProject();
    }

    static void testProject() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task = new Task("Задача", "Описание");
        EpicTask epic = new EpicTask("Эпик", "Описание");
        EpicTask epic2 = new EpicTask("Эпик2", "Описание");
        Subtask subtask1 = new Subtask("Подзадача1", "Описание", 2);
        Subtask subtask2 = new Subtask("Подзадача2", "Описание", 2);
        Subtask subtask3 = new Subtask("Подзадача3", "Описание", 2);
        Subtask subtask4 = new Subtask("Подзадача4", "Описание", 2);
        Subtask subtask5 = new Subtask("Подзадача5", "Описание", 3);
        Subtask subtask6 = new Subtask("Подзадача6", "Описание", 3);
        Subtask subtask7 = new Subtask("Подзадача7", "Описание", 3);

        inMemoryTaskManager.createTask(task);
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createEpic(epic2);
        inMemoryTaskManager.createSubTask(subtask1);
        inMemoryTaskManager.createSubTask(subtask2);
        inMemoryTaskManager.createSubTask(subtask4);
        inMemoryTaskManager.createSubTask(subtask5);
        inMemoryTaskManager.createSubTask(subtask6);
        inMemoryTaskManager.createSubTask(subtask3);
        inMemoryTaskManager.printAllTasks();
        System.out.println("\nПодзадачи эпика 1:\n" + inMemoryTaskManager.getSubTasksOfEpic(epic.getTaskId()));
        System.out.println("\nПодзадачи эпика 2:\n" + inMemoryTaskManager.getSubTasksOfEpic(epic2.getTaskId()) + "\n\n");
        inMemoryTaskManager.removeSubTask(subtask4.getTaskId());
        inMemoryTaskManager.printAllTasks();
        System.out.println("\nПодзадачи эпика 1:\n" + inMemoryTaskManager.getSubTasksOfEpic(epic.getTaskId()));

        subtask5.setTaskTitle("Новая подзадача 5");
        subtask5.setTaskDescription("Новое описание");
        subtask5.setTaskStatus(Status.DONE);
        inMemoryTaskManager.updateSubTask(subtask5);
        subtask6.setTaskTitle("Новая подзадача 6");
        subtask6.setTaskDescription("Новое описание");
        subtask6.setTaskStatus(Status.DONE);
        inMemoryTaskManager.updateSubTask(subtask6);
        //inMemoryTaskManager.createSubTask(subtask7);        // при создании подзадачи, статус эпика будет IN_PROGRESS
        System.out.println("\nЭпик 2: \n" + epic2);
        System.out.println("\nПодзадачи эпика 2:\n"
                + inMemoryTaskManager.getSubTasksOfEpic(subtask5.getEpicId()) + "\n\n");

        // Проверка удаления
        // Если удалить подз. с ст. DONE, и оставить подзадачу с ст. NEW (50 строка), то статус эпика изменится на NEW
        /*inMemoryTaskManager.removeSubTask(subtask5.getTaskId());
        //inMemoryTaskManager.removeSubTask(subtask6.getTaskId());
        //System.out.println("\nЭпик 2: \n" + epic2);
        //System.out.println("\nПодзадачи эпика 2:\n"
                + inMemoryTaskManager.getSubTasksOfEpic(subtask5.getEpicId()) + "\n\n"); */

        // Удаление эпика и его подзадач
        //inMemoryTaskManager.removeEpic(epic.getTaskId());
        //inMemoryTaskManager.printAllTasks();

        // Проверка истории
        System.out.println("Проверка истории:");
        inMemoryTaskManager.getTask(task.getTaskId());                     // <--- не войдет в список
        inMemoryTaskManager.getTask(epic.getTaskId());
        inMemoryTaskManager.getTask(epic.getTaskId());
        inMemoryTaskManager.getTask(epic2.getTaskId());
        inMemoryTaskManager.getTask(epic2.getTaskId());
        inMemoryTaskManager.getTask(subtask5.getTaskId());
        inMemoryTaskManager.getTask(subtask6.getTaskId());
        inMemoryTaskManager.getTask(subtask1.getTaskId());
        inMemoryTaskManager.getTask(epic2.getTaskId());
        inMemoryTaskManager.getTask(subtask2.getTaskId());
        inMemoryTaskManager.getTask(subtask3.getTaskId());
        System.out.println(inMemoryTaskManager.getHistory());

        // Проверка статуса эпиков, если удалить их подзадачи
        //inMemoryTaskManager.removeAllSubTasks();
        //inMemoryTaskManager.printAllTasks();
    }
}
