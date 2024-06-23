package service;

import model.EpicTask;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {
    private final InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    private static Task task1 = new Task("ЗадачаТест1", "Описание1");
    private static Task task2 = new Task("ЗадачаТест2", "Описание2");
    private static EpicTask epic1 = new EpicTask("Эпик1", "Описание1");
    private static EpicTask epic2 = new EpicTask("Эпик2", "Описание2");
    private static EpicTask epic3 = new EpicTask("Эпик3", "Описание3");

    @BeforeEach
    void setUp() {
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.createEpic(epic1);
        inMemoryTaskManager.createEpic(epic2);
        inMemoryTaskManager.createEpic(epic3);
    }

    @Test
    void createTask() {
        // проверим ожидаемый id
        assertEquals(1, task1.getTaskId());
        assertEquals(2, task2.getTaskId());

        // Проверяем, что задачи не равны
        assertNotSame(task1, task2);
    }

    @Test
    void createEpic() {
        // проверим ожидаемый id
        assertEquals(3, epic1.getTaskId());
        assertEquals(4, epic2.getTaskId());

        // Проверяем, что задачи не равны
        assertNotSame(epic1, epic2);
    }

    @Test
    void createSubTask() {
        Subtask subtask1 = new Subtask("ПодзадачаТест1", "Описание1", epic1.getTaskId());
        Subtask subtask2 = new Subtask("ПодзадачаТест2", "Описание2", epic2.getTaskId());

        inMemoryTaskManager.createSubTask(subtask1);
        inMemoryTaskManager.createSubTask(subtask2);

        // проверим ожидаемый id
        assertEquals(6, subtask1.getTaskId());
        assertEquals(7, subtask2.getTaskId());

        // проверим корректное помещение подзадачи в соответствующую коллекцию эпика
        assertEquals(epic1.getTaskId(), subtask1.getEpicId(), "id совпадают");

        // проверим, что подзадача находится в коллекции
        assertEquals(epic1.getSubTasksOfEpic().get(0), subtask1.getTaskId(), "id подзадачи в листе");
    }

    @Test
    void removeTask() {
        Subtask subtask1 = new Subtask("ПодзадачаТест1", "Описание1", epic1.getTaskId());
        Subtask subtask2 = new Subtask("ПодзадачаТест2", "Описание2", epic1.getTaskId());

        inMemoryTaskManager.createSubTask(subtask1);
        inMemoryTaskManager.createSubTask(subtask2);

        // проверим наличие задачи в хэш-мапе после применения метода
        int idTask = task1.getTaskId();
        inMemoryTaskManager.removeTask(idTask);
        assertFalse(inMemoryTaskManager.tasksHashMap.containsKey(idTask), "задача удалена");

        // подготовка: перед удалением убедимся что коллекция id и хэш-мапа подзадач заполнены
        int idEpic = epic1.getTaskId();
        int idSub1 = subtask1.getTaskId();
        int idSub2 = subtask2.getTaskId();

        assertTrue(epic1.getSubTasksOfEpic().contains(idSub1), "в листе содержится этот id");
        assertTrue(epic1.getSubTasksOfEpic().contains(idSub2), "в листе содержится этот id");
        assertTrue(inMemoryTaskManager.subTasksHashMap.containsKey(idSub1), "мапа содержит эту подзадачу");
        assertTrue(inMemoryTaskManager.subTasksHashMap.containsKey(idSub2), "мапа содержит эту подзадачу");

        // проверим корректное удаление эпика, его коллекции и подзадач
        inMemoryTaskManager.removeEpic(idEpic);
        assertFalse(inMemoryTaskManager.epicTasksHashMap.containsKey(idEpic), "эпик удален из мапы");
        assertFalse(epic1.getSubTasksOfEpic().contains(idSub1), "id подзадачи удален из листа");
        assertFalse(epic1.getSubTasksOfEpic().contains(idSub2), "id подзадачи удален из листа");
        assertFalse(inMemoryTaskManager.subTasksHashMap.containsKey(idSub1), "подзадача удалена из мапы");
        assertFalse(inMemoryTaskManager.subTasksHashMap.containsKey(idSub2), "подзадача удалена мапы");
    }

    @Test
    void updateTask() {
        // убедимся в корректной работе метода обновления задачи
        assertEquals("ЗадачаТест1", task1.getTaskTitle(), "начальные входные данные совпадают");
        assertEquals("Описание1", task1.getTaskDescription(), "начальные входные данные совпадают");

        task1.setTaskTitle("Новая задача");
        task1.setTaskDescription("Новое описание");
        inMemoryTaskManager.updateTask(task1);

        assertEquals("Новая задача", task1.getTaskTitle(), "новые данные успешно подставлены");
        assertEquals("Новое описание", task1.getTaskDescription(), "новые данные успешно подставлены");
    }

    @Test
    void checkAndChangeStatus() {
        // убедимся в корректной работе метода обновления статуса на примере эпика
        Subtask subtask1 = new Subtask("ПодзадачаТест1", "Описание1", epic3.getTaskId());
        Subtask subtask2 = new Subtask("ПодзадачаТест2", "Описание2", epic3.getTaskId());
        Subtask subtask3 = new Subtask("ПодзадачаТест2", "Описание2", epic3.getTaskId());

        inMemoryTaskManager.createSubTask(subtask1);
        inMemoryTaskManager.createSubTask(subtask2);

        int idSub1 = subtask1.getTaskId();
        int idSub2 = subtask2.getTaskId();

        assertEquals(6, subtask1.getTaskId());
        assertEquals(7, subtask2.getTaskId());

        // эпик и подзадачи имеют статус NEW
        assertTrue(subtask1.getTaskStatus().equals(Status.NEW), "NEW");
        assertTrue(subtask2.getTaskStatus().equals(Status.NEW), "NEW");
        assertTrue(epic3.getTaskStatus().equals(Status.NEW), "NEW");

        // эпик IN_PROGRESS, subtask1 IN_PROGRESS
        subtask1.setTaskTitle("T");
        subtask1.setTaskDescription("D");
        subtask1.setTaskStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateTask(subtask1);
        inMemoryTaskManager.checkAndChangeStatus(epic3);

        assertTrue(subtask1.getTaskStatus().equals(Status.IN_PROGRESS), "IN_PROGRESS");
        assertTrue(subtask2.getTaskStatus().equals(Status.NEW), "NEW");
        assertTrue(epic3.getTaskStatus().equals(Status.IN_PROGRESS), "IN_PROGRESS");

        // эпик IN_PROGRESS, subtask1 DONE
        subtask1.setTaskTitle("T");
        subtask1.setTaskDescription("D");
        subtask1.setTaskStatus(Status.DONE);
        inMemoryTaskManager.updateTask(subtask1);
        inMemoryTaskManager.checkAndChangeStatus(epic3);

        assertTrue(subtask1.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(subtask2.getTaskStatus().equals(Status.NEW), "NEW");
        assertTrue(epic3.getTaskStatus().equals(Status.IN_PROGRESS), "IN_PROGRESS");

        // эпик DONE, subtask1 DONE, subtask2 DONE
        subtask1.setTaskTitle("T");
        subtask1.setTaskDescription("D");
        subtask1.setTaskStatus(Status.DONE);
        subtask2.setTaskTitle("T");
        subtask2.setTaskDescription("D");
        subtask2.setTaskStatus(Status.DONE);
        inMemoryTaskManager.updateTask(subtask1);
        inMemoryTaskManager.updateTask(subtask2);
        inMemoryTaskManager.checkAndChangeStatus(epic3);

        assertTrue(subtask1.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(subtask2.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(epic3.getTaskStatus().equals(Status.DONE), "DONE");

        // эпик IN_PROGRESS, subtask1 DONE, subtask2 DONE, subtask3 NEW
        inMemoryTaskManager.createSubTask(subtask3);
        int idSub3 = subtask3.getTaskId();
        inMemoryTaskManager.checkAndChangeStatus(epic3);

        assertTrue(subtask1.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(subtask2.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(subtask3.getTaskStatus().equals(Status.NEW), "NEW");
        assertTrue(epic3.getTaskStatus().equals(Status.IN_PROGRESS), "IN_PROGRESS");

        // эпик IN_PROGRESS, subtask1 DELETE, subtask2 DONE, subtask3 NEW
        inMemoryTaskManager.removeSubTask(idSub1);
        inMemoryTaskManager.checkAndChangeStatus(epic3);

        assertTrue(subtask2.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(subtask3.getTaskStatus().equals(Status.NEW), "NEW");
        assertTrue(epic3.getTaskStatus().equals(Status.IN_PROGRESS), "IN_PROGRESS");

        // эпик DONE, subtask1 DELETE, subtask2 DONE, subtask3 DELETE
        inMemoryTaskManager.removeSubTask(idSub3);
        inMemoryTaskManager.checkAndChangeStatus(epic3);

        assertTrue(subtask2.getTaskStatus().equals(Status.DONE), "DONE");
        assertTrue(epic3.getTaskStatus().equals(Status.DONE), "DONE");

        // эпик NEW, subtask1 DELETE, subtask2 DELETE, subtask3 DELETE
        inMemoryTaskManager.removeSubTask(idSub2);
        inMemoryTaskManager.checkAndChangeStatus(epic3);
        assertTrue(epic3.getTaskStatus().equals(Status.NEW), "NEW");
        assertEquals(0, epic3.getSubTasksOfEpic().size(), "коллекция Id подзадач пуста");
    }
}