package project.manager;

import org.junit.jupiter.api.BeforeEach;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    @BeforeEach
    public void beforeEachFileBacked() {
        taskManager = Managers.getDefaultFileBackedTaskManager();
        init();
    }
}
