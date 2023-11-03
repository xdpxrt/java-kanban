package project.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import project.manager.*;
import project.task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import com.google.gson.*;
import project.util.LocalDateAdapter;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer httpServer;
    private final TaskManager taskManager;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();

    public static void main(String[] args) throws IOException {
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }

    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        this.taskManager = Managers.getDefaultFileBackedTaskManager();
        taskManager.addTask(new Task("testName", "testDescription", 30, "21.10.2023 15:30"));
        httpServer.createContext("/tasks", this::tasksHandle);
        httpServer.createContext("/tasks/task", this::taskHandle);
        httpServer.createContext("/tasks/epic", this::epicHandle);
        httpServer.createContext("/tasks/subtask", this::subtaskHandle);
        httpServer.createContext("/tasks/history", this::historyHandle);
        httpServer.createContext("/tasks/priority", this::priorityHandle);
    }

    public void tasksHandle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    System.out.println("hello");
                    List<Task> array = taskManager.getAllTasks();
                    String tasks = gson.toJson(array);
                    writeResponse(exchange, tasks, 200);
                case "DELETE":
                default:
            }
        } else if (Pattern.matches("^id=\\d+$", query)) {
            int id = Integer.parseInt(query.replaceFirst("id=", ""));
            switch (exchange.getRequestMethod()) {
                case "GET":
                    System.out.println("buy");
                    String task = gson.toJson(taskManager.getTask(1));
                    writeResponse(exchange, task, 200);
                case "DELETE":
                default:
            }
        }
    }

    public void taskHandle(HttpExchange exchange) throws IOException {
    }

    public void epicHandle(HttpExchange exchange) throws IOException {
    }

    public void subtaskHandle(HttpExchange exchange) throws IOException {
    }

    public void historyHandle(HttpExchange exchange) throws IOException {
    }

    public void priorityHandle(HttpExchange exchange) throws IOException {
    }


    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        exchange.sendResponseHeaders(responseCode, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseString.getBytes());
        }
    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        httpServer.stop(1);
        System.out.println("HTTP-сервер остановлен!");

    }

}




