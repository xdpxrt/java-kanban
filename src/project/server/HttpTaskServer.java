package project.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import project.manager.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer httpServer;
    private final TaskManager taskManager;

    public static void main(String[] args) throws IOException {
//        HttpTaskServer server = new HttpTaskServer();
        String put = "122";
        String test = put.split("=")[1];

    }

    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        this.taskManager = Managers.getDefaultFileBackedTaskManager();
        httpServer.createContext("/tasks", this::tasksHandle);
        httpServer.createContext("/tasks/task", this::taskHandle);
        httpServer.createContext("/tasks/epic", this::epicHandle);
        httpServer.createContext("/tasks/subtask", this::subtaskHandle);
        httpServer.createContext("/tasks/history", this::historyHandle);
        httpServer.createContext("/tasks/priority", this::priorityHandle);
        httpServer.start();
    }


    public void tasksHandle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        switch (exchange.getRequestMethod()) {
            case "GET":
                if (path.length == 2) {
                    if (!taskManager.getAllTasks().isEmpty()) {
//                        writeResponse();      TODO
                    }
                }
                if (path.length == 3) {
                    try {
                        int id = Integer.parseInt(path[2].split("=")[1]);
                        if (taskManager.getTask(id) != null) {
//                            writeResponse();TODO
                        }

                    } catch (RuntimeException exp) {
                        System.out.println("aaa");
                    }
                }
                break;
            case "DELETE":
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + path);
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




