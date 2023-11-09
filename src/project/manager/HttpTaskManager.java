package project.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import project.server.KVTaskClient;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.util.CSVTaskUtil;
import project.util.CustomLinkedList;
import project.util.LocalDateAdapter;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static project.util.CSVTaskUtil.EMPTY_HISTORY;

public class HttpTaskManager extends FileBackedTaskManager {
    private static KVTaskClient client;
    public final static String KV_SERVER_PORT = "8078";
    private static final String TASKS_KEY = "tasks";
    private static final String EPICS_KEY = "epics";
    private static final String SUBTASKS_KEY = "subtasks";
    private static final String HISTORY_KEY = "history";
    public static final String ID_KEY = "id";
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();

    public HttpTaskManager() throws IOException, InterruptedException {
        String uri = "http://localhost:" + KV_SERVER_PORT;
        client = new KVTaskClient(URI.create(uri));
    }

    private HttpTaskManager(Map<Integer, Task> taskMap, Map<Integer, Epic> epicMap, Map<Integer
            , Subtask> subtaskMap, HistoryManager historyManager, int id, Set<Task> sortedTasks) {
        super(taskMap, epicMap, subtaskMap, historyManager, id, sortedTasks);
    }

    @Override
    public void save() {
        String tasksToJson = gson.toJson(new ArrayList<>(getTasksMap().values()));
        String epicsToJson = gson.toJson(new ArrayList<>(getEpicsMap().values()));
        String subtasksToJson = gson.toJson(new ArrayList<>(getSubtasksMap().values()));
        String historyAsString;
        if (!getHistoryManager().getHistory().isEmpty()) {
            historyAsString = CSVTaskUtil.historyTostring(getHistoryManager());
        } else historyAsString = EMPTY_HISTORY;
        try {
            client.put(TASKS_KEY, tasksToJson);
            client.put(EPICS_KEY, epicsToJson);
            client.put(SUBTASKS_KEY, subtasksToJson);
            client.put(HISTORY_KEY, historyAsString);
            client.put(ID_KEY, String.valueOf(getId()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static TaskManager load() {
        Map<Integer, Task> tasksMap = new HashMap<>();
        Map<Integer, Epic> epicsMap = new HashMap<>();
        Map<Integer, Subtask> subtasksMap = new HashMap<>();
        Set<Task> sortedTasks = new TreeSet<>();
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        CustomLinkedList<Task> historyList = new CustomLinkedList<>();
        try {
            String tasksAsJson = client.load(TASKS_KEY);
            String epicsAsJson = client.load(EPICS_KEY);
            String subtasksAsJson = client.load(SUBTASKS_KEY);
            String history = client.load(HISTORY_KEY);
            int id = Integer.parseInt(client.load(ID_KEY));
            if (tasksAsJson == null || epicsAsJson == null || subtasksAsJson == null) {
                return new HttpTaskManager();
            }
            Task[] tasksArray = gson.fromJson(tasksAsJson, Task[].class);
            for (Task task : tasksArray) {
                tasksMap.put(task.getId(), task);
                sortedTasks.add(task);
            }
            Epic[] epicsArray = gson.fromJson(epicsAsJson, Epic[].class);
            for (Epic epic : epicsArray) {
                epicsMap.put(epic.getId(), epic);
                sortedTasks.add(epic);
            }
            Subtask[] subtasksArray = gson.fromJson(subtasksAsJson, Subtask[].class);
            for (Subtask subtask : subtasksArray) {
                subtasksMap.put(subtask.getId(), subtask);
                sortedTasks.add(subtask);

            }
            if (history != null) {
                List<Integer> historyAsArray = CSVTaskUtil.historyFromString(history);
                for (Integer taskId : historyAsArray) {
                    if (tasksMap.containsKey(taskId)) {
                        historyList.linkLast(tasksMap.get(taskId));
                    } else if (epicsMap.containsKey(taskId)) {
                        historyList.linkLast(epicsMap.get(taskId));
                    } else if (subtasksMap.containsKey(taskId)) {
                        historyList.linkLast(subtasksMap.get(taskId));
                    }
                }
            }
            historyManager.setHistoryList(historyList);
            return new HttpTaskManager(tasksMap, epicsMap, subtasksMap, historyManager, id, sortedTasks);
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
