package project.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import project.manager.FileBackedTaskManager;
import project.manager.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks");
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

        FileBackedTaskManager taskManager = Managers.getDefaultFileBackedTaskManager();

    }

    private static class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

        }
    }
}
