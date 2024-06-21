package Test;

import static org.junit.jupiter.api.Assertions.*;
import model.Task;
import org.junit.Test;
import service.InMemoryTaskManager;

class TaskTest {
    @Test
    public void testTaskEquality() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("ЗадачаТест1", "Описание1");
        inMemoryTaskManager.createTask(task1);
        int idTask1 = task1.getTaskId();

        Task task2 = new Task("ЗадачаТест2", "Описание2");
        inMemoryTaskManager.createTask(task2);
        int idTask2 = task2.getTaskId();

        // Проверяем, что задачи не равны
        assertFalse(idTask1 == idTask2);

        // Создаем задачу с тем же id
        task2 = new Task("ЗадачаТест1", "Описание1");
        task2.setTaskId(task1.getTaskId());
        idTask2 = task2.getTaskId();

        // Теперь задачи должны быть равны
        assertTrue(idTask1 == idTask2);
    }

}