package service;

import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private static Task task1 = new Task("ЗадачаТест1", "Описание1");
    private static Task task2 = new Task("ЗадачаТест2", "Описание2");

    @Test
    void add() {
        historyManager.add(task1);
        historyManager.add(task2);

        assertNotNull(historyManager, "История не пустая.");

        assertEquals(2, historyManager.getHistory().size(), "История не пустая.");

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(10, historyManager.getHistory().size(), "Содержимое равно 10.");


    }
}