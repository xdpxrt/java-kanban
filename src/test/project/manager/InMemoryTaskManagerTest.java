package project.manager;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void beforeEachInMemory() {
        taskManager = Managers.getDefaultInMemoryTaskManager();
        init();
    }
}
