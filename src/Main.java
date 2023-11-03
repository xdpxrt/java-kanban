import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import project.manager.Managers;
import project.manager.TaskManager;
import project.task.Task;
import project.util.LocalDateAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
//        HttpTaskServer server = new HttpTaskServer();
//        server.start();
        TaskManager manager = Managers.getDefaultFileBackedTaskManager();
        manager.addTask(new Task("testName", "testDescription", 30, "21.10.2023 15:30"));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();
        String task = gson.toJson(manager.getTask(1));
        System.out.println(task);

    }

}

