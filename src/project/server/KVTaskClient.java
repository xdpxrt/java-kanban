package project.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final URI uri;
    private final String apiToken;
    private final HttpClient client = HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();


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
        try {
            HttpResponse<String> response = client.send(save, handler);
            int status = response.statusCode();
            if (status >= 200 && status <= 299) {
                System.out.println("Сохранено на сервер");
            } else if (status >= 400 && status <= 499) {
                System.out.println("Сервер сообщил о проблеме с запросом. Код состояния: " + status);
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + status);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + saveURI + "' возникла ошибка.\n"
                    + "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    public String load(String key) throws IOException, InterruptedException {
        String loadURI = uri.toString() + "/load/" + key + "?API_TOKEN=" + apiToken;
        HttpRequest load = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(loadURI))
                .build();
        String value = null;
        try {
            HttpResponse<String> response = client.send(load, handler);
            int status = response.statusCode();
            if (status >= 200 && status <= 299) {
                value = response.body();
                System.out.println("Выгружено с сервера");
            } else if (status >= 400 && status <= 499) {
                System.out.println("Сервер сообщил о проблеме с запросом. Код состояния: " + status);
                return null;
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + status);
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + loadURI + "' возникла ошибка.\n"
                    + "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return value;
    }


}
