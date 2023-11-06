package project.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import project.manager.*;
import project.task.Epic;
import project.task.Subtask;
import project.task.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

import com.google.gson.*;
import project.task.TaskStatus;
import project.util.LocalDateAdapter;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private String query;
    String[] path;
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter()).create();

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

    public void tasksHandle(HttpExchange exchange) {
        setPathAndQuery(exchange);
        if (path.length == 2 && query == null) {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    String tasks = gson.toJson(taskManager.getAllTasks());
                    writeResponse(exchange, tasks, 200);
                    break;
                case "DELETE":
                    taskManager.removeAllTasks();
                    writeResponse(exchange, "Все задачи удалены", 200);
                    break;
                default:
                    writeResponse(exchange, "Метод не поддерживается", 405);
            }
        } else if (path.length == 2 && Pattern.matches("^id=\\d+$", query)) {
            int id;
            if (parseId(exchange, query).isPresent()) {
                id = parseId(exchange, query).get();
            } else return;
            if (taskManager.getTask(id) == null) {
                writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                return;
            }
            switch (exchange.getRequestMethod()) {
                case "GET":
                    String task = gson.toJson(taskManager.getTask(id));
                    writeResponse(exchange, task, 200);
                    break;
                case "DELETE":
                    taskManager.removeTask(id);
                    writeResponse(exchange, "Задача с id:" + id + " удалена", 200);
                    break;
                default:
                    writeResponse(exchange, "Метод не поддерживается", 405);
            }
        } else
            writeResponse(exchange, "Некорректный запрос", 400);
    }

    public void taskHandle(HttpExchange exchange) {
        Task newTask;
        setPathAndQuery(exchange);
        if (!"POST".equals(exchange.getRequestMethod())) {
            writeResponse(exchange, "Метод не поддерживается", 405);
            return;
        }
        try {
            newTask = gson.fromJson(new String(exchange.getRequestBody().readAllBytes()), Task.class);
        } catch (JsonSyntaxException | IOException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
            return;
        }
        if (path.length == 3 && query == null) {
            newTask.setTaskStatus(TaskStatus.NEW);
            boolean isAdded = taskManager.addTask(newTask);
            if (isAdded) {
                writeResponse(exchange, "Задача добавлена", 201);
            } else {
                writeResponse(exchange, "Задача не добавлена! На это время уже стоит другая задача"
                        , 200);
            }
        } else if (path.length == 3 && Pattern.matches("^id=\\d+&status=\\D+$", query)) {
            int id;
            TaskStatus status;
            String[] queryToArray = query.split("&");
            Optional<Integer> optId = parseId(exchange, queryToArray[0]);
            Optional<TaskStatus> optStatus = parseStatus(exchange, queryToArray[1]);
            if (optId.isPresent() && optStatus.isPresent()) {
                id = optId.get();
                status = optStatus.get();
            } else return;
            if (taskManager.getTask(optId.get()) == null) {
                writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                return;
            }
            newTask.setId(id);
            boolean isAdded = taskManager.updateTask(optId.get(), status, newTask);
            if (isAdded) {
                writeResponse(exchange, "Задача #" + id + " обновлена", 201);
            } else writeResponse(exchange
                    , "Задача не обновлена! На это время уже стоит другая задача"
                    , 200);
        } else writeResponse(exchange, "Некорректный запрос", 400);
    }

    public void epicHandle(HttpExchange exchange) {
        Epic newEpic;
        setPathAndQuery(exchange);
        int id;
        switch (exchange.getRequestMethod()) {
            case "GET":
                if (path.length == 4 && path[3].equals("getSubtasks") && Pattern.matches("^id=\\d+$", query)) {
                    if (parseId(exchange, query).isPresent()) {
                        id = parseId(exchange, query).get();
                    } else return;
                    if (taskManager.getTask(id) == null) {
                        writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                        return;
                    }
                    String subtasks = gson.toJson(taskManager.getSubtasksByEpic(id));
                    writeResponse(exchange, subtasks, 200);
                } else writeResponse(exchange, "Некорректный запрос", 400);
                break;
            case "POST":
                try {
                    newEpic = gson.fromJson(new String(exchange.getRequestBody().readAllBytes()), Epic.class);
                } catch (JsonSyntaxException | IOException e) {
                    writeResponse(exchange, "Получен некорректный JSON", 400);
                    return;
                }
                if (path.length == 3 && query == null) {
                    newEpic.setTaskStatus(TaskStatus.NEW);
                    taskManager.addEpic(newEpic);
                    writeResponse(exchange, "Задача добавлена", 201);
                } else if (path.length == 3 && Pattern.matches("^id=\\d+$", query)) {
                    Optional<Integer> optId = parseId(exchange, query);
                    if (optId.isPresent()) {
                        id = parseId(exchange, query).get();
                    } else return;
                    if (taskManager.getTask(id) == null) {
                        writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                        return;
                    }
                    taskManager.updateEpic(id, newEpic);
                    writeResponse(exchange, "Задача #" + id + " обновлена", 201);
                } else
                    break;
            default:
                writeResponse(exchange, "Метод не поддерживается", 405);
        }
    }

    public void subtaskHandle(HttpExchange exchange) {
        Subtask newSubtask;
        setPathAndQuery(exchange);
        if (!"POST".equals(exchange.getRequestMethod())) {
            writeResponse(exchange, "Метод не поддерживается", 405);
            return;
        }
        try {
            newSubtask = gson.fromJson(new String(exchange.getRequestBody().readAllBytes()), Subtask.class);
        } catch (JsonSyntaxException | IOException e) {
            writeResponse(exchange, "Получен некорректный JSON", 400);
            return;
        }
        if (path.length == 3 && query == null) {
            newSubtask.setTaskStatus(TaskStatus.NEW);
            boolean isAdded = taskManager.addSubtask(newSubtask);
            if (isAdded) {
                writeResponse(exchange, "Задача добавлена", 201);
            } else {
                writeResponse(exchange, "Задача не добавлена! На это время уже стоит другая задача"
                        , 200);
            }
        } else if (path.length == 3 && Pattern.matches("^id=\\d+&\\D+$", query)) {
            int id;
            TaskStatus status;
            String[] queryToArray = query.split("&");
            Optional<Integer> optId = parseId(exchange, queryToArray[0]);
            Optional<TaskStatus> optStatus = parseStatus(exchange, queryToArray[1]);
            if (optId.isPresent() && optStatus.isPresent()) {
                id = optId.get();
                status = optStatus.get();
            } else return;
            if (taskManager.getTask(id) == null) {
                writeResponse(exchange, "Задача с id:" + id + " не найдена", 404);
                return;
            }
            newSubtask.setId(id);
            newSubtask.setTaskStatus(status);
            boolean isAdded = taskManager.updateSubtask(id, status, newSubtask);
            if (isAdded) {
                writeResponse(exchange, "Задача #" + id + " обновлена", 201);
            } else writeResponse(exchange, "Задача не обновлена! На это время уже стоит другая задача"
                    , 200);
        } else writeResponse(exchange, "Некорректный запрос", 400);
    }

    public void historyHandle(HttpExchange exchange) {
        setPathAndQuery(exchange);
        if (!"GET".equals(exchange.getRequestMethod())) {
            writeResponse(exchange, "Метод не поддерживается", 405);
            return;
        }
        if (path.length != 3 && query != null) {
            writeResponse(exchange, "Некорректный запрос", 400);
            return;
        }
        String history = gson.toJson(taskManager.getHistoryManager().getHistory());
        writeResponse(exchange, history, 200);
    }

    public void priorityHandle(HttpExchange exchange) {
        setPathAndQuery(exchange);
        if (!"GET".equals(exchange.getRequestMethod())) {
            writeResponse(exchange, "Метод не поддерживается", 405);
            return;
        }
        if (path.length != 3 && query != null) {
            writeResponse(exchange, "Некорректный запрос", 400);
            return;
        }
        String priority = gson.toJson(taskManager.getPrioritizedTasks());
        writeResponse(exchange, priority, 200);
    }

    public void start() {
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        httpServer.stop(1);
        System.out.println("HTTP-сервер остановлен!");

    }

    private void setPathAndQuery(HttpExchange exchange) {
        query = exchange.getRequestURI().getQuery();
        path = exchange.getRequestURI().getPath().split("/");
    }


    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) {
        try {
            exchange.sendResponseHeaders(responseCode, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(responseString.getBytes());
            os.close();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "Ошибка отправки ответа");
        }
    }

    private Optional<Integer> parseId(HttpExchange exchange, String query) {
        try {
            int id = Integer.parseInt(query.replaceFirst("id=", ""));
            return Optional.of(id);
        } catch (NumberFormatException e) {
            writeResponse(exchange, "Некорректный id", 400);
            return Optional.empty();
        }
    }

    private Optional<TaskStatus> parseStatus(HttpExchange exchange, String query) {
        String statusType = query.split("=")[1];
        switch (statusType) {
            case "NEW":
                return Optional.of(TaskStatus.NEW);
            case "IN_PROGRESS":
                return Optional.of(TaskStatus.IN_PROGRESS);
            case "DONE":
                return Optional.of(TaskStatus.DONE);
            default:
                writeResponse(exchange, "Некорректный статус", 400);
                return Optional.empty();
        }
    }
}




