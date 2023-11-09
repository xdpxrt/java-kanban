package project.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.manager.TaskManager;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;
import project.task.TaskStatus;
import project.util.LocalDateAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.server.HttpTaskServer.HTTP_PORT;

public class HttpTaskServerTest {
    private KVServer kvServer;
    private HttpTaskServer taskServer;
    private TaskManager taskManager;
    private final String url = "http://localhost:" + HTTP_PORT;
    private final HttpClient client = HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();
    private Epic epic;
    private Subtask subtask;
    private Task task;

    @BeforeEach
    public void startServers() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskServer = new HttpTaskServer();
        taskServer.start();

        taskManager = taskServer.getTaskManager();
        epic = new Epic("testName", "testDescription");
        subtask = new Subtask("testName", "testDescription"
                , 30, "30.06.2023 10:30", 1);
        task = new Task("testName", "testDescription"
                , 30, "21.10.2023 15:30");
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
        kvServer.stop();
    }

    @Test
    public void getTaskTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        String uri = url + "/tasks/?id=1";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        Task newTask = gson.fromJson(response.body(), Task.class);
        assertEquals(200, response.statusCode());
        assertEquals(task, newTask);
    }

    @Test
    public void deleteTaskTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        String uri = url + "/tasks/?id=1";
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void getAllTasksTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        taskManager.addTask(epic);
        String uri = url + "/tasks";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        Task[] tasksArray = gson.fromJson(response.body(), Task[].class);
        assertEquals(200, response.statusCode());
        assertEquals(taskManager.getAllTasks().size(), tasksArray.length);
    }

    @Test
    public void deleteAllTasksTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        taskManager.addTask(epic);
        String uri = url + "/tasks";
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    public void tasksWithParametersWrongMethodTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        String uri = url + "/tasks/?id=1";
        String taskToJson = gson.toJson(task);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(405, response.statusCode());
    }

    @Test
    public void tasksWithoutParametersWrongMethodTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        String uri = url + "/tasks";
        String taskToJson = gson.toJson(task);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(405, response.statusCode());
    }

    @Test
    public void tasksWithParametersWrongIdTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/?id=1";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Задача с id:1 не найдена", response.body());
    }

    @Test
    public void tasksBadRequestTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/?i=1";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
    }

    @Test
    public void addTaskTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task";
        task.setId(1);
        String taskToJson = gson.toJson(task);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(task, taskManager.getTask(1));
    }

    @Test
    public void updateTaskTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task/?id=1&status=NEW";
        taskManager.addTask(task);
        Task newTask = new Task("testName", "testDescription"
                , 30, "10.05.2024 10:20");
        newTask.setId(1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(newTask, taskManager.getTask(1));
    }

    @Test
    public void taskWrongMethodTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(405, response.statusCode());
    }

    @Test
    public void taskBadJsonTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task";
        String badJson = "{badJson}";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(badJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Получен некорректный JSON", response.body());
    }

    @Test
    public void addTaskWithBadTimeTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task";
        taskManager.addTask(task);
        Task newTask = new Task("testName", "testDescription"
                , 30, "21.10.2023 15:30");
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Задача не добавлена! На это время уже стоит другая задача", response.body());
    }

    @Test
    public void updateTaskWithWrongIdTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task/?id=2&status=DONE";
        taskManager.addTask(task);
        Task newTask = new Task("testName", "testDescription"
                , 30, "10.05.2024 10:20");
        newTask.setId(1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Задача с id:" + 2 + " не найдена", response.body());
    }

    @Test
    public void updateTaskWithBadStatusTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task/?id=1&status=DDDONE";
        taskManager.addTask(task);
        Task newTask = new Task("testName", "testDescription"
                , 30, "10.05.2024 10:20");
        newTask.setId(1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Некорректный статус", response.body());
    }

    @Test
    public void updateTaskWithBadTimeTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/task/?id=2&status=IN_PROGRESS";
        taskManager.addTask(task);
        taskManager.addTask(new Task("testName", "testDescription"
                , 30, "10.05.2024 10:20"));
        Task newTask = new Task("testName", "testDescription"
                , 30, "21.10.2023 15:30");
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Задача не обновлена! На это время уже стоит другая задача", response.body());
    }

    @Test
    public void getSubtasksByIdTest() throws IOException, InterruptedException {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        String uri = url + "/tasks/epic/getSubtasks/?id=1";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        Subtask[] subtasks = gson.fromJson(response.body(), Subtask[].class);
        assertEquals(200, response.statusCode());
        assertEquals(taskManager.getSubtasksByEpic(1).size(), subtasks.length);
        assertEquals(taskManager.getSubtasksByEpic(1).get(0), subtasks[0]);
    }

    @Test
    public void getSubtasksByIdWithWrongIdTest() throws IOException, InterruptedException {
        taskManager.addEpic(epic);
        String uri = url + "/tasks/epic/getSubtasks/?id=2";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Задача с id:2 не найдена", response.body());
    }

    @Test
    public void addEpicTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/epic";
        epic.setId(1);
        String taskToJson = gson.toJson(epic);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(epic, taskManager.getTask(1));
    }

    @Test
    public void epicBadJsonTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/epic";
        String badJson = "{badJson}";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(badJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Получен некорректный JSON", response.body());
    }

    @Test
    public void updateEpicTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/epic/?id=1";
        taskManager.addEpic(epic);
        Task newEpic = new Epic("anotherTestName", "anotherTestDescription");
        newEpic.setId(1);
        String epicToJson = gson.toJson(newEpic);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epicToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(newEpic, taskManager.getTask(1));
    }

    @Test
    public void epicWithParametersWrongIdTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/epic/?id=1";
        String epicToJson = gson.toJson(epic);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epicToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Задача с id:1 не найдена", response.body());
    }

    @Test
    public void epicWrongMethodTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/epic";
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(405, response.statusCode());
    }

    @Test
    public void addSubtaskTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask";
        taskManager.addEpic(epic);
        String taskToJson = gson.toJson(subtask);
        subtask.setId(2);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(subtask, taskManager.getTask(2));
    }

    @Test
    public void updateSubtaskTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask/?id=2&status=DONE";
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        Task newSubtask = subtask = new Subtask("newTestName", "newTestDescription"
                , 30, "30.06.2023 10:30", 1);
        newSubtask.setId(2);
        newSubtask.setTaskStatus(TaskStatus.DONE);
        String taskToJson = gson.toJson(newSubtask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(newSubtask, taskManager.getTask(2));
    }

    @Test
    public void subtaskWrongMethodTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(405, response.statusCode());
    }

    @Test
    public void subtaskBadJsonTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask";
        String badJson = "{badJson}";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(badJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Получен некорректный JSON", response.body());
    }

    @Test
    public void addSubtaskWithBadTimeTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask";
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        Subtask newTask = new Subtask("testName", "testDescription"
                , 30, "30.06.2023 10:30", 1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Задача не добавлена! На это время уже стоит другая задача", response.body());
    }

    @Test
    public void updateSubtaskWithWrongIdTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask/?id=3&status=DONE";
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        Subtask newTask = new Subtask("testName", "testDescription"
                , 30, "30.06.2023 10:30", 1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Задача с id:" + 3 + " не найдена", response.body());
    }

    @Test
    public void updateSubtaskWithBadTimeTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask/?id=3&status=IN_PROGRESS";
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.addSubtask(new Subtask("testName", "testDescription"
                , 30, "21.10.2023 15:30", 1));
        Subtask newTask = new Subtask("testName", "testDescription"
                , 30, "30.06.2023 10:30", 1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Задача не обновлена! На это время уже стоит другая задача", response.body());
    }

    @Test
    public void updateSubtaskWithBadStatusTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/subtask/?id=2&status=DDDONE";
        taskManager.addEpic(epic);
        taskManager.addTask(task);
        Task newTask = new Task("testName", "testDescription"
                , 30, "10.05.2024 10:20");
        newTask.setId(1);
        String taskToJson = gson.toJson(newTask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Некорректный статус", response.body());
    }

    @Test
    public void getHistoryTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        taskManager.addTask(new Task("testName", "testDescription"
                , 30, "21.10.2024 15:30"));
        taskManager.getTask(2);
        taskManager.getTask(1);
        String uri = url + "/tasks/history";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        Task[] history = gson.fromJson(response.body(), Task[].class);
        assertEquals(200, response.statusCode());
        assertEquals(2, history.length);
        assertEquals(task, history[1]);
    }

    @Test
    public void getHistoryWrongMethodTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/history";
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(405, response.statusCode());
    }

    @Test
    public void getHistoryBadRequestTest() throws IOException, InterruptedException {
        String uri = url + "/tasks/history/?id=2";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Некорректный запрос", response.body());
    }

    @Test
    public void getPriorityTasksTest() throws IOException, InterruptedException {
        taskManager.addTask(task);
        taskManager.addTask(new Task("testName", "testDescription"
                , 30, "20.10.2023 15:30"));
        String uri = url + "/tasks/priority";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, handler);
        Task[] history = gson.fromJson(response.body(), Task[].class);
        assertEquals(200, response.statusCode());
        assertEquals(2, history.length);
        assertEquals(task, history[1]);
    }


}
