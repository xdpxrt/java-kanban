package project.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final URI uri;
    private final String apiToken;
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();


    public KVTaskClient(URI uri) throws IOException, InterruptedException {
        this.uri = uri;
        String registerURI = uri.toString() + "/register";
        HttpRequest register = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(registerURI))
                .build();
        HttpResponse<String> response = client.send(register, handler);
        this.apiToken = response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        String saveURI = uri.toString() + "/save/" + key + "?API_TOKEN=" + apiToken;
        HttpRequest save = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(saveURI))
                .build();
        client.send(save, handler);
        System.out.println("Сохранено на сервер");
    }

    public String load(String key) throws IOException, InterruptedException {
        String loadURI = uri.toString() + "/load/" + key + "?API_TOKEN=" + apiToken;
        HttpRequest load = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(loadURI))
                .build();
        HttpResponse<String> response = client.send(load, handler);
        return response.body();
    }


}
