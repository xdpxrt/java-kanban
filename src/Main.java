import project.server.KVServer;
import project.server.KVTaskClient;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();
        KVTaskClient client = new KVTaskClient(URI.create("http://localhost:8078"));
        String json = "{\n" +
                "\t\"name\": \"testName\",\n" +
                "\t\"description\": \"testDescription\",\n" +
                "\t\"id\": 1,\n" +
                "\t\"taskStatus\": \"NEW\",\n" +
                "\t\"duration\": 30,\n" +
                "\t\"startTime\": \"21.10.2023 15:30\"\n" +
                "}";
        client.put("1",json);
        System.out.println(client.load("1"));
    }
}
