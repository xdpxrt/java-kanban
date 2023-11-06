package project.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import project.server.KVTaskClient;
import project.util.LocalDateAdapter;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

public class HttpTaskManager extends FileBackedTaskManager {
    KVTaskClient client;
    private static final String TASKS_KEY = "tasks";
    private static final String ID_KEY = "id";
    private static final String HISTORY_KEY = "history";
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();

    public HttpTaskManager() {
    }

    public HttpTaskManager(URI uri) throws IOException, InterruptedException {
        client = new KVTaskClient(uri);
    }

    @Override
    public void save() {
        String tasksToJson = gson.toJson(getAllTasks());
        String historyToJson = gson.toJson(getHistoryManager().getHistory());
        String idToJson = gson.toJson(getId());
        try {
            client.put(TASKS_KEY, tasksToJson);
            client.put(HISTORY_KEY, historyToJson);
            client.put(ID_KEY, idToJson);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public TaskManager load() {
        try {
            int id =

            client.load(TASKS_KEY);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new HttpTaskManager();
    }
}
