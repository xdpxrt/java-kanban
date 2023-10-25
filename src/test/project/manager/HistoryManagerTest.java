package project.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryManagerTest {
    private HistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;

    private void fillHistory() {
        task1 = new Task("Отпуск", "Купить билеты", 30, "21.10.23 15:30");
        task2 = new Task("Бег", "Подготовиться к беговым тренировкам", 30
                , "21.06.2023 10:40");
        task3 = new Task("Стоматолог", "Сходить на чистку зубов", 120
                , "04.07.2023 08:00");
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        historyManager.addToHistory(task1);
        historyManager.addToHistory(task2);
        historyManager.addToHistory(task3);
    }

    @BeforeEach
    public void beforeEach() {
        historyManager = Managers.getDefaultHistoryManager();
    }

    @Test
    public void addToHistoryTest() {
        fillHistory();
        assertEquals(3, historyManager.getHistory().size());
    }

    @Test
    public void addToHistoryDuplicateTest() {
        fillHistory();
        historyManager.addToHistory(task1);
        assertEquals(3, historyManager.getHistory().size());
    }

    @Test
    public void removeFirstFromHistoryTest() {
        fillHistory();
        historyManager.removeFromHistory(1);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    public void removeMiddleFromHistoryTest() {
        fillHistory();
        historyManager.removeFromHistory(2);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    public void removeLastFromHistoryTest() {
        fillHistory();
        historyManager.removeFromHistory(3);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    public void removeHistoryTest() {
        fillHistory();
        historyManager.removeHistory();
        assertEquals(0, historyManager.getHistory().size());
    }
}
